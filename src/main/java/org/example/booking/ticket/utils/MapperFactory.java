package org.example.booking.ticket.utils;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MapperFactory {
    private final Map<Map.Entry<?, ?>, Mapper<?, ?>> mappers = new ConcurrentHashMap<>();

    public MapperFactory(List<Mapper<?, ?>> mappers) {
        for (Mapper<?, ?> mapper : mappers) {
            this.mappers.put(Map.entry(mapper.getSourceClass(), mapper.getDestinationClass()), mapper);
        }
    }

    @SuppressWarnings("unchecked")
    public <S, D> Mapper<S, D> getMapper(Class<S> sourceClass, Class<D> destinationClass) {
        return (Mapper<S, D>) mappers.get(Map.entry(sourceClass, destinationClass));
    }
}
