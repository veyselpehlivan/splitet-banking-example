package com.splitet.accountexample.event.listener;

import com.splitet.accountexample.entity.Account;
import com.splitet.accountexample.entity.AccountState;
import com.splitet.accountexample.event.published.AccountCreditedEvent;
import com.splitet.accountexample.event.received.CreditAccountEvent;
import io.splitet.core.api.EventHandler;
import io.splitet.core.api.EventRepository;
import io.splitet.core.api.ViewQuery;
import io.splitet.core.common.EventKey;
import io.splitet.core.exception.EventStoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreditAccountEventHandler implements EventHandler<CreditAccountEvent> {

    private final ViewQuery<Account> accountQuery;
    private final EventRepository eventRepository;

    @Override
    @KafkaListener(topics = "CreditAccountEvent", containerFactory = "eventsKafkaListenerContainerFactory")
    public EventKey execute(CreditAccountEvent event) throws Exception {
        Account account = accountQuery.queryEntity(event.getAccountId());
        if (Objects.isNull(account) || !AccountState.ACTIVE.equals(account.getState())) {
            throw new EventStoreException("Account state is not valid for this operation: " + event);
        }

        log.debug("Account({}) is processing", event.getAccountId());
        AccountCreditedEvent accountCreditedEvent = AccountCreditedEvent.builder()
                .amount(event.getAmount())
                .transactionId(event.getSender().getEntityId())
                .build();

        return eventRepository.recordAndPublish(account, accountCreditedEvent);
    }
}
