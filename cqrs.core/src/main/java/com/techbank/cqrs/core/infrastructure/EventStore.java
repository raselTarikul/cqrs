package com.techbank.cqrs.core.infrastructure;

import com.techbank.cqrs.core.events.BaseEvent;

import java.util.List;

public interface EventStore {
    void saveEvent(String aggregateI, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> baseEvent(String aggregateI);
}
