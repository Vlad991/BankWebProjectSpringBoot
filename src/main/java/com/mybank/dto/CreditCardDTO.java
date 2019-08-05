package com.mybank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mybank.entity.CreditCardStatus;
import com.mybank.entity.User;
import com.mybank.entity.carddate.CreditCardDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardDTO {
    @NotNull(message = "Id is required")
    private Long id;   // card number

    @NotNull(message = "Date is required")
    private CreditCardDate date;

    @NotNull(message = "Client is required")
    private User client;

    @NotNull(message = "Code is required")
    private int code;

    private int sum;

    @NotNull(message = "Status is required")
    private CreditCardStatus status;

    @NotNull(message = "Pin is required")
    private int pin;     // 0000 todo (validator)
}
