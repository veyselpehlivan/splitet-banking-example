package com.splitet.transactionexample.resource;

import com.querydsl.core.types.Predicate;
import com.splitet.transactionexample.entity.Transaction;
import com.splitet.transactionexample.exception.TransactionException;
import io.splitet.core.cassandra.EntityEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionQueryResource {
	@GetMapping(value = "/transactions/{transactionId}")
	Transaction findTransactionById(@PathVariable("transactionId") String transactionId);

	@GetMapping(value = "/accounts/{accountId}/history")
	List<EntityEvent> loadTransactionHistoryByTransactionId(@PathVariable("transactionId") String transactionId) throws TransactionException;

	@GetMapping(value = "/transactions/{transactionId}/{version}")
	Transaction findAccountByIdAndVersion(@PathVariable("transactionId") String transactionId, @PathVariable("version") Integer version) throws TransactionException;

	@GetMapping(value = "/transactions")
	Page<Transaction> findTransactions(@QuerydslPredicate(root = Transaction.class) Predicate predicate, Pageable pageable);
}
