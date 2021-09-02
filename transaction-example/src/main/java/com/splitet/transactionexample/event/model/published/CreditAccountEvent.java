package com.splitet.transactionexample.event.model.published;

import io.splitet.core.common.EventType;
import io.splitet.core.common.PublishedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditAccountEvent extends PublishedEvent {

    private String accountId;

    private BigDecimal amount;

    private String transactionId;

    private EventType eventType = EventType.EVENT;
}
