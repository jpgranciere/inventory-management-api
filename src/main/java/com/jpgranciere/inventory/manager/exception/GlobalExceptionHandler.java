package com.jpgranciere.inventory.manager.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(
            ProductNotFoundException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateSkuException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateSku(
            DuplicateSkuException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStock(
            InsufficientStockException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StockMovementNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStockMovementNotFound(
            StockMovementNotFoundException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentMethodRequiredException.class)
    public ResponseEntity<ErrorResponse> handlePaymentMethodRequired(
            PaymentMethodRequiredException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientPaymentException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientPayment(
            InsufficientPaymentException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ReferenceDateExistisException.class)
    public ResponseEntity<ErrorResponse> handleReferenceDateExists(
            ReferenceDateExistisException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DateFutureException.class)
    public ResponseEntity<ErrorResponse> handleFutureDate(
            DateFutureException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SalesNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSalesNotFound(
            SalesNotFoundException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductInactiveException.class)
    public ResponseEntity<ErrorResponse> handleProductInactive(
            ProductInactiveException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPayment(
            InvalidPaymentException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InsufficientQuantityException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientQuantity(
            InsufficientQuantityException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StatusNotExistsException.class)
    public ResponseEntity<ErrorResponse> handleStatusNotExists(
            StatusNotExistsException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CashRegisterAlreadyClosedException.class)
    public ResponseEntity<ErrorResponse> handleCashRegisterAlreadyClosed(
            CashRegisterAlreadyClosedException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CashRegisterNotOpenException.class)
    public ResponseEntity<ErrorResponse> handleCashRegisterNotOpen(
            CashRegisterNotOpenException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CashRegisterAlreadyOpenException.class)
    public ResponseEntity<ErrorResponse> handleCashRegisterAlreadyOpen(
            CashRegisterAlreadyOpenException ex,
            HttpServletRequest request
    ) {
        return handleBusinessException(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyRegistrationException.class)
    public ResponseEntity<ErrorResponse> handlerUserAlreadyRegistrationException(
            UserAlreadyRegistrationException ex,
            HttpServletRequest request
    ){
        return handleBusinessException(ex,request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(
            Exception ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error(
                "Erro inesperado: exception={} path={} status={} msg={}",
                ex.getClass().getSimpleName(),
                request.getRequestURI(),
                status.value(),
                ex.getMessage(),
                ex
        );

        return buildResponse(status, ex, request);
    }

    private ResponseEntity<ErrorResponse> handleBusinessException(
            Exception ex,
            HttpServletRequest request,
            HttpStatus status
    ) {
        log.warn(
                "Regra rejeitada: exception={} path={} status={} msg={}",
                ex.getClass().getSimpleName(),
                request.getRequestURI(),
                status.value(),
                ex.getMessage()
        );

        return buildResponse(status, ex, request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            Exception ex,
            HttpServletRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }
}