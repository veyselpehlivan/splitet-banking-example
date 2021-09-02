package com.splitet.accountexample.event.published;

import com.fasterxml.jackson.annotation.JsonView;
import io.splitet.core.api.Views;
import io.splitet.core.common.EventType;
import io.splitet.core.common.PublishedEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreditedEvent extends PublishedEvent {

    private String accountId;

    private BigDecimal amount;

    private String transactionId;

    @JsonView(Views.PublishedOnly.class)
    public String getAccountId() {
        return getSender().getEntityId();
    }

    @Override
    public EventType getEventType() {
        return EventType.EVENT;
    }

}
