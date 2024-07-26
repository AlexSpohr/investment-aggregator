package com.projects.investmentaggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class StockNotFoundException extends InvestmentAggregatorException {

    private final String stockId;

    public StockNotFoundException(String stockId) {
        this.stockId = stockId;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pd.setDetail("Stock not found");
        pd.setDetail("There is no stock associated with the specified stock id " + stockId + ".");

        return pd;
    }
}
