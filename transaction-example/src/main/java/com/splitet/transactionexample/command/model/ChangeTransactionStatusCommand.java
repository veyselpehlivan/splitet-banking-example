package com.splitet.transactionexample.command.model;

import com.splitet.transactionexample.entity.TransactionStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChangeTransactionStatusCommand {

	private final String transactionId;

	private final TransactionStatus status;
}