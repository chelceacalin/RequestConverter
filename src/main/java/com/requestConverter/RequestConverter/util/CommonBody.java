package com.requestConverter.RequestConverter.util;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommonBody<T> {
    private String message;
    private T data;
    private Errors errors;
    private String requester;

    public CommonBody(String message, T data) {
        requester = AppContext.getAppContext().getUsername();
        this.message = message;
        this.data = data;
    }

    public CommonBody(String message) {
        requester = AppContext.getAppContext().getUsername();
        this.message = message;
    }

    public CommonBody(Errors errors) {
        requester = AppContext.getAppContext().getUsername();
        this.errors = errors;
    }


}
