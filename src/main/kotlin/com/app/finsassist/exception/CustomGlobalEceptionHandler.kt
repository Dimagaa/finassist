package com.app.finsassist.exception

import java.time.LocalDateTime
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomGlobalExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST,
            errors = ex.bindingResult.allErrors.map { it.toErrorMessage() }
        )
        return ResponseEntity(errorResponse, headers, status)
    }

    @ExceptionHandler(UserCreationException::class)
    fun handleUserCreationException(
        ex: UserCreationException,
    ): ResponseEntity<Any> {
        val response = ErrorResponse(
            status = HttpStatus.BAD_REQUEST,
            errors = listOf(ex.message)
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(
        ex: EntityNotFoundException
    ): ResponseEntity<Any> {
        val response = ErrorResponse(
            status = HttpStatus.NOT_FOUND,
            errors = listOf(ex.message)
        )
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(exception: Exception): ResponseEntity<Any> {
        logger.error("Internal server error", exception)
        val response = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            errors = listOf("Internal server error")
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun ObjectError.toErrorMessage(): String? =
        if (this is FieldError) "$field $defaultMessage" else defaultMessage
}
