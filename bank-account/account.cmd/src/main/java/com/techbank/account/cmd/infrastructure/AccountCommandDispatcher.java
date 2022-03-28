package com.techbank.account.cmd.infrastructure;

import com.techbank.cqrs.core.commands.BaseCommand;
import com.techbank.cqrs.core.commands.CommandHandlerMethod;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routs = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routs.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);

    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routs.get(command.getClass());
        if(handlers == null || handlers.size() == 0){
            throw new RuntimeException("No command handler was register.");
        }
        if(handlers.size() > 1){
            throw new RuntimeException("Could not send to command to more than one handler");
        }
        handlers.get(0).handle(command);
    }
}
