package com.splitet.transactionexample.event.model.published;

import com.splitet.transactionexample.entity.TransactionStatus;
import io.splitet.core.common.EventType;
import io.splitet.core.common.PublishedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionStatusChangedEvent extends PublishedEvent {

    private TransactionStatus status;

    private EventType eventType = EventType.OP_SINGLE;
}
