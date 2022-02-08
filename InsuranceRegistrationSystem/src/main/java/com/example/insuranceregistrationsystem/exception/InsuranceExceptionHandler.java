package com.example.insuranceregistrationsystem.exception;

import com.example.insuranceregistrationsystem.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class InsuranceExceptionHandler  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException (MethodArgumentNotValidException exception) {
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        List<String> errMsg = errorList.stream()
                                        .map(objErr -> objErr.getDefaultMessage())
                                        .collect(Collectors.toList());
        ResponseDTO responseDTO = new ResponseDTO( errMsg,"Exception while processing REST Request due to argument Invalid");
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsuranceException.class)
    public ResponseEntity<ResponseDTO> handleCustomerException(InsuranceException exception) {
        ResponseDTO responseDTO = new ResponseDTO("", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
