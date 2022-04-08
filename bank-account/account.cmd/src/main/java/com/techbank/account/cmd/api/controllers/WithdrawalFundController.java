package com.techbank.account.cmd.api.controllers;

import com.techbank.account.cmd.api.commands.DepositFundsCommand;
import com.techbank.account.cmd.api.commands.WithdrewFoundsCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/withdrawalFunds")
public class WithdrawalFundController {

    private final Logger logger = Logger.getLogger(DepositFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> withdrawalFund(@PathVariable String id, @RequestBody WithdrewFoundsCommand command){
        try{
            command.setId(id);
            logger.log(Level.INFO, "account id +" +id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Withdrew Founds account"), HttpStatus.OK);
        } catch (IllegalStateException e){
            logger.log(Level.WARNING, MessageFormat.format("Bad request {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse("Fail to WithdrewFoundsCommand"), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            var safeErrorMessage = MessageFormat.format("Error on WithdrewFoundsCommand for {0}", id);
            logger.log(Level.SEVERE, e.toString());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
