package com.mybank.exception;

public class NotEnoughCreditCardSum extends RuntimeException {
    public NotEnoughCreditCardSum(String message){
        super(message);
    }
}
