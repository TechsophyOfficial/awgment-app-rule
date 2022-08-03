package com.techsophy.tsf.rule.controller;

import com.techsophy.tsf.rule.config.CustomFilter;
import com.techsophy.tsf.rule.dto.PaginationResponsePayload;
import com.techsophy.tsf.rule.dto.RuleAuditSchema;
import com.techsophy.tsf.rule.dto.RuleResponse;
import com.techsophy.tsf.rule.service.RuleAuditService;
import com.techsophy.tsf.rule.utils.TokenUtils;
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
import java.util.stream.Stream;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.RULES_URL;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.*;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RuleAuditControllerTest
{
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtSaveOrUpdate = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_RULE_CREATE_OR_UPDATE));
    private static  final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRead = jwt().authorities(new SimpleGrantedAuthority(AWGMENT_RULE_READ));

    @MockBean
    TokenUtils mockTokenUtils;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    RuleAuditService ruleAuditService;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    CustomFilter customFilter;

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .addFilters(customFilter)
                .build();
    }

    @Test
    void saveRuleTest() throws Exception
    {
        Mockito.when(mockTokenUtils.getIssuerFromToken(TOKEN)).thenReturn(TENANT);
        RuleAuditSchema ruleAuditSchema=new RuleAuditSchema(TEST_ID,RULE_ID, RULE_NAME, RULE_VERSION,RULE_CONTENT ,CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        RuleResponse ruleResponse=new RuleResponse(TEST_ID,RULE_VERSION);
        Mockito.when(ruleAuditService.saveRule(ruleAuditSchema)).thenReturn(ruleResponse);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.post(BASE_URL+VERSION_V1+ HISTORY+RULES_URL)
                .with(jwtSaveOrUpdate)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getRulesByIdTest() throws Exception
    {
        RuleAuditSchema ruleAuditSchema=new RuleAuditSchema(TEST_ID,RULE_ID, RULE_NAME, RULE_VERSION,RULE_CONTENT ,CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        Mockito.when(ruleAuditService.getRulesById(TEST_ID,RULE_VERSION)).thenReturn(ruleAuditSchema);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL+VERSION_V1+ HISTORY+RULE_VERSION_BY_ID_URL,1,1)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getAllRulesPaginationTest() throws Exception
    {
        PaginationResponsePayload paginationResponsePayloadTest=new PaginationResponsePayload();
        Mockito.when(ruleAuditService.getAllRules(TEST_ID,true, PageRequest.of(PAGE_VALUE,PAGE_SIZE,Sort.by(RULE_NAME)))).thenReturn(paginationResponsePayloadTest);
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL+VERSION_V1+ HISTORY+RULE_BY_ID_URL,1)
                .param(INCLUDE_CONTENT,TRUE)
                .param(PAGE,PAGE_NUMBER)
                .param(PAGE_SIZE_KEY,PAGE_SIZE_VALUE)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void getAllRulesSortingTest() throws Exception
    {
        RuleAuditSchema ruleAuditSchema=new RuleAuditSchema(TEST_ID,RULE_ID, RULE_NAME, RULE_VERSION,RULE_CONTENT ,CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        Mockito.when(ruleAuditService.getAllRules(TEST_ID,true, Sort.by(RULE_NAME))).thenReturn(Stream.of(ruleAuditSchema));
        RequestBuilder requestBuilderTest = MockMvcRequestBuilders.get(BASE_URL+VERSION_V1+ HISTORY+RULE_BY_ID_URL,1)
                .param(INCLUDE_CONTENT,TRUE)
                .param(SORT_BY,RULE_NAME)
                .with(jwtRead)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = this.mockMvc.perform(requestBuilderTest).andExpect(status().isOk()).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());
    }
}
