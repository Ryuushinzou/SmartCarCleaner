package com.scc.app.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CardDetails {

    private String cardHolderName;
    private String cardNo;
    private String cvv;
    private Date expirationDate;
}
