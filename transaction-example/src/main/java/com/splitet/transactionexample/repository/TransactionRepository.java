package com.splitet.transactionexample.repository;

import com.splitet.transactionexample.entity.Transaction;
import io.splitet.core.view.SnapshotRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>, QuerydslPredicateExecutor<Transaction>, SnapshotRepository<Transaction, String> {

}
