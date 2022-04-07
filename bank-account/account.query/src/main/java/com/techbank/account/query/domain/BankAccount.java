package com.techbank.account.query.domain;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BankAccount extends BaseEvent {

    @Id
    private String id;
    private String accountHolder;
    private Date createDate;
    private AccountType accountType;
    private double balance;
}
