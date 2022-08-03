package com.techsophy.tsf.rule.model;

import lombok.Value;

@Value
public class ApiResponse <T>
{
    T data;
    Boolean success;
    String message;
}
