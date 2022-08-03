package com.techsophy.tsf.rule.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.rule.config.GlobalMessageSource;
import com.techsophy.tsf.rule.exception.UserDetailsIdNotFoundException;
import com.techsophy.tsf.rule.model.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class UserDetailsExceptionTest
{
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    TokenUtils mockTokenUtils;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    WebClientWrapper mockWebClientWrapper;
    @InjectMocks
    UserDetails mockUserDetails;

    @Test
    void getUserDetailsExceptionTest() throws JsonProcessingException
    {
        ApiResponse apiResponse=new ApiResponse(null,true,USER_DETAILS_RETRIEVED_SUCCESS);
        Mockito.when(mockTokenUtils.getLoggedInUserId()).thenReturn(EMPTY_STRING);
        Mockito.when(mockTokenUtils.getTokenFromContext()).thenReturn(TEST_TOKEN);
        Mockito.when(mockObjectMapper.readValue(anyString(),(TypeReference<ApiResponse>) any())).thenReturn(apiResponse);
        Assertions.assertThrows(UserDetailsIdNotFoundException.class, () -> mockUserDetails.getUserDetails());
    }

    @Test
    void getUserDetailsNotFoundExceptionTest() throws JsonProcessingException
    {
        ApiResponse apiResponse=new ApiResponse(null,true,USER_DETAILS_RETRIEVED_SUCCESS);
        Mockito.when(mockTokenUtils.getLoggedInUserId()).thenReturn(LOGGED_USER_ID);
        Mockito.when(mockTokenUtils.getTokenFromContext()).thenReturn(TEST_TOKEN);
        Mockito.when(mockObjectMapper.readValue(anyString(),(TypeReference<ApiResponse>) any()))
                .thenReturn(apiResponse);
        Assertions.assertThrows(UserDetailsIdNotFoundException.class, () ->
                mockUserDetails.getUserDetails());
    }
}
