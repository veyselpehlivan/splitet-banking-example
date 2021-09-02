package com.splitet.transactionexample.command.handler;

import com.splitet.transactionexample.command.model.DepositTransactionCommand;
import com.splitet.transactionexample.event.model.published.CreditAccountEvent;
import com.splitet.transactionexample.event.model.published.DepositTransactionEvent;
import com.splitet.transactionexample.exception.TransactionException;
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
public class DepositTransactionCommandHandler implements CommandHandler {

    //private final TransactionMapper mapper;
    private final EventRepository eventRepository;

    @Command(eventRepository = "eventRepository")
    public EventKey execute(DepositTransactionCommand command) throws TransactionException {
        EventKey eventKey = null;
        try {
            DepositTransactionEvent transactionEvent = toEvent(command);
            eventKey = eventRepository.recordAndPublish(transactionEvent);
            CreditAccountEvent accountEvent = CreditAccountEvent.builder()
                    .accountId(transactionEvent.getAccountId())
                    .amount(transactionEvent.getAmount()).build();
            eventKey = eventRepository.recordAndPublish(eventKey, accountEvent);
        }
        catch (EventStoreException | ConcurrentEventException exception) {
            log.error("Exception is caught while creating account");
            throw new TransactionException(String.format("Transaction can not be executed", command));
        }
        return eventKey;
    }

    private DepositTransactionEvent toEvent(DepositTransactionCommand command) {
        return DepositTransactionEvent.builder()
                .accountId(command.getTarget().getIdentifier())
                .amount(command.getAmount())
                .build();
    }
}
