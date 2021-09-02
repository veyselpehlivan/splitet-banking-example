package com.splitet.accountexample.resource;

import com.splitet.accountexample.exception.AccountException;
import com.splitet.accountexample.resource.model.CreateAccountDto;
import io.splitet.core.common.EventKey;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface AccountCommandResource {

    @PostMapping(value = "/accounts")
    EventKey createAccount(@RequestBody CreateAccountDto dto) throws AccountException;

    @PostMapping(value = "/accounts/{accountId}/activate")
    EventKey activateAccount(@PathVariable("accountId") String accountId) throws AccountException;
}
