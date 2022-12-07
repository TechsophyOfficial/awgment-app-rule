package com.techsophy.tsf.rule.exception;

import com.techsophy.tsf.rule.model.ApiErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    WebRequest webRequest;
    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    @Test
    void externalServiceErrorExceptionTest() {
        ExternalServiceErrorException exception = new ExternalServiceErrorException("error", "msg");
        HttpStatus actualResponse = globalExceptionHandler.externalServiceErrorException(exception, webRequest).getStatusCode();
        ApiErrorResponse errorDetails = new ApiErrorResponse(Instant.now(), exception.getMessage(), exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR, webRequest.getDescription(false));
        HttpStatus expectedResponse =  new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR).getStatusCode();
        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}