package com.projects.investmentaggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class UserNotFoundException extends InvestmentAggregatorException {

    private final String userId;

    public UserNotFoundException(String userId) {
        this.userId = userId;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pd.setDetail("User not found");
        pd.setDetail("There is no stock associated with the specified stock id " + userId + ".");


        return pd;
    }
}
