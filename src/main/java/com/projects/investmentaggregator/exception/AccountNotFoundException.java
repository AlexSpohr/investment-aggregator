package com.projects.investmentaggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AccountNotFoundException extends InvestmentAggregatorException {

    private final String accountId;

    public AccountNotFoundException(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pd.setDetail("Account not found");
        pd.setDetail("There is no account associated with the specified account id " + accountId + ".");


        return pd;
    }
}
