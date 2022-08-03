package com.techsophy.tsf.rule.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorConstants
{
    public static final String LOGGED_IN_USER_ID_NOT_FOUND ="AWGMENT-RULE-1001";
    public static final String  RULE_NOT_FOUND="AWGMENT-RULE-1002";
    public static final String INVALID_TOKEN ="AWGMENT-RULE-1003";
    public static final String TOKEN_NOT_NULL="AWGMENT-RULE-1004";
    public static final String USER_DETAILS_NOT_FOUND_EXCEPTION="AWGMENT-RULE-1005";
    public static final String UNABLE_TO_GET_TOKEN="AWGMENT-RULE-1006";
    public static final String AUTHENTICATION_FAILED="AWGMENT-RULE-1007";
    public static final String INVALID_PAGE_REQUEST ="AWGMENT-RULE-1008";
    public static final String SERVICE_NOT_AVAILABLE="AWGMENT-RULE-1009";
}
