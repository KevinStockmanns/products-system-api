package com.products.error;

import lombok.Getter;

@Getter
public class GenericError extends RuntimeException {
    private String campo;

    public GenericError(String campo, String message){
        super(message);
        this.campo = campo;
    }
}
