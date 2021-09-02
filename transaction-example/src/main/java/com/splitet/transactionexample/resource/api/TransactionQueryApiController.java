package com.splitet.transactionexample.resource.api;

import com.querydsl.core.types.Predicate;
import com.splitet.transactionexample.entity.Transaction;
import com.splitet.transactionexample.exception.TransactionException;
import com.splitet.transactionexample.repository.TransactionRepository;
import com.splitet.transactionexample.resource.TransactionQueryResource;
import io.splitet.core.api.ViewQuery;
import io.splitet.core.cassandra.EntityEvent;
import io.splitet.core.exception.EventStoreException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionQueryApiController implements TransactionQueryResource {

	private final ViewQuery<Transaction> viewQuery;

	private final TransactionRepository transactionRepository;

	@Override
	@SneakyThrows
	public Transaction findTransactionById(String transactionId) {
		return transactionRepository.findById(transactionId).orElseThrow(() -> new TransactionException(String.format("There is no account with %s", transactionId)));
	}

	@Override
	public List<EntityEvent> loadTransactionHistoryByTransactionId(String transactionId) throws TransactionException {
		List<EntityEvent> history;
		try {
			history = viewQuery.queryHistory(transactionId);
		}
		catch (EventStoreException exception) {
			log.error("Exception is caught while querying acount with {}", transactionId);
			throw new TransactionException(String.format("There is no account with %s", transactionId));
		}
		return history;
	}

	@Override
	public Transaction findAccountByIdAndVersion(String transactionId, Integer version) throws TransactionException {
		Transaction account;
		try {
			account = Objects.isNull(version) ? viewQuery.queryEntity(transactionId) : viewQuery
					.queryEntity(transactionId, version);
		}
		catch (EventStoreException exception) {
			log.error("Exception is caught while querying acount with {}, {}", transactionId, version);
			throw new TransactionException(String
					.format("There is no account with %s id and %s version", transactionId, version));
		}
		return account;
	}

	@Override
	public Page<Transaction> findTransactions(Predicate predicate, Pageable pageable) {
		return transactionRepository.findAll(predicate, pageable);
	}
}
