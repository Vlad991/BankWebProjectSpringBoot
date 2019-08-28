package com.mybank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class SendSumDTO {
    @NotNull(message = "Sender is required.")
    private String senderCardNumber;
    @NotNull(message = "Receiver id required.")
    private String receiverCardNumber;
    @Min(0)
    private int sum;
}
