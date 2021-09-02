package com.splitet.accountexample.entity;

import io.splitet.core.spring.model.JpaEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity(name = "ACCOUNTS")
public class Account extends JpaEntity {

    private String userId;

    private BigDecimal balance;

    private String currency;

    @Enumerated(EnumType.STRING)
    private AccountState state;
}
