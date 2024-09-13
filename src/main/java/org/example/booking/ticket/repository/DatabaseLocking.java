package org.example.booking.ticket.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseLocking {
    private static final Map<Object, ReentrantLock> tableLocks = new ConcurrentHashMap<>();
    
    public static synchronized void acquireTableLock(Object key) {
        if (!tableLocks.containsKey(key)) {
            tableLocks.put(key, new ReentrantLock());
        }
        ReentrantLock lock = tableLocks.get(key);
        if (!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
    }

    public static synchronized void releaseTableLock(Object key) {
        if (!tableLocks.containsKey(key)) {
            throw new IllegalStateException("No such lock for key: " + key);
        }
        ReentrantLock lock = tableLocks.get(key);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
