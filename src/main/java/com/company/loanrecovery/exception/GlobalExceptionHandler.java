
package com.company.loanrecovery.exception;

import com.company.loanrecovery.common.ApiResponse;

import org.springframework.security.access.AccessDeniedException;
import tools.jackson.databind.exc.InvalidFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);


    // 1. DUPLICATE RESOURCE - 409 CONFLICT

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateResource(
            DuplicateResourceException ex) {

        log.warn("Duplicate resource: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                ));
    }


    // 2. EMPLOYEE NOT FOUND - 404 NOT FOUND

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmployeeNotFound(
            EmployeeNotFoundException ex) {

        log.warn("Employee not found: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                ));
    }


    // 3. CASE NOT FOUND - 404 NOT FOUND

    @ExceptionHandler(CaseNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCaseNotFound(
            CaseNotFoundException ex) {

        log.warn("Case not found: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                ));
    }


    // 4. ASSIGNMENT NOT FOUND - 404 NOT FOUND

    @ExceptionHandler(AssignmentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleAssignmentNotFound(
            AssignmentNotFoundException ex) {

        log.warn("Assignment not found: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                ));
    }


    // 5. REQUEST VALIDATION ERRORS - 400 BAD REQUEST

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(
                        error.getField(),
                        error.getDefaultMessage()
                )
        );

        log.warn("Request validation failed: {}", errors);

        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(
                        false,
                        "Validation failed",
                        errors
                ));
    }


    // 6. INVALID ENUM / INVALID JSON - 400 BAD REQUEST

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidJson(
            HttpMessageNotReadableException ex) {

        String message = getInvalidJsonMessage(ex);

        log.warn("Invalid request body: {}", message);

        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(
                        false,
                        message,
                        null
                ));
    }


    // HELPER METHOD FOR ENUM VALUES AND INVALID JSON

    private String getInvalidJsonMessage(
            HttpMessageNotReadableException ex) {

        Throwable cause = ex;

        while (cause != null) {

            if (cause instanceof InvalidFormatException invalidFormat) {

                Class<?> targetType = invalidFormat.getTargetType();

                if (targetType.isEnum()) {

                    String allowedValues = Arrays.stream(
                                    targetType.getEnumConstants()
                            )
                            .map(value -> ((Enum<?>) value).name())
                            .collect(Collectors.joining(", "));

                    return "Invalid value '" + invalidFormat.getValue()
                            + "'. Allowed values: " + allowedValues;
                }
            }

            cause = cause.getCause();
        }

        return "Invalid JSON request. Please check the request body and data types.";
    }


    // 7. INVALID BUSINESS OPERATION - 400 BAD REQUEST

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(
            IllegalArgumentException ex) {

        log.warn("Invalid operation: {}", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                ));
    }


    // 8. DATABASE CONSTRAINT VIOLATION - 409 CONFLICT

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        log.error("Database constraint violation", ex);

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(
                        false,
                        "Database constraint violation. Check duplicate or conflicting records.",
                        null
                ));
    }


    // 9. UNEXPECTED SERVER ERROR - 500 INTERNAL SERVER ERROR

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(
            Exception ex) {

        log.error("Unexpected application error", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(
                        false,
                        "An unexpected server error occurred. Please try again later.",
                        null
                ));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, "Invalid credentials or token", null));
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(
            AccessDeniedException ex) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>(
                        false,
                        "You do not have permission to access this resource",
                        null
                ));
    }
}
