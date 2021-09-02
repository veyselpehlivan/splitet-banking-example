package com.splitet.accountexample.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountCommand {

    private String userId;

    private BigDecimal balance;

    private String currency;
}
