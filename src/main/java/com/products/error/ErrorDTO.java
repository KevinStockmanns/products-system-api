package com.products.error;

import org.springframework.validation.FieldError;

public record ErrorDTO(
    String campo,
    String error
) {
    public ErrorDTO(FieldError e){
        this(e.getField(), e.getDefaultMessage());
    }
}
