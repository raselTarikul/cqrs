package com.techbank.account.query;

import com.techbank.account.query.api.query.FindAccountByHolderQuery;
import com.techbank.account.query.api.query.FindAccountsIdQuery;
import com.techbank.account.query.api.query.FindAllAccountsQuery;
import com.techbank.account.query.api.query.QueryHandler;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class QueryApplication {

	@Autowired
	private QueryDispatcher queryDispatcher;

	@Autowired
	private QueryHandler queryHandler;

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	void registerHandlers(){
		queryDispatcher.registerHandlers(FindAllAccountsQuery.class, queryHandler::handle);
		queryDispatcher.registerHandlers(FindAccountsIdQuery.class, queryHandler::handle);
		queryDispatcher.registerHandlers(FindAccountByHolderQuery.class, queryHandler::handle);
	}

}
