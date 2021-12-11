package ru.javawebinar.topjava.util.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ExceptionsUtil {

    public static ResponseEntity<String> hasErrors(BindingResult result) {
        String errorFieldsMsg = result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining("<br>"));
        return ResponseEntity.unprocessableEntity().body(errorFieldsMsg);
    }
}
