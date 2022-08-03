package com.techsophy.tsf.rule.controller;

import com.techsophy.tsf.rule.config.CustomFilter;
import com.techsophy.tsf.rule.exception.EntityIdNotFoundException;
import com.techsophy.tsf.rule.exception.GlobalExceptionHandler;
import com.techsophy.tsf.rule.exception.InvalidInputException;
import com.techsophy.tsf.rule.exception.UserDetailsIdNotFoundException;
import com.techsophy.tsf.rule.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static com.techsophy.tsf.rule.constants.ErrorConstants.TOKEN_NOT_NULL;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.RULES_URL;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.*;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
class RuleControllerExceptionTest
{
    @MockBean
    TokenUtils mockTokenUtils;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    CustomFilter customFilter;
    @Mock
    private RuleController mockRuleController;

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(new GlobalExceptionHandler(), mockRuleController).addFilters(customFilter).build();
    }

    @Test
    void entityIdNotFoundExceptionTest() throws Exception
    {
       Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockRuleController.getRuleById(RULE_ID)).thenThrow(new EntityIdNotFoundException(RULE_NOT_FOUND_WITH_GIVEN_ID,RULE_NOT_FOUND_WITH_GIVEN_ID));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL + VERSION_V1 + RULES_BY_ID_URL,1)
                        .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void  constraintViolationExceptionTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL + VERSION_V1 + RULES_BY_ID_URL,1)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().is4xxClientError()).andReturn();
        assertEquals(405,mvcResult.getResponse().getStatus());
    }

    @Test
    void userDetailsNotFoundExceptionTest() throws Exception
    {
        Mockito.when(mockRuleController.getRuleById(RULE_ID)).thenThrow(new UserDetailsIdNotFoundException(USER_DETAILS_NOT_FOUND_WITH_GIVEN_ID,USER_DETAILS_NOT_FOUND_WITH_GIVEN_ID));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL + VERSION_V1 + RULES_BY_ID_URL,1)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }

    @Test
    void invalidInputExceptionTest() throws Exception
    {
        Mockito.when(mockRuleController.saveRule(any(),any(),any(),any())).thenThrow(new InvalidInputException(TOKEN_NOT_NULL,TOKEN_NOT_NULL));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL+VERSION_V1+RULES_URL)
                .param(PARAM_ID, PARAM_ID_VALUE).param(PARAM_NAME, PARAM_NAME_VALUE).param(PARAM_VERSION, PARAM_VERSION_VALUE).param(PARAM_CONTENT, PARAM_CONTENT_VALUE)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isInternalServerError()).andReturn();
        assertEquals(500,mvcResult.getResponse().getStatus());
    }
}
