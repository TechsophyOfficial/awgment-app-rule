package com.techsophy.tsf.rule.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoggingHandlerTest {

    @Mock
    JoinPoint joinPoint;
    @Mock
    Signature signature;
    @Mock
    Logger logger;

    @InjectMocks
    LoggingHandler loggingHandler;

    @Test
    void beforeControllerTest() {
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("Signature_Name");
        loggingHandler.beforeController(joinPoint);
        verify(logger, times(1)).info(anyString(), anyString(), anyString());
    }

    @Test
    void afterControllerTest() {
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("Signature_Name");
        loggingHandler.afterController(joinPoint);
        verify(logger, times(1)).info(anyString(), anyString(), anyString());
    }

    @Test
    void beforeServiceTest() {
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("Signature_Name");
        loggingHandler.beforeService(joinPoint);
        verify(logger, times(1)).info(anyString(), anyString(), anyString());
    }

    @Test
    void afterServiceTest() {
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("Signature_Name");
        loggingHandler.afterService(joinPoint);
        verify(logger, times(1)).info(anyString(), anyString(), anyString());
    }

    @Test
    void logAfterThrowingControllerTest() {
        Exception exception = Mockito.mock(Exception.class);
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("Signature_Name");
        loggingHandler.logAfterThrowingController(joinPoint, exception);
        verify(logger, times(1)).error(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void logAfterThrowingServiceTest() {
        Exception exception = Mockito.mock(Exception.class);
        Mockito.when(joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn("Signature_Name");
        loggingHandler.logAfterThrowingService(joinPoint, exception);
        verify(logger, times(1)).error(anyString(), anyString(), anyString(), anyString());
    }
}