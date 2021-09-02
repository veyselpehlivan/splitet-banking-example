package com.splitet.accountexample.resource.api;

import com.splitet.accountexample.command.handler.ActivateAccountCommandHandler;
import com.splitet.accountexample.command.handler.CreateAccountCommandHandler;
import com.splitet.accountexample.command.model.ActivateAccountCommand;
import com.splitet.accountexample.command.model.CreateAccountCommand;
import com.splitet.accountexample.exception.AccountException;
import com.splitet.accountexample.resource.AccountCommandResource;
import com.splitet.accountexample.resource.model.CreateAccountDto;
import io.splitet.core.common.EventKey;
import io.splitet.core.exception.EventStoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountCommandApiController implements AccountCommandResource {

    //private final AccountMapper mapper;

    private final CreateAccountCommandHandler createAccountCommandHandler;
    private final ActivateAccountCommandHandler activateAccountCommandHandler;

    @Override
    public EventKey createAccount(CreateAccountDto dto) throws AccountException {
        return createAccountCommandHandler.execute(toCommand(dto));
    }

    @Override
    public EventKey activateAccount(String accountId) throws AccountException {
        return activateAccountCommandHandler.execute(ActivateAccountCommand.builder().accountId(accountId).build());
    }

    private CreateAccountCommand toCommand(CreateAccountDto dto) {
        CreateAccountCommand createAccountCommand = new CreateAccountCommand();
        createAccountCommand.setBalance(dto.getBalance());
        createAccountCommand.setUserId(dto.getUserId());
        createAccountCommand.setCurrency(dto.getCurrency());

        return createAccountCommand;
    }

}
