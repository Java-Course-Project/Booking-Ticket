package org.example.booking.ticket.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface Mapper<S, D> {
    D map(S s);

    default List<D> map(Collection<S> collection) {
        return new ArrayList<>(collection.stream().map(this::map).toList());
    }

    Class<S> getSourceClass();

    Class<D> getDestinationClass();
}
