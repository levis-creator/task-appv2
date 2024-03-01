package com.micosoft.simpletaskapp.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class CusException {
    private final  String message;
    private final Throwable throwable;
    private  final HttpStatus httpStatus;
}
