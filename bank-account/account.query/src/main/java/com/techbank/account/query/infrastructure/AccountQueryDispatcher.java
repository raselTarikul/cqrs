package com.techbank.account.query.infrastructure;

import com.techbank.cqrs.core.domain.BaseEntity;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import com.techbank.cqrs.core.quries.BaseQuery;
import com.techbank.cqrs.core.quries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {
    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routs = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandlers(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routs.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routs.get(query.getClass());
        if(handlers == null || handlers.size() == 0){
            throw new RuntimeException("No query handlers was regsiter");
        }
        if(handlers.size() > 1){
            throw new RuntimeException("Cannot send query for more than handlers");
        }
        return handlers.get(0).handle(query);
    }
}
