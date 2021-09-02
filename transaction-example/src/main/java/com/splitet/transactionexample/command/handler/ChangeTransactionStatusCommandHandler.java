package com.splitet.transactionexample.command.handler;

import com.splitet.transactionexample.command.model.ChangeTransactionStatusCommand;
import com.splitet.transactionexample.entity.Transaction;
import com.splitet.transactionexample.entity.TransactionStatus;
import com.splitet.transactionexample.event.model.published.TransactionStatusChangedEvent;
import com.splitet.transactionexample.exception.TransactionException;
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

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangeTransactionStatusCommandHandler implements CommandHandler {

    private final EventRepository eventRepository;

    private final ViewQuery<Transaction> viewQuery;

    @Command(eventRepository = "eventRepository")
    public EventKey execute(ChangeTransactionStatusCommand command) throws TransactionException {
        EventKey eventKey = null;
        try {
            Transaction transaction = viewQuery.queryEntity(command.getTransactionId());
            if(!Objects.isNull(transaction) && transaction.getTransactionStatus().equals(TransactionStatus.PENDING)){
                eventKey = eventRepository.recordAndPublish(transaction, toEvent(command));
            }
        }
        catch (EventStoreException | ConcurrentEventException exception) {
            log.error("Exception is caught while creating account");
            throw new TransactionException(String.format("Transaction status can not be changed", command));
        }
        return eventKey;
    }

    private TransactionStatusChangedEvent toEvent(ChangeTransactionStatusCommand command) {
        return TransactionStatusChangedEvent.builder()
                .status(command.getStatus())
                .build();
    }
}
