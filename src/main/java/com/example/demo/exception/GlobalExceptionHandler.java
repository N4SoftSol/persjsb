package com.example.demo.exception;

import com.example.demo.dto.ErrorResponse;

import com.example.demo.dto.JwtUserInfo;
import com.example.demo.service.AuditService;
import com.example.demo.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authorization.AuthorizationDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final AuditService auditService;

    public GlobalExceptionHandler(
            AuditService auditService) {

        this.auditService = auditService;
    }

    @ExceptionHandler(
            IndividualNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleNotFound(
            IndividualNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponse(
                                LocalDateTime.now(),
                                404,
                                "NOT_FOUND",
                                ex.getMessage(),
                                request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponse(
                                LocalDateTime.now(),
                                500,
                                "INTERNAL_SERVER_ERROR",
                                ex.getMessage(),
                                request.getRequestURI()));
    }

    @ExceptionHandler(
            AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse>
    handleAccessDenied(
            AuthorizationDeniedException ex,
            HttpServletRequest request,
            Authentication authentication) {

        String username =
                authentication != null
                        ? authentication.getName()
                        : "UNKNOWN";
        JwtUserInfo userInfo =
                JwtUtils.getUserInfo(
                        authentication);

        auditService.log(
                userInfo.username(),
                userInfo.displayName(),
                userInfo.email(),
                request.getMethod(),
                "PERSON",
                "DENIED",
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new ErrorResponse(
                                LocalDateTime.now(),
                                403,
                                "FORBIDDEN",
                                "Access Denied",
                                request.getRequestURI()));
    }
}