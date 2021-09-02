package com.splitet.transactionexample.command.model;

import com.splitet.transactionexample.entity.TransactionType;
import com.splitet.transactionexample.resource.model.endpoint.Endpoint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepositTransactionCommand {

    private Endpoint target;

    private BigDecimal amount;

    private TransactionType type = TransactionType.DEPOSIT;
}
