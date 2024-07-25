package com.projects.investmentaggregator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;


public class UserEmailAlreadyExistException extends InvestmentAggregatorException {

    private String detail;

    public UserEmailAlreadyExistException(String detail) {
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pd.setTitle("User Email Already Exist");
        pd.setDetail(detail);

        return pd;
    }
}
