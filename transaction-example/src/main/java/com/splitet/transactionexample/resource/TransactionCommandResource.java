package com.splitet.transactionexample.resource;

import com.splitet.transactionexample.exception.TransactionException;
import com.splitet.transactionexample.resource.model.CreateTransactionDto;
import io.splitet.core.common.EventKey;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface TransactionCommandResource {

	@PostMapping(value = "/transactions")
	EventKey createTransaction(@RequestBody CreateTransactionDto dto) throws TransactionException;

	@PutMapping(value = "/transactions/{transactionId}")
	EventKey approveTransaction(@PathVariable("transactionId") String transactionId) throws TransactionException;

	@DeleteMapping(value = "/transactions/{transactionId}")
	EventKey cancelTransaction(@PathVariable("transactionId") String transactionId) throws TransactionException;
}