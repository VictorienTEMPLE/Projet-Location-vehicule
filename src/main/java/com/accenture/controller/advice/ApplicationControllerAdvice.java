package com.accenture.controller.advice;
import com.accenture.exception.ClientException;
import com.accenture.service.dto.ErreurResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

//classe permettant de gerer les erreurs
public class ApplicationControllerAdvice {

        @ExceptionHandler(ClientException.class)
        public ResponseEntity<ErreurResponse> gestionClientException(ClientException ex){
            ErreurResponse er = new ErreurResponse(LocalDateTime.now(),"ErreurFonctionnelle",ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ErreurResponse> entityNotFoundException(EntityNotFoundException ex){
            ErreurResponse er = new ErreurResponse(LocalDateTime.now(),"ErreurFonctionnelle",ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public  ResponseEntity<ErreurResponse>problemValidation(MethodArgumentNotValidException ex){
            String message = ex.getBindingResult().getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(","));

            ErreurResponse er = new ErreurResponse(LocalDateTime.now(),"Validation error",message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }
    }


