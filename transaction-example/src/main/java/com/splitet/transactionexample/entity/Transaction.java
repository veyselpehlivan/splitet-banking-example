package com.splitet.transactionexample.entity;

import io.splitet.core.spring.model.JpaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TRANSACTIONS")
public class Transaction extends JpaEntity {

	private String sourceAccount;

	private String targetAccount;

	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionStatus;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
}
