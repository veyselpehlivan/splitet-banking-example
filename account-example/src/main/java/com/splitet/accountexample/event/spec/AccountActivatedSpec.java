package com.splitet.accountexample.event.spec;

import com.splitet.accountexample.entity.Account;
import com.splitet.accountexample.event.published.AccountActivatedEvent;
import io.splitet.core.view.EntityFunctionSpec;
import org.springframework.stereotype.Component;

@Component
public class AccountActivatedSpec extends EntityFunctionSpec<Account, AccountActivatedEvent> {

    public AccountActivatedSpec() {
        super((account, event) -> {
            AccountActivatedEvent accountActivatedEvent = event.getEventData();
            account.setState(accountActivatedEvent.getState());
            return account;
        });
    }
}
