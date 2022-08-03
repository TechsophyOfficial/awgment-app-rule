package com.techsophy.tsf.rule.controller;

import com.techsophy.tsf.rule.config.CustomFilter;
import com.techsophy.tsf.rule.dto.PaginationResponsePayload;
import com.techsophy.tsf.rule.dto.RuleResponse;
import com.techsophy.tsf.rule.dto.RuleSchema;
import com.techsophy.tsf.rule.service.RuleService;
import com.techsophy.tsf.rule.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.Arrays;
import java.util.stream.Stream;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.*;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.ID_OR_NAME_LIKE;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.RULES_URL;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.SEARCH_RULES_URL;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({MockitoExtension.class})
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RuleControllerTest
{
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtSaveOrUpdate = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_RULE_CREATE_OR_UPDATE));
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRead = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_RULE_READ));
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtDelete = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_RULE_DELETE));

    @MockBean
    TokenUtils mockTokenUtils;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    CustomFilter customFilter;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    RuleService mockRuleService;

    @BeforeEach public void setUp()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(customFilter)
                .apply(springSecurity())
                .build();
    }

    @Test
    void saveRuleTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockRuleService.saveRule(RULE_ID, RULE_NAME, RULE_VERSION, Arrays.toString(RULE_CONTENT))).thenReturn(new RuleResponse(RULE_ID, RULE_VERSION));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL_RULE + VERSION_V1_1 + RULES_URL)
                .param(PARAM_ID, PARAM_ID_VALUE).param(PARAM_NAME, PARAM_NAME_VALUE).param(PARAM_VERSION, PARAM_VERSION_VALUE).param(PARAM_CONTENT, PARAM_CONTENT_VALUE)
                .with(jwtSaveOrUpdate)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getRuleByIdTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockRuleService.getRuleById(RULE_ID)).thenReturn(new RuleSchema(RULE_ID, RULE_NAME, RULE_CONTENT, RULE_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW, CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL_RULE + VERSION_V1_1 + RULES_BY_ID_URL, 1)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getAllRulesSortingTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockRuleService.getAllRules(true, null,null,null)).thenReturn(
                Stream.of(new RuleSchema(RULE_ID, RULE_NAME, RULE_CONTENT, RULE_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME),
                        new RuleSchema(RULE_ID, RULE_NAME, RULE_CONTENT, RULE_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME)));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL_RULE + VERSION_V1_1 + RULES_URL)
                .param(PARAM_INCLUDE_CONTENT, String.valueOf(true)).param(PARAM_DEPLOYMENT, PARAM_DEPLOYMENT_VALUE)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getAllRulesPaginationTest() throws Exception
    {
        PaginationResponsePayload paginationResponsePayloadTest=new PaginationResponsePayload();
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockRuleService.getAllRules(Q,true, PageRequest.of(PAGE_VALUE,PAGE_SIZE, Sort.by(RULE_NAME)))).thenReturn(paginationResponsePayloadTest);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL_RULE + VERSION_V1_1 + RULES_URL)
                .param(PARAM_INCLUDE_CONTENT, String.valueOf(true)).param(PARAM_DEPLOYMENT, PARAM_DEPLOYMENT_VALUE)
                .param(PAGE,PAGE_NUMBER).param(PAGE_SIZE_KEY,PAGE_SIZE_VALUE)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void deleteRuleByIdTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.delete(BASE_URL_RULE + VERSION_V1_1 + RULES_BY_ID_URL, 1)
                .with(jwtDelete);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void searchRuleByIdOrNameLike() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        Mockito.when(mockRuleService.searchRuleByIdOrNameLike(ID_OR_NAME_LIKE)).thenReturn(
                Stream.of( new RuleSchema(RULE_ID, RULE_NAME, RULE_CONTENT, RULE_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME),
                        new RuleSchema(RULE_ID, RULE_NAME, RULE_CONTENT, RULE_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW, CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME)));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL_RULE + VERSION_V1_1 + SEARCH_RULES_URL)
                .param(PARAM_ID_OR_NAME_LIKE, PARAM_ID_OR_NAME_LIKE_VALUE)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }
}

