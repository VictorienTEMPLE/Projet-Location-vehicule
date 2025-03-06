package com.accenture.controller.advice;
import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.service.dto.ErreurResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
//classe permettant de gerer les erreurs
@Slf4j
@ControllerAdvice
public class ApplicationControllerAdvice {

        @ExceptionHandler(ClientException.class)
        public ResponseEntity<ErreurResponse> gestionClientException(ClientException ex){
            ErreurResponse er = new ErreurResponse(LocalDateTime.now(),"ErreurFonctionnelle",ex.getMessage());
            log.error("Erreur lors de l'ajout du client : {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
        }

        @ExceptionHandler(AdministrateurException.class)
        public ResponseEntity<ErreurResponse> gestionClientException(AdministrateurException ex){
        ErreurResponse er = new ErreurResponse(LocalDateTime.now(),"ErreurFonctionnelle",ex.getMessage());
        log.error("Erreur lors de l'ajout de l'administrateur : {}", ex.getMessage(), ex);
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


