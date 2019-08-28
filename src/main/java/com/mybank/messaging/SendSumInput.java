package com.mybank.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SendSumInput {
    String INPUT = "send-event-input";  //chanel name

    @Input(INPUT)
    SubscribableChannel input();
}
