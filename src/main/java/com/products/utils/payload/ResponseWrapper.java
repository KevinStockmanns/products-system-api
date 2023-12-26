package com.products.utils.payload;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.products.error.ErrorDTO;

import lombok.Getter;

@Getter
public class ResponseWrapper<T> {
    private boolean ok;
    private int status;
    private String message;
    private List<ErrorDTO> errores;
    private T body;


    public ResponseWrapper(HttpStatus status, String message, List<ErrorDTO> errores, T body){
        this.ok = status.is2xxSuccessful();
        this.status = status.value();
        this.message = message;
        this.errores = errores;
        this.body = body;
    }
}
