package com.cloud.license.controller;

import com.cloud.license.model.util.ErrorMessage;
import com.cloud.license.model.util.ResponseWrapper;
import com.cloud.license.model.util.RestErrorList;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.util.Collections.singletonMap;

/**
 * ExceptionController.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@ControllerAdvice
@EnableWebMvc
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @SuppressWarnings("unused")
    public @ResponseBody ResponseEntity<ResponseWrapper> handleException(
            HttpServletRequest request, Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // ← 500
                .body(new ResponseWrapper(null, null, null));
    }

    @ExceptionHandler(Exception.class)
    @SuppressWarnings("unused")
    public ResponseEntity<ResponseWrapper> handleIOException(
            HttpServletRequest request, Exception e) {
        RestErrorList errorList = new RestErrorList(
                HttpStatus.NOT_ACCEPTABLE,
                new ErrorMessage(e.getMessage(), e.getMessage()));
        ResponseWrapper responseWrapper = new ResponseWrapper(
                null, singletonMap("status", HttpStatus.NOT_ACCEPTABLE), errorList);
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE) // ← 406 вместо 200
                .body(responseWrapper);
    }

}
