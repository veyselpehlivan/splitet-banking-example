package com.splitet.accountexample.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.splitet.accountexample.entity.Account;
import com.splitet.accountexample.repository.AccountRepository;
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
    AggregateListener snapshotRecAccount(ViewQuery<Account> accountViewRepository, // View Query with Function Specs
                                         EventRepository accountEventRepository, // Event Repository to mark failed events
                                         AccountRepository accountRepository,      // Jpa Repository to record snapshots
                                         Optional<List<RollbackSpec>> rollbackSpecs // Custom Rollback Specs for Event Failures
    ) {
        return new AggregateListener(accountViewRepository, accountEventRepository, accountRepository, rollbackSpecs.orElseGet(ArrayList::new), objectMapper);
    }

    @Bean
    ViewQuery<Account> accountViewRepository(List<EntityFunctionSpec<Account, ?>> functionSpecs, EventApisConfiguration eventApisConfiguration) {
        return new CassandraViewQuery<>(
                eventApisConfiguration.getTableNameForEvents("account"),
                cassandraSession, objectMapper, functionSpecs);
    }

    @Bean
    EventRecorder accountPersistentEventRepository(EventApisConfiguration eventApisConfiguration, IUserContext userContext) {
        return new CassandraEventRecorder(eventApisConfiguration.getTableNameForEvents("account"), cassandraSession, operationContext, userContext, new ObjectMapper());
    }

    @Bean
    EventRepository accountEventRepository(EventRecorder accountEventRecorder, IOperationRepository operationRepository) {
        return new CompositeRepositoryImpl(accountEventRecorder, new ObjectMapper(), operationRepository);
    }

    @Bean
    DataMigrationService dataMigrationService(EventRecorder accountEventRecorder, ViewQuery<Account> accountViewQuery, AccountRepository accountRepository) {
        return new DataMigrationService(accountEventRecorder, accountViewQuery, accountRepository);
    }


}

