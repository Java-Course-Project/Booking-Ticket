package org.example.booking.ticket.repository;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class DatabaseLocking {
    private static final Map<Object, ReentrantLock> tableLocks = new ConcurrentHashMap<>();
    private static final ReentrantLock methodLock = new ReentrantLock();
    private static final Map<Object, Condition> conditions = new ConcurrentHashMap<>();

    public static void acquireTableLock(Object key) {
        log.debug("Acquiring lock for key {} in thread {}", key, Thread.currentThread().getId());
        methodLock.lock();
        try {
            if (!tableLocks.containsKey(key)) {
                tableLocks.put(key, new ReentrantLock());
                conditions.put(key, methodLock.newCondition());
            }
            ReentrantLock lock = tableLocks.get(key);
            Condition condition = conditions.get(key);
            while (!lock.tryLock(0, TimeUnit.SECONDS)) {
                condition.await();
            }
            log.debug("Lock for key {} in thread {} held {} after acquiring", key, Thread.currentThread().getId(), lock.getHoldCount());
            log.debug("Acquired lock for key {} in thread {}", key, Thread.currentThread().getId());
        } catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
            methodLock.unlock();
        }
    }

    public static void releaseTableLock(Object key) {
        log.debug("Releasing lock for key {} in thread {}", key, Thread.currentThread().getId());
        methodLock.lock();
        try {
            if (!tableLocks.containsKey(key)) {
                throw new IllegalStateException("No such lock for key: " + key);
            }
            ReentrantLock lock = tableLocks.get(key);
            Condition condition = conditions.get(key);
            lock.unlock();
            condition.signalAll();
            log.debug("Lock for key {} in thread {} held {} after releasing", key, Thread.currentThread().getId(), lock.getHoldCount());
            log.debug("Released lock for key {} in thread {}", key, Thread.currentThread().getId());
        } finally {
            methodLock.unlock();
        }
    }
}
