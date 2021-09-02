package com.splitet.accountexample.event.spec;

import com.splitet.accountexample.entity.Account;
import com.splitet.accountexample.event.published.AccountCreatedEvent;
import io.splitet.core.view.EntityFunctionSpec;
import org.springframework.stereotype.Component;

@Component
public class AccountCreatedSpec extends EntityFunctionSpec<Account, AccountCreatedEvent> {
    public AccountCreatedSpec() {
        super((account, event) -> {
            AccountCreatedEvent eventData = event.getEventData();
            account.setUserId(eventData.getUserId());
            account.setBalance(eventData.getAccountBalance());
            account.setCurrency(eventData.getCurrency());
            account.setState(eventData.getState());
            return account;
        });
    }
}
