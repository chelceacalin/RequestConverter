package com.requestConverter.RequestConverter.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Errors {
    String message;
    String details;
}
