package com.mybank.exception.handler;

import com.mybank.dto.ErrorInfo;
import com.mybank.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerService {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            CardNotFoundException.class,
            CreditCardAlreadyExistsException.class,
            UserAlreadyExistsException.class,
            UserAlreadyBlockedException.class,
            UserNotFoundException.class,
            UserLoginNotNullException.class,
            UserNotBlockedException.class})
    @ResponseBody
    public ErrorInfo exceptionHandler(Exception ex){
        return new ErrorInfo().setTimestamp(System.currentTimeMillis())
                .setMessage(ex.getMessage())
                .setDeveloperMessage(ex.toString());
    }
}
