package com.splitet.transactionexample.event.spec;

import com.splitet.transactionexample.entity.Transaction;
import com.splitet.transactionexample.entity.TransactionStatus;
import com.splitet.transactionexample.entity.TransactionType;
import com.splitet.transactionexample.event.model.published.DepositTransactionEvent;
import io.splitet.core.view.EntityFunctionSpec;
import org.springframework.stereotype.Component;

@Component
public class DepositTransactionSpec extends EntityFunctionSpec<Transaction, DepositTransactionEvent> {
	public DepositTransactionSpec() {
		super((transaction, event) -> {
			DepositTransactionEvent eventData = event.getEventData();
			transaction.setAmount(eventData.getAmount());
			transaction.setTransactionStatus(TransactionStatus.STARTED);
			transaction.setTransactionType(TransactionType.DEPOSIT);
			transaction.setTargetAccount(eventData.getAccountId());
			return transaction;
		});
	}
}