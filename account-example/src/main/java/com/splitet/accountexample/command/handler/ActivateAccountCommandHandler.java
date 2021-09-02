package com.splitet.accountexample.command.handler;

import com.splitet.accountexample.command.model.ActivateAccountCommand;
import com.splitet.accountexample.entity.Account;
import com.splitet.accountexample.entity.AccountState;
import com.splitet.accountexample.event.published.AccountActivatedEvent;
import com.splitet.accountexample.exception.AccountException;
import io.splitet.core.api.Command;
import io.splitet.core.api.CommandHandler;
import io.splitet.core.api.EventRepository;
import io.splitet.core.api.ViewQuery;
import io.splitet.core.cassandra.ConcurrentEventException;
import io.splitet.core.common.EventKey;
import io.splitet.core.exception.EventStoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActivateAccountCommandHandler implements CommandHandler {

    private final ViewQuery<Account> viewQuery;

    private final EventRepository eventRepository;

    @Command
    public EventKey execute(ActivateAccountCommand command) throws AccountException {
        try {
            Account account = viewQuery.queryEntity(command.getAccountId());

            if (account == null) {
                throw new AccountException(String.format("Account with id: %s is not found", command.getAccountId()));
            }

            if (account.getState() == AccountState.ACTIVE) {
                throw new AccountException(String.format("Account with id: %s is already activated", command.getAccountId()));
            }

            AccountActivatedEvent accountActivatedEvent = AccountActivatedEvent.builder()
                    .state(AccountState.ACTIVE)
                    .userId(command.getAccountId())
                    .build();

            return eventRepository.recordAndPublish(account.getEventKey(), accountActivatedEvent);
        } catch (EventStoreException | ConcurrentEventException e) {
            log.error("Exception is caught while activating account {}", command.getAccountId());
            throw new AccountException(String.format("Can not activate account(%s)", command.getAccountId()));
        }
    }
}
