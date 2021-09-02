package com.splitet.transactionexample.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.splitet.transactionexample.entity.Transaction;
import com.splitet.transactionexample.repository.TransactionRepository;
import io.splitet.core.api.EventRepository;
import io.splitet.core.api.IUserContext;
import io.splitet.core.api.RollbackSpec;
import io.splitet.core.api.ViewQuery;
import io.splitet.core.cassandra.CassandraEventRecorder;
import io.splitet.core.cassandra.CassandraSession;
import io.splitet.core.cassandra.CassandraViewQuery;
import io.splitet.core.common.EventRecorder;
import io.splitet.core.common.OperationContext;
import io.splitet.core.core.CompositeRepositoryImpl;
import io.splitet.core.kafka.IOperationRepository;
import io.splitet.core.spring.configuration.DataMigrationService;
import io.splitet.core.spring.configuration.EventApisConfiguration;
import io.splitet.core.view.AggregateListener;
import io.splitet.core.view.EntityFunctionSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
public class SplitetConfig {

    @Autowired
    private CassandraSession cassandraSession;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OperationContext operationContext;

    @Bean
    AggregateListener snapshotRecTransaction(ViewQuery<Transaction> transactionViewRepository, // View Query with Function Specs
                                             EventRepository transactionEventRepository, // Event Repository to mark failed events
                                             TransactionRepository transactionRepository,      // Jpa Repository to record snapshots
                                             Optional<List<RollbackSpec>> rollbackSpecs // Custom Rollback Specs for Event Failures
    ) {
        return new AggregateListener(transactionViewRepository, transactionEventRepository, transactionRepository, rollbackSpecs.orElseGet(ArrayList::new), objectMapper);
    }

    @Bean
    ViewQuery<Transaction> TransactionViewRepository(List<EntityFunctionSpec<Transaction, ?>> functionSpecs, EventApisConfiguration eventApisConfiguration) {
        return new CassandraViewQuery<>(
                eventApisConfiguration.getTableNameForEvents("transaction"),
                cassandraSession, objectMapper, functionSpecs);
    }

    @Bean
    EventRecorder TransactionPersistentEventRepository(EventApisConfiguration eventApisConfiguration, IUserContext userContext) {
        return new CassandraEventRecorder(eventApisConfiguration.getTableNameForEvents("transaction"), cassandraSession, operationContext, userContext, new ObjectMapper());
    }

    @Bean
    EventRepository TransactionEventRepository(EventRecorder transactionEventRecorder, IOperationRepository operationRepository) {
        return new CompositeRepositoryImpl(transactionEventRecorder, new ObjectMapper(), operationRepository);
    }

    @Bean
    DataMigrationService dataMigrationService(EventRecorder transactionEventRecorder, ViewQuery<Transaction> transactionViewQuery, TransactionRepository transactionRepository) {
        return new DataMigrationService(transactionEventRecorder, transactionViewQuery, transactionRepository);
    }


}

