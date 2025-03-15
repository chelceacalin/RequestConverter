package com.requestConverter.RequestConverter.controller;

import com.requestConverter.RequestConverter.domain.TestBase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController extends BaseController {

    @GetMapping("/test")

    public ResponseEntity<Object> init() {
        return buildOkResponse();
    }

    @GetMapping("/testBase")
    public ResponseEntity<Object> init(@RequestBody TestBase testBase) {
        return buildOkResponse(testBase);
    }
}
