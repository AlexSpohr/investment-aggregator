package com.projects.investmentaggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InvestmentAggregatorException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pd.setDetail("Investment aggregator internal server error");

        return pd;
    }
}
