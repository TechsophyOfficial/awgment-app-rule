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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.*;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.GET;
import static org.mockito.ArgumentMatchers.*;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class UserDetailsIdNotFoundExceptionTest
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

    @Test
    void userDetailsIdNotFoundExceptionTest() throws JsonProcessingException
    {
        ObjectMapper objectMapper=new ObjectMapper();
        ApiResponse apiResponse=new ApiResponse(userList,true,USER_DETAILS_RETRIEVED_SUCCESS);
        Map<String,Object> response=objectMapper.convertValue(apiResponse,Map.class);
        Mockito.when(mockTokenUtils.getLoggedInUserId()).thenReturn(ABC);
        Mockito.when(mockTokenUtils.getTokenFromContext()).thenReturn(TEST_TOKEN);
        Mockito.when(mockWebClientWrapper.webclientRequest(any(),any(),eq(GET),any())).thenReturn
                (INITIALIZATION_DATA);
        Mockito.when(mockObjectMapper.readValue(anyString(),(TypeReference<Map<String,Object>>) any()))
                .thenReturn(response);
        Mockito.when(mockObjectMapper.convertValue(any(),eq(List.class))).thenReturn(userList);
        Assertions.assertThrows(UserDetailsIdNotFoundException.class, () ->
                mockUserDetails.getUserDetails());
    }
}
