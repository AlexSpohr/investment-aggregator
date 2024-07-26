package com.projects.investmentaggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class BrapiErrorException extends InvestmentAggregatorException {

    private final String stockId;
    private final HttpStatus status = null;

    public BrapiErrorException(String stockId) {
        this.stockId = stockId;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.SERVICE_UNAVAILABLE);

        pd.setDetail("Error Brapi Api");
        pd.setDetail("Error fetching quote for stock ID: " + stockId + ". The Brapi service is currently unavailable.");


        return pd;
    }
}
