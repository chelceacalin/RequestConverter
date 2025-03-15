package com.requestConverter.RequestConverter.controller;

import com.requestConverter.RequestConverter.util.CommonBody;
import org.springframework.http.ResponseEntity;

public class BaseController {

    String SUCCESS = "SUCCESS";

    protected ResponseEntity<Object> buildOkResponse() {
        return ResponseEntity.ok(new CommonBody<>(SUCCESS));
    }

    protected ResponseEntity<Object> buildOkResponse(Object data) {
        CommonBody<Object> body = new CommonBody<>(SUCCESS, data);
        return ResponseEntity.ok(body);
    }
}
