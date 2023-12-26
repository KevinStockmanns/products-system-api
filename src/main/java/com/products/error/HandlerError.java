package com.products.error;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.products.utils.payload.ResponseWrapper;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class HandlerError {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseWrapper<Void>> tratarJsonBody(HttpMessageNotReadableException e){

        String mensaje = "Ocurrio un error al leer el cuerpo de la petición";
        if(e.getMessage().contains("parse error")){
            String causa = e.getCause().toString();
            causa = causa.substring(causa.indexOf("DTO[\"")+5, causa.lastIndexOf("\"]"));
            String tipoDato = e.getMessage().substring(e.getMessage().indexOf("`")+1, e.getMessage().lastIndexOf("`"));
            tipoDato = tipoDato.substring(tipoDato.lastIndexOf(".") + 1);

            mensaje = String.format("Error al intentar convertir datos. El campo '%s' espera recibir un '%s'.", causa, tipoDato);
        }else if (e.getMessage().contains("Required request body is missing")) {
            mensaje = "El cuerpo de la petición es requerido.";
        }
        else
            e.printStackTrace();
        
        return ResponseEntity.badRequest().body(new ResponseWrapper<Void>(
            HttpStatus.BAD_REQUEST, 
            null, 
            List.of(new ErrorDTO(null, mensaje), new ErrorDTO(null, e.getMessage())), 
            null));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<ResponseWrapper<Void>> tratarErrorDB(DataIntegrityViolationException e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body(new ResponseWrapper<Void>(
            HttpStatus.BAD_REQUEST, 
            null, 
            List.of(new ErrorDTO("asd", e.getMessage())),
            null));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> tratarError404(EntityNotFoundException e){

        String mensaje = "No se logró encontrar la entidad en la base de datos.";
        if(e.getMessage().startsWith("IntegrityError: "))
            mensaje = e.getMessage().substring(16);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<Void>(
            HttpStatus.NOT_FOUND, 
            null, 
            List.of(new ErrorDTO(null, mensaje)),
            null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<Void>> tratarErrores(MethodArgumentNotValidException e){
        List<ErrorDTO> errores = e.getFieldErrors().stream().map(ErrorDTO::new).toList();
        return ResponseEntity.badRequest().body(new ResponseWrapper<Void>(
            HttpStatus.BAD_REQUEST, 
            null, errores, 
            null));
    }
    @ExceptionHandler(GenericError.class)
    public ResponseEntity<ResponseWrapper<Void>> tratarErrrorLogica(GenericError e){

        return ResponseEntity.badRequest().body(new ResponseWrapper<Void>(
            HttpStatus.BAD_REQUEST, 
            null, 
            List.of(new ErrorDTO(e.getCampo(), e.getMessage())), 
            null));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseWrapper<Void>> tratarUsuarioNotFound(UsernameNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<Void>(
            HttpStatus.NOT_FOUND, 
            null, 
            List.of(new ErrorDTO(null, e.getMessage())), 
            null));
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseWrapper<Void>> accesoDenegado(AccessDeniedException e){
        System.out.println("****************************");
        System.out.println(e.getMessage());
        System.out.println(e.getStackTrace());
        System.out.println(e.getSuppressed());
        System.out.println("****************************");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseWrapper<Void>(
            HttpStatus.FORBIDDEN, 
            null, 
            List.of(new ErrorDTO(null, "Acceso denegado, no tienes los permisos necesarios")), 
            null));
    }
    
}
