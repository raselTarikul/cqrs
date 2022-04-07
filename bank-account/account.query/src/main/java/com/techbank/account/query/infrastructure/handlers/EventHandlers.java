package com.techbank.account.query.infrastructure.handlers;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundDepositedEvent;
import com.techbank.account.common.events.FundWithDrownEvent;

public interface EventHandlers {
    void on(AccountOpenedEvent event);
    void on(FundDepositedEvent event);
    void on(FundWithDrownEvent event);
    void on(AccountClosedEvent event);
}
