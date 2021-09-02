package com.splitet.accountexample.event.published;

import com.splitet.accountexample.entity.AccountState;
import io.splitet.core.common.EventType;
import io.splitet.core.common.PublishedEvent;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountActivatedEvent extends PublishedEvent {

    private AccountState state;
    private String userId;

    @Override
    public EventType getEventType() {
        return EventType.OP_SINGLE;
    }
}
