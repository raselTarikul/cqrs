package com.techbank.account.query.api.query;

import com.techbank.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAllAccountsQuery query);
    List<BaseEntity> handle(FindAccountsIdQuery query);
    List<BaseEntity> handle(FindAccountByHolderQuery query);
}
