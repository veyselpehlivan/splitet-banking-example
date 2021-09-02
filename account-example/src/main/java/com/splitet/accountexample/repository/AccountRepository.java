package com.splitet.accountexample.repository;

import com.splitet.accountexample.entity.Account;
import io.splitet.core.view.SnapshotRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>, QuerydslPredicateExecutor<Account>, SnapshotRepository<Account, String> {

}
