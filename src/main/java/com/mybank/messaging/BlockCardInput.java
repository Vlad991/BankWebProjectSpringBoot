package com.mybank.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface BlockCardInput {
    String INPUT = "block-event-input";  //chanel name

    @Input(INPUT)
    SubscribableChannel input();
}
