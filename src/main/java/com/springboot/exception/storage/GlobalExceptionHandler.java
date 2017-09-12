/*
package com.springboot.exception.storage;


import com.springboot.dto.updto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;

*/
/**
 * Created by liuyongg on 27/7/2017.
 *//*


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

 */
/*   @ResponseBody
    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleException(HttpServletRequest request, Exception exc){
        HttpStatus status = getStatus(request);
        String message = exc.getMessage();
        log.error("Exception:"+exc);
        return ExceptionResponse.create(status.value(), message);
    }*//*


    @ResponseBody
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ExceptionResponse handleStorageFileNotFound(HttpServletRequest request, StorageFileNotFoundException exc) {
        HttpStatus status = getStatus(request);
        String message = exc.getMessage();
        return ExceptionResponse.create(status.value(), message);
    }

    @ResponseBody
    @ExceptionHandler(StorageException.class)
    public ExceptionResponse handleStorageException(HttpServletRequest request, StorageException exc) {
        HttpStatus status = getStatus(request);
        String message = exc.getMessage();
        return ExceptionResponse.create(status.value(), message);
    }


    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    @ResponseBody
    @ExceptionHandler(FileFormatException.class)
    public ExceptionResponse handleFileFormatException(HttpServletRequest request, FileFormatException exc) {
        HttpStatus status = getStatus(request);
        String message = exc.getMessage();
        return ExceptionResponse.create(status.value(), message);
    }

    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public ExceptionResponse handleMultipartException(HttpServletRequest request, MultipartException exc) {
        HttpStatus status = getStatus(request);
        try {
            return ExceptionResponse.create(status.value(), exc.getCause().getCause().getMessage());
        } catch (NullPointerException e) {
            return ExceptionResponse.create(status.value(), exc.getMessage());
        }
    }

}


*/
