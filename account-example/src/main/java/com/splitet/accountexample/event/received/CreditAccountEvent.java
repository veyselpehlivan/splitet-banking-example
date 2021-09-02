package com.splitet.accountexample.event.received;


import io.splitet.core.common.ReceivedEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditAccountEvent extends ReceivedEvent {

    private String accountId;

    private BigDecimal amount;
}
