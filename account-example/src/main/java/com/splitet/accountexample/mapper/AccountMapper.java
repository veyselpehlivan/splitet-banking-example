package com.splitet.accountexample.mapper;

import com.splitet.accountexample.command.model.CreateAccountCommand;
import com.splitet.accountexample.resource.model.CreateAccountDto;

public interface AccountMapper {

    CreateAccountCommand toCommand(CreateAccountDto dto);

}