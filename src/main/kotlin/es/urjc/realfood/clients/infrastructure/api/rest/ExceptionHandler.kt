package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.domain.exception.ClientNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ClientNotFoundException::class)
    fun resourceNotFoundException(ex: ConstraintViolationException): ResponseEntity<Error?>? {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Error.from(ex))
    }

    data class Error(val reason: String?) {
        companion object {
            fun from(ex: Exception): Error {
                return Error(ex.message)
            }
        }
    }

}