package com.splitet.accountexample.command.handler;

import com.splitet.accountexample.command.model.CreateAccountCommand;
import com.splitet.accountexample.event.published.AccountCreatedEvent;
import com.splitet.accountexample.exception.AccountException;
import io.splitet.core.api.Command;
import io.splitet.core.api.CommandHandler;
import io.splitet.core.api.EventRepository;
import io.splitet.core.cassandra.ConcurrentEventException;
import io.splitet.core.common.EventKey;
import io.splitet.core.exception.EventStoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAccountCommandHandler implements CommandHandler {

    private final EventRepository eventRepository;

    @Command(eventRepository = "eventRepository")
    public EventKey execute(CreateAccountCommand command) throws AccountException {
        try {
            AccountCreatedEvent accountCreatedEvent = AccountCreatedEvent
                    .builder()
                    .userId(command.getUserId())
                    .accountBalance(command.getBalance())
                    .currency(command.getCurrency())
                    .build();
            return eventRepository.recordAndPublish(accountCreatedEvent);
        } catch (EventStoreException | ConcurrentEventException exception) {
            log.error("Exception is caught while creating account");
            throw new AccountException(String.format("Account can not be created, %s", command));
        }
    }
}

