package com.example.todoapp.infra.exception

import com.example.todoapp.infra.exception.dto.ErrorDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ModelNotFoundException::class)
    fun handleModelNotFoundException(e: ModelNotFoundException): ResponseEntity<ErrorDTO>
    {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorDTO(e.message))
    }

    @ExceptionHandler(SecurityInfoNotMatchException::class)
    fun handlePasswordNotMatchException(e: SecurityInfoNotMatchException): ResponseEntity<ErrorDTO>
    {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorDTO(e.message))
    }
    @ExceptionHandler(IllegalStateException::class)
    fun handlerIllegalStateException(e: IllegalStateException): ResponseEntity<ErrorDTO>{
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ErrorDTO(e.message))
    }
    @ExceptionHandler(NotHaveAuthorityException::class)
    fun handlerIllegalStateException(e: NotHaveAuthorityException): ResponseEntity<ErrorDTO>{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorDTO(e.message))
    }
}