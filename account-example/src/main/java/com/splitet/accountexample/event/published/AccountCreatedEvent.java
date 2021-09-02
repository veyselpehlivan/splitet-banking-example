package com.splitet.accountexample.event.published;

import com.fasterxml.jackson.annotation.JsonView;
import com.splitet.accountexample.entity.AccountState;
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
public class AccountCreatedEvent extends PublishedEvent {

    private final AccountState state = AccountState.CREATED;
    private String userId;
    private BigDecimal accountBalance;
    private String currency;

    @JsonView(Views.PublishedOnly.class)
    public String getAccountId() {
        return getSender().getEntityId();
    }

    @Override
    public EventType getEventType() {
        return EventType.OP_SINGLE;
    }

}
