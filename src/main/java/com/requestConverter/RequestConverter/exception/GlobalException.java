package com.requestConverter.RequestConverter.exception;

import com.requestConverter.RequestConverter.util.AppContext;
import com.requestConverter.RequestConverter.util.CommonBody;
import com.requestConverter.RequestConverter.util.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler({InvalidUsernameException.class})
    public ResponseEntity<Object> handleInvalidUsername(InvalidUsernameException e) {
        Errors errors = new Errors();
        String invoked = "Invoked by " + AppContext.getAppContext().getUsername() + ",";
        errors.setMessage(invoked + e.getMessage());
        errors.setDetails(Optional.ofNullable(e.getCause()).map(Throwable::getMessage).orElse(e.getMessage()));
        return new ResponseEntity<>(new CommonBody<>(errors).setMessage(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInvalidUsername(Exception e) {
        Errors errors = new Errors();
        String invoked = "[Exception]: Invoked by: " + AppContext.getAppContext().getUsername() + ",";
        errors.setMessage(invoked + e.getMessage());
        errors.setDetails(Optional.ofNullable(e.getCause()).map(Throwable::getMessage).orElse("GLOBAL EXCEPTION"));
        return new ResponseEntity<>(new CommonBody<>(errors).setMessage(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
