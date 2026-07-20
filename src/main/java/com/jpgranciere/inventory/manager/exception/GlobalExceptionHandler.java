package com.jpgranciere.inventory.manager.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerProductNotFoundException(ProductNotFoundException ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(HttpStatus.NOT_FOUND, ex, httpServletRequest));
    }

    @ExceptionHandler(DuplicateSkuException.class)
    public ResponseEntity<ErrorResponse> handlerDuplicateSkuException(DuplicateSkuException ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildErrorResponse(HttpStatus.CONFLICT, ex, httpServletRequest));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handlerInsufficientStockException(InsufficientStockException ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(HttpStatus.BAD_REQUEST, ex, httpServletRequest));
    }

    @ExceptionHandler(StockMovementNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerStockMovementNotFoundException(StockMovementNotFoundException ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(HttpStatus.NOT_FOUND, ex, httpServletRequest));
    }

    @ExceptionHandler(PaymentMethodRequiredException.class)
    public ResponseEntity<ErrorResponse> handlerPaymentMethodRequiredException(PaymentMethodRequiredException ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(buildErrorResponse(HttpStatus.PAYMENT_REQUIRED, ex, httpServletRequest));
    }

    @ExceptionHandler(InsufficientPaymentException.class)
    public ResponseEntity<ErrorResponse> handlerInsufficientPaymentException(InsufficientPaymentException ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(buildErrorResponse(HttpStatus.PAYMENT_REQUIRED, ex, httpServletRequest));
    }

    @ExceptionHandler(ReferenceDateExistisException.class)
    public ResponseEntity<ErrorResponse> handlerReferenceDateExistisException(ReferenceDateExistisException ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildErrorResponse(HttpStatus.CONFLICT, ex, httpServletRequest));
    }

    @ExceptionHandler(DateFutureExeception.class)
    public ResponseEntity<ErrorResponse> handlerDateFutureExeception(DateFutureExeception ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(HttpStatus.BAD_REQUEST, ex, httpServletRequest));
    }

    @ExceptionHandler(SalesNotFound.class)
    public ResponseEntity<ErrorResponse> handlerSalesNotFoundForReferenceDateException(SalesNotFound ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(HttpStatus.NOT_FOUND, ex, httpServletRequest));
    }

    @ExceptionHandler(ProductInactiveException.class)
    public ResponseEntity<ErrorResponse> handlerProductInactiveException(ProductInactiveException ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(HttpStatus.BAD_REQUEST, ex, httpServletRequest));
    }

    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidPaymentException(InvalidPaymentException ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(buildErrorResponse(HttpStatus.PAYMENT_REQUIRED, ex, httpServletRequest));
    }

    @ExceptionHandler(InsufficientQuantityException.class)
    public ResponseEntity<ErrorResponse> handlerInsufficientQuantityException(InsufficientQuantityException ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(HttpStatus.BAD_REQUEST, ex, httpServletRequest));
    }

    @ExceptionHandler(StatusNotExists.class)
    public ResponseEntity<ErrorResponse> handlerStatusNotExists(StatusNotExists ex, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(HttpStatus.NOT_FOUND, ex, httpServletRequest));
    }

    private ErrorResponse buildErrorResponse(HttpStatus status, Exception ex, HttpServletRequest request){
        return new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    }
}
