package com.splitet.transactionexample.resource.api;

import com.splitet.transactionexample.command.handler.ChangeTransactionStatusCommandHandler;
import com.splitet.transactionexample.command.handler.DepositTransactionCommandHandler;
import com.splitet.transactionexample.command.model.ChangeTransactionStatusCommand;
import com.splitet.transactionexample.command.model.DepositTransactionCommand;
import com.splitet.transactionexample.entity.TransactionStatus;
import com.splitet.transactionexample.entity.TransactionType;
import com.splitet.transactionexample.exception.TransactionException;
import com.splitet.transactionexample.resource.TransactionCommandResource;
import com.splitet.transactionexample.resource.model.CreateTransactionDto;
import io.splitet.core.common.EventKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionCommandApiController implements TransactionCommandResource {

	//private final TransactionMapper mapper;

	private final DepositTransactionCommandHandler depositTransactionCommandHandler;

	private final ChangeTransactionStatusCommandHandler approveTransactionStatusCommandHandler;

	@Override
	public EventKey createTransaction(CreateTransactionDto dto) throws TransactionException {
		EventKey eventKey = null;
		if (dto.getType() == TransactionType.DEPOSIT) {
			eventKey = depositTransactionCommandHandler.execute(toDepositMoneyCommand(dto));
		}

		return eventKey;
	}

	@Override
	public EventKey approveTransaction(String transactionId) throws TransactionException {
		return approveTransactionStatusCommandHandler
				.execute(ChangeTransactionStatusCommand.builder()
						.transactionId(transactionId)
						.status(TransactionStatus.APPROVED)
						.build());
	}

	@Override
	public EventKey cancelTransaction(String transactionId) throws TransactionException {
		return approveTransactionStatusCommandHandler
				.execute(ChangeTransactionStatusCommand.builder()
						.transactionId(transactionId)
						.status(TransactionStatus.CANCELED)
						.build());
	}

	private DepositTransactionCommand toDepositMoneyCommand(CreateTransactionDto dto) {
		return DepositTransactionCommand.builder()
				.amount(dto.getAmount())
				.target(dto.getTarget())
				.build();
	}
}
