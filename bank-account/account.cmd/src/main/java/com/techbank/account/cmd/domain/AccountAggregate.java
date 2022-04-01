package com.techbank.account.cmd.domain;

import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundDepositedEvent;
import com.techbank.account.common.events.FundWithDrownEvent;
import com.techbank.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private Double balance;

    public double getBalance(){
        return this.balance;
    }

    public AccountAggregate(OpenAccountCommand command){
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event){
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFund(double amount){
        if(!this.active){
            throw new IllegalStateException("Fund cannot be deposited a closed account");
        }
        if(amount <= 0 ){
            throw new IllegalStateException("Deposit amount should be Grater than zero");
        }
        raiseEvent(FundDepositedEvent.builder()
                .amount(amount)
                .build());
    }

    public void apply(FundDepositedEvent event){
        this.id = event.getId();
        this.balance += event.getAmount();
    }
    
    public void withdrawFund(double amount){
        if(!this.active){
            throw new IllegalStateException("Fund cannot be witdrown a closed account");
        }
        if(this.balance < amount){
            throw new IllegalStateException("Not enough Balance");
        }
        raiseEvent(FundWithDrownEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundWithDrownEvent event){
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount(){
        if(!this.active){
            throw new IllegalStateException("The account already closed");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }

}
