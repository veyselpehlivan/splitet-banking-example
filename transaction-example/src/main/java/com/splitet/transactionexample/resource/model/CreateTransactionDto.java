package com.splitet.transactionexample.resource.model;

import com.splitet.transactionexample.entity.TransactionType;
import com.splitet.transactionexample.resource.model.endpoint.Endpoint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTransactionDto {

	private Endpoint source;

	private Endpoint target;

	@NotNull
	private TransactionType type;

	@DecimalMin("0.01")
	private BigDecimal amount;

}
