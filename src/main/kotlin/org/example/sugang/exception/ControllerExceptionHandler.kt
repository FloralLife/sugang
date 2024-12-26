package org.example.sugang.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

data class ErrorResponse(val code: String, val message: String)

@RestControllerAdvice
class ApiControllerAdvice : ResponseEntityExceptionHandler() {
  private val logger: Logger = LoggerFactory.getLogger(javaClass)

  @ExceptionHandler(IllegalArgumentException::class)
  fun handleException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
    logger.info(e.message)
    return ResponseEntity(
      ErrorResponse("400", "${e.message}"),
      HttpStatus.BAD_REQUEST,
    )
  }

  @ExceptionHandler(Exception::class)
  fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
    logger.error(e.message)
    return ResponseEntity(
      ErrorResponse("500", "${e.message}"),
      HttpStatus.INTERNAL_SERVER_ERROR,
    )
  }
}
