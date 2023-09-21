package com.example.aston.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class AstonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception e, WebRequest request) throws Exception {
        log.error("Exception during execution of application", e);
        return handleException(e, request);
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> badRequestException(BadRequestException e) {
        log.error("Bad Request Exception: ", e);
        ErrorDto dto = getResponseErrorDto(e);
        return ResponseEntity.status(dto.getErrorStatus()).body(dto);
    }

    private ErrorDto getResponseErrorDto(BadRequestException e) {
        return ErrorDto.builder().
                error(e.getMessage()).
                errorStatus(e.getStatus())
                .build();
    }
}

