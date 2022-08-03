package com.techsophy.tsf.rule.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.rule.config.GlobalMessageSource;
import com.techsophy.tsf.rule.dto.PaginationResponsePayload;
import com.techsophy.tsf.rule.dto.RuleAuditSchema;
import com.techsophy.tsf.rule.entity.RuleAuditDefinition;
import com.techsophy.tsf.rule.repository.RuleDefinitionAuditRepository;
import com.techsophy.tsf.rule.service.impl.RuleAuditServiceImpl;
import com.techsophy.tsf.rule.utils.TokenUtils;
import com.techsophy.tsf.rule.utils.UserDetails;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@EnableWebMvc
@ActiveProfiles(TEST_ACTIVE_PROFILE)
@ExtendWith({SpringExtension.class})
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RuleAuditServiceImplTest
{
    @Mock
    UserDetails mockUserDetails;
    @Mock
    RuleDefinitionAuditRepository mockRuleDefinitionAuditRepository;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    IdGeneratorImpl mockIdGenerator;
    @Mock
    TokenUtils tokenUtils;
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    RuleAuditService ruleAuditService;
    @InjectMocks
    RuleAuditServiceImpl mockRuleAuditServiceImpl;

    @Test
    void saveRuleTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA_SCHEMA).getInputStream();
        String ruleDataTest = new String(inputStreamTest.readAllBytes());
        RuleAuditDefinition ruleDefinitionTest = objectMapperTest.readValue(ruleDataTest, RuleAuditDefinition.class);
        RuleAuditSchema ruleAuditSchema=new RuleAuditSchema(RULE_ID,RULE_ID, RULE_NAME,RULE_VERSION, RULE_CONTENT, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        when(this.mockObjectMapper.convertValue(any(), eq(RuleAuditDefinition.class))).thenReturn(ruleDefinitionTest);
        when(mockRuleDefinitionAuditRepository.save(any())).thenReturn(ruleDefinitionTest.withId(BigInteger.valueOf(Long.parseLong(RULE_ID))));
        mockRuleAuditServiceImpl.saveRule(ruleAuditSchema);
        verify(mockRuleDefinitionAuditRepository, times(1)).save(any());
    }

    @Test
    void getRulesByIdTest() throws Exception
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String ruleDataTest = new String(inputStreamTest.readAllBytes());
        RuleAuditSchema ruleAuditSchema=new RuleAuditSchema(RULE_ID,RULE_ID, RULE_NAME,RULE_VERSION, RULE_CONTENT, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        RuleAuditDefinition ruleDefinitionTest=objectMapperTest.readValue(ruleDataTest,RuleAuditDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(RuleAuditSchema.class))).thenReturn(ruleAuditSchema);
        when(mockRuleDefinitionAuditRepository.findById(BigInteger.valueOf(Long.parseLong(TEST_ID)),1)).thenReturn(Optional.ofNullable(ruleDefinitionTest));
        mockRuleAuditServiceImpl.getRulesById(TEST_ID,RULE_VERSION);
        verify(mockRuleDefinitionAuditRepository,times(1)).findById(BigInteger.valueOf(Long.parseLong(TEST_ID)),RULE_VERSION);
    }

    @Test
    void getAllRulesIncludeContentTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String rulesDataTest=new String(inputStreamTest.readAllBytes());
        RuleAuditDefinition ruleDefinitionTest=objectMapperTest.readValue(rulesDataTest,RuleAuditDefinition.class);
        Page<RuleAuditDefinition> page =new PageImpl<>(List.of(ruleDefinitionTest)) ;
        Pageable pageable= PageRequest.of(PAGE_VALUE,PAGE_SIZE);
        PaginationResponsePayload paginationResponsePayload = new PaginationResponsePayload();
        when(mockRuleDefinitionAuditRepository.findAllById(BigInteger.valueOf(Long.parseLong(TEST_ID)),pageable)).thenReturn(page);
        when(tokenUtils.getPaginationResponsePayload(any(),any())).thenReturn(paginationResponsePayload);
        mockRuleAuditServiceImpl.getAllRules(RULE_ID,true,pageable);
        verify(mockRuleDefinitionAuditRepository,times(1)).findAllById(BigInteger.valueOf(Long.parseLong(TEST_ID)),pageable);
    }

    @Test
    void getAllRulesIncludeContentTest1() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String rulesDataTest=new String(inputStreamTest.readAllBytes());
        RuleAuditDefinition ruleDefinitionTest=objectMapperTest.readValue(rulesDataTest,RuleAuditDefinition.class);
        when(mockRuleDefinitionAuditRepository.findAllById(BigInteger.valueOf(Long.parseLong(TEST_ID)),(Sort) null)).thenReturn(Stream.of( ruleDefinitionTest));
        mockRuleAuditServiceImpl.getAllRules(RULE_ID,true,(Sort) null);
        verify(mockRuleDefinitionAuditRepository,times(1)).findAllById(BigInteger.valueOf(Long.parseLong(TEST_ID)),(Sort) null);
    }
}
