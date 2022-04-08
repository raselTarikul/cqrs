package com.techbank.account.query.api.controllers;

import com.techbank.account.query.api.dto.AccountLookupResponse;
import com.techbank.account.query.api.query.FindAccountByHolderQuery;
import com.techbank.account.query.api.query.FindAllAccountsQuery;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/bankAccountLookup")
public class AccountLookupControllers {
    private final Logger logger = Logger.getLogger(AccountLookupControllers.class.getName());

    @Autowired
    private QueryDispatcher dispatcher;

    @GetMapping("/")
    public ResponseEntity<AccountLookupResponse> getAll(){
        try{
             List<BankAccount> accounts = dispatcher.send(new FindAllAccountsQuery());
             if(accounts == null || accounts.size() == 0){
                 return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
             } else {
                 var response = AccountLookupResponse.builder()
                         .accounts(accounts)
                         .message("Accounts Found")
                         .build();
                 return new ResponseEntity<>(response, HttpStatus.OK);
             }
        } catch (Exception e){
            logger.log(Level.SEVERE, e.toString());
            return new ResponseEntity<>(new AccountLookupResponse("Failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
