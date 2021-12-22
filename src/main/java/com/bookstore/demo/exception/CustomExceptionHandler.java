package com.bookstore.demo.exception;

import com.bookstore.demo.model.dto.ResponseDto;
import com.bookstore.demo.util.ResponseStatusEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LogManager.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = {CustomException.class})
    @ResponseBody
    public ResponseDto handleException(CustomException ex) {
        logger.error("CustomException: " + ex.getMessage());
        return new ResponseDto(ResponseStatusEnum.ERROR.getStatus(), ex.getErrorCode(), ex.getErrorMsg());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseDto handleException(MethodArgumentNotValidException ex) {
        logger.error("MethodArgumentNotValidException: " + ex.getFieldError().getDefaultMessage());
        return new ResponseDto(ResponseStatusEnum.ERROR.getStatus(), CustomExceptionConstants.METHOD_ARGUMENT_ERROR_CODE, ex.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseBody
    public ResponseDto handleException(AccessDeniedException ex) {
        logger.error("AccessDeniedException: " + ex.getMessage());
        return new ResponseDto(ResponseStatusEnum.ERROR.getStatus(), CustomExceptionConstants.ACCESS_DENIED_ERROR_CODE, CustomExceptionConstants.ACCESS_DENIED_ERROR_MSG);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseDto handleException(Exception ex) {
        logger.error("Exception: " + ex.getMessage());
        return new ResponseDto(CustomExceptionConstants.GENERAL_EXCEPTION_ERROR_CODE, CustomExceptionConstants.GENERAL_EXCEPTION_ERROR_MSG);
    }
}
