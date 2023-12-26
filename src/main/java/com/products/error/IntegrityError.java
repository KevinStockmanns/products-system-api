package com.products.error;

public class IntegrityError extends GenericError{

    public IntegrityError(String campo, String message) {
        super(campo, message);
    }
    
}
