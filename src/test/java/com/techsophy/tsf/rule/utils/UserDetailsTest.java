package com.techsophy.tsf.rule.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.rule.config.GlobalMessageSource;
import com.techsophy.tsf.rule.exception.InvalidInputException;
import com.techsophy.tsf.rule.model.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.*;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.GET;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class UserDetailsTest
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

    List<Map<String, Object>> userList = new ArrayList<>();

    @BeforeEach
    public void init()
    {
        Map<String, Object> map = new HashMap<>();
        map.put(CREATED_BY_ID, NULL);
        map.put(CREATEDE_BY_NAME, NULL);
        map.put(CREATED_ON, NULL);
        map.put(UPDATED_BY_ID, NULL);
        map.put(UPDATED_BY_NAME, NULL);
        map.put(UPDATED_ON, NULL);
        map.put(LOGGED_USER_ID, ID_NUMBER);
        map.put(USER_NAME, USER_FIRST_NAME);
        map.put(FIRST_NAME, USER_LAST_NAME);
        map.put(LAST_NAME, USER_FIRST_NAME);
        map.put(MOBILE_NUMBER, NUMBER);
        map.put(EMAIL_ID, MAIL_ID);
        map.put(DEPARTMENT, NULL);
        userList.add(map);
    }

    @Test
    void getUserDetailsTest() throws JsonProcessingException
    {
        ObjectMapper objectMapper=new ObjectMapper();
        ApiResponse apiResponse=new ApiResponse(userList,true,USER_DETAILS_RETRIEVED_SUCCESS);
        Map<String,Object> response=objectMapper.convertValue(apiResponse,Map.class);
        Mockito.when(mockTokenUtils.getLoggedInUserId()).thenReturn(ABC);
        Mockito.when(mockTokenUtils.getTokenFromContext()).thenReturn(TEST_TOKEN);
        Mockito.when(mockWebClientWrapper.webclientRequest(any(),any(),eq(GET),any())).thenReturn(INITIALIZATION_DATA);
        Mockito.when(mockObjectMapper.readValue(anyString(),(TypeReference<Map<String,Object>>) any()))
                .thenReturn(response);
        Mockito.when(mockObjectMapper.convertValue(any(),eq(List.class))).thenReturn(userList);
        Assertions.assertNotNull(mockUserDetails.getUserDetails());
    }

    @Test
    void InvalidInputExceptionTest() throws JsonProcessingException
    {
        ApiResponse apiResponse=new ApiResponse(userList,true,USER_DETAILS_RETRIEVED_SUCCESS);
            Mockito.when(mockTokenUtils.getLoggedInUserId()).thenReturn(LOGGED_USER_ID);
        Mockito.when(mockTokenUtils.getTokenFromContext()).thenReturn(EMPTY_STRING);
        Mockito.when(mockWebClientWrapper.webclientRequest(any(),any(),eq(GET),any())).thenReturn(INITIALIZATION_DATA);
        Mockito.when(mockObjectMapper.readValue(anyString(),(TypeReference<ApiResponse>) any())).thenReturn(apiResponse);
        Mockito.when(mockObjectMapper.convertValue(any(),eq(List.class))).thenReturn(userList);
        Assertions.assertThrows(InvalidInputException.class, () ->
                mockUserDetails.getUserDetails());
    }
}

