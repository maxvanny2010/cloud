package com.cloud.license.controller;

import com.cloud.license.model.util.ErrorMessage;
import com.cloud.license.model.util.ResponseWrapper;
import com.cloud.license.model.util.RestErrorList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletRequest;

import static java.util.Collections.*;

/**
 * ExceptionController.
 *
 * @author legion
 * @version 5.0
 * @since 17.11.2022
 */
@ControllerAdvice
@EnableWebMvc
public class ExceptionController extends ResponseEntityExceptionHandler {


    /**
     * handleException - Handles all the Exception receiving a request, responseWrapper.
     */
    @ExceptionHandler(value = {Exception.class})
    public @ResponseBody ResponseEntity<ResponseWrapper> handleException(HttpServletRequest request, ResponseWrapper responseWrapper) {
        return ResponseEntity.ok(responseWrapper);
    }

    /**
     * handleIOException - Handles all the Authentication Exceptions to the application.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseWrapper> handleIOException(HttpServletRequest request, RuntimeException e) {
        RestErrorList errorList = new RestErrorList(HttpStatus.NOT_ACCEPTABLE, new ErrorMessage(e.getMessage(), e.getMessage()));
        ResponseWrapper responseWrapper = new ResponseWrapper(null, singletonMap("status", HttpStatus.NOT_ACCEPTABLE), errorList);
        return ResponseEntity.ok(responseWrapper);
    }

}
