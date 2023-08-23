package com.example.core.query;

import lombok.Data;


@Data
public class FetchUserPaymentDetailsQuery {
    private String userId;

    public FetchUserPaymentDetailsQuery(String userId) {
        this.userId = userId;
    }
}
