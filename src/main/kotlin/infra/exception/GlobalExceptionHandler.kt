package infra.exception

import infra.exception.dto.ErrorDTO
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
            .body(ErrorDTO(message = e.message))
    }
}