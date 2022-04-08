package com.techbank.account.query.api.query;

import com.techbank.cqrs.core.quries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountsIdQuery extends BaseQuery {
    private String id;
}
