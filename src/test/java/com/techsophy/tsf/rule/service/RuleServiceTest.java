package com.techsophy.tsf.rule.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.rule.config.GlobalMessageSource;
import com.techsophy.tsf.rule.constants.RuleTestConstants;
import com.techsophy.tsf.rule.dto.PaginationResponsePayload;
import com.techsophy.tsf.rule.dto.RuleAuditSchema;
import com.techsophy.tsf.rule.dto.RuleResponse;
import com.techsophy.tsf.rule.dto.RuleSchema;
import com.techsophy.tsf.rule.entity.RuleDefinition;
import com.techsophy.tsf.rule.repository.RuleDefinitionRepository;
import com.techsophy.tsf.rule.service.impl.RuleServiceImpl;
import com.techsophy.tsf.rule.utils.TokenUtils;
import com.techsophy.tsf.rule.utils.UserDetails;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import java.util.*;
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
class RuleServiceTest
{
    @Mock
    UserDetails mockUserDetails;
    @Mock
    RuleDefinitionRepository mockRuleDefinitionRepository;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    IdGeneratorImpl mockIdGenerator;
    @Mock
    TokenUtils tokenUtils;
    @Mock
    RuleAuditService ruleAuditService;
    @Mock
    GlobalMessageSource globalMessageSource;
    @InjectMocks
    RuleServiceImpl mockRuleServiceImpl;

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
        map.put(ID, LOGGED_USER_ID);
        map.put(USER_NAME, USER_FIRST_NAME);
        map.put(FIRST_NAME, USER_LAST_NAME);
        map.put(LAST_NAME, USER_FIRST_NAME);
        map.put(MOBILE_NUMBER, NUMBER);
        map.put(EMAIL_ID, MAIL_ID);
        map.put(DEPARTMENT, NULL);
        userList.add(map);
    }

    @Test
    void saveRuleTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA_SCHEMA).getInputStream();
        String ruleDataTest = new String(inputStreamTest.readAllBytes());
        RuleDefinition ruleDefinitionTest = objectMapperTest.readValue(ruleDataTest, RuleDefinition.class);
        RuleAuditSchema ruleAuditSchema=new RuleAuditSchema(RULE_ID,RULE_ID, RULE_NAME,RULE_VERSION, RULE_CONTENT, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        when(mockObjectMapper.convertValue(any(), eq(RuleAuditSchema.class))).thenReturn(ruleAuditSchema);
        when(mockUserDetails.getUserDetails()).thenReturn(userList);
        when(mockIdGenerator.nextId()).thenReturn(BigInteger.valueOf(Long.parseLong(RULE_ID)));
        when(mockRuleDefinitionRepository.save(any())).thenReturn(ruleDefinitionTest.withId(BigInteger.valueOf(Long.parseLong(RULE_ID))));
        when(this.mockObjectMapper.convertValue(any(), eq(RuleDefinition.class))).thenReturn(ruleDefinitionTest);
        when(mockRuleDefinitionRepository.save(ruleDefinitionTest)).thenReturn(ruleDefinitionTest.withId(BigInteger.valueOf(Long.parseLong(RULE_ID))));
        when(this.mockObjectMapper.convertValue(any(), eq(RuleResponse.class))).thenReturn(new RuleResponse(RULE_ID, RULE_VERSION));
        mockRuleServiceImpl.saveRule(null, RULE_NAME, RULE_VERSION, Arrays.toString(RULE_CONTENT));
        verify(mockRuleDefinitionRepository, times(1)).save(any());
    }

    @Test
    void updateRuleTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String ruleDataTest = new String(inputStreamTest.readAllBytes());
        RuleDefinition ruleDefinitionTest = objectMapperTest.readValue(ruleDataTest,RuleDefinition.class);
        RuleAuditSchema ruleAuditSchema=new RuleAuditSchema(RULE_ID,RULE_ID, RULE_NAME,RULE_VERSION, RULE_CONTENT, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        when(mockRuleDefinitionRepository.save(any())).thenReturn(ruleDefinitionTest.withId(BigInteger.valueOf(Long.parseLong(RULE_ID))));
        when(mockObjectMapper.convertValue(any(), eq(RuleAuditSchema.class))).thenReturn(ruleAuditSchema);
        Mockito.when(mockUserDetails.getUserDetails()).thenReturn(userList);
        when(mockIdGenerator.nextId()).thenReturn(BigInteger.valueOf(Long.parseLong(RULE_ID)));
        when(this.mockObjectMapper.convertValue(any(), eq(RuleDefinition.class)))
                .thenReturn(ruleDefinitionTest);
        when(this.mockObjectMapper.convertValue(any(), eq(RuleResponse.class))).thenReturn(new RuleResponse(RULE_ID, RULE_VERSION));
        when(mockRuleDefinitionRepository.existsById(BigInteger.valueOf(Long.parseLong(RULE_ID)))).thenReturn(true);
        when(mockRuleDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong(RULE_ID)))).thenReturn(Optional.of(ruleDefinitionTest));
        mockRuleServiceImpl.saveRule(RULE_ID, RULE_NAME, RULE_VERSION, Arrays.toString(RULE_CONTENT));
        verify(mockRuleDefinitionRepository, times(1)).save(any());
    }

    @Test
    void updateRuleNewRecordTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String ruleDataTest = new String(inputStreamTest.readAllBytes());
        RuleDefinition ruleDefinitionTest = objectMapperTest.readValue(ruleDataTest,RuleDefinition.class);
        RuleAuditSchema ruleAuditSchema=new RuleAuditSchema(RULE_ID,RULE_ID, RULE_NAME,RULE_VERSION, RULE_CONTENT, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATED_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        when(mockRuleDefinitionRepository.save(any())).thenReturn(ruleDefinitionTest.withId(BigInteger.valueOf(Long.parseLong(RULE_ID))));
        when(mockObjectMapper.convertValue(any(), eq(RuleAuditSchema.class))).thenReturn(ruleAuditSchema);
        Mockito.when(mockUserDetails.getUserDetails()).thenReturn(userList);
        when(mockIdGenerator.nextId()).thenReturn(BigInteger.valueOf(Long.parseLong(RULE_ID)));
        when(this.mockObjectMapper.convertValue(any(), eq(RuleDefinition.class)))
                .thenReturn(ruleDefinitionTest);
        when(this.mockObjectMapper.convertValue(any(), eq(RuleResponse.class))).thenReturn(new RuleResponse(RULE_ID, RULE_VERSION));
        when(mockRuleDefinitionRepository.existsById(BigInteger.valueOf(Long.parseLong(RULE_ID)))).thenReturn(false);
        when(mockRuleDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong(RULE_ID)))).thenReturn(Optional.of(ruleDefinitionTest));
        mockRuleServiceImpl.saveRule(RULE_ID, RULE_NAME, RULE_VERSION, Arrays.toString(RULE_CONTENT));
        verify(mockRuleDefinitionRepository, times(1)).save(any());
    }

    @Test
    void getRuleByIdTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String ruleDataTest = new String(inputStreamTest.readAllBytes());
        RuleSchema ruleSchemaTest=new RuleSchema(RULE_ID, RULE_NAME, RULE_CONTENT, RULE_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        RuleDefinition ruleDefinitionTest=objectMapperTest.readValue(ruleDataTest,RuleDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(RuleSchema.class))).thenReturn(ruleSchemaTest);
        when(mockRuleDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong(String.valueOf(1))))).thenReturn(java.util.Optional.ofNullable(ruleDefinitionTest));
        mockRuleServiceImpl.getRuleById(RULE_ID);
        verify(mockRuleDefinitionRepository,times(1)).findById(BigInteger.valueOf(1));
    }

    @Test
    void getAllRulesIncludeContentTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String rulesDataTest=new String(inputStreamTest.readAllBytes());
        RuleSchema ruleSchemaTest=new RuleSchema(RULE_ID, RULE_NAME, RULE_CONTENT, RULE_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        RuleDefinition ruleDefinitionTest=objectMapperTest.readValue(rulesDataTest,RuleDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(RuleSchema.class))).thenReturn(ruleSchemaTest);
        when(mockRuleDefinitionRepository.findAll()).thenReturn(List.of(ruleDefinitionTest));
        mockRuleServiceImpl.getAllRules(true,null,null,null);
        verify(mockRuleDefinitionRepository,times(1)).findAll((Sort) any());
    }

    @Test
    void getAllRulesIncludeDeploymentIdListTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String rulesDataTest=new String(inputStreamTest.readAllBytes());
        RuleSchema ruleSchemaTest=new RuleSchema(RULE_ID, RULE_NAME, RULE_CONTENT, RULE_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        RuleDefinition ruleDefinitionTest=objectMapperTest.readValue(rulesDataTest,RuleDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(RuleSchema.class))).thenReturn(ruleSchemaTest);
        when(mockRuleDefinitionRepository.findByIdIn(List.of(RuleTestConstants.ID_NUMBER))).thenReturn(List.of(ruleDefinitionTest));
        mockRuleServiceImpl.getAllRules(true, RuleTestConstants.ID_NUMBER,null,null);
        verify(mockRuleDefinitionRepository,times(1)).findByIdIn(List.of(RuleTestConstants.ID_NUMBER));
    }

    @Test
    void getAllRulesWithoutDeploymentIdListTest() throws IOException
    {
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String rulesDataTest=new String(inputStreamTest.readAllBytes());
        RuleSchema ruleSchemaTest=new RuleSchema(RULE_ID, RULE_NAME, RULE_CONTENT, RULE_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        RuleDefinition ruleDefinitionTest=objectMapperTest.readValue(rulesDataTest,RuleDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(RuleSchema.class))).thenReturn(ruleSchemaTest);
        when(mockRuleDefinitionRepository.findRulesByQSorting(Q,null)).thenReturn(Stream.of(ruleDefinitionTest));
        mockRuleServiceImpl.getAllRules(true, null,Q,null);
        verify(mockRuleDefinitionRepository,times(1)).findRulesByQSorting(Q,null);
    }

    @Test
    void deleteRuleById()
    {
        when(mockRuleDefinitionRepository.existsById(BigInteger.valueOf(Long.parseLong(TEST_ID)))).thenReturn(true);
        when(mockRuleDefinitionRepository.deleteById(BigInteger.valueOf(Long.parseLong(TEST_ID)))).thenReturn(Integer.valueOf(RULE_ID));
        mockRuleServiceImpl.deleteRuleById(RULE_ID);
        verify(mockRuleDefinitionRepository,times(1)).deleteById(BigInteger.valueOf(1));
    }

    @Test
    void searchRuleByIdOrNameLike() throws IOException
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String ruleDataTest =new String(inputStreamTest.readAllBytes());
        RuleSchema ruleSchemaTest=new RuleSchema(RULE_ID, RULE_NAME, RULE_CONTENT, RULE_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW,CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        RuleDefinition ruleDefinitionTest=objectMapperTest.readValue(ruleDataTest,RuleDefinition.class);
        when(this.mockObjectMapper.convertValue(any(),eq(RuleSchema.class))).thenReturn(ruleSchemaTest);
        when(mockRuleDefinitionRepository.findByNameOrId(ID_OR_NAME_LIKE)).thenReturn(Collections.singletonList(ruleDefinitionTest));
        mockRuleServiceImpl.searchRuleByIdOrNameLike(ID_OR_NAME_LIKE);
        verify(mockRuleDefinitionRepository,times(1)).findByNameOrId(ID_OR_NAME_LIKE);
    }

    @Test
    void getAllRulesAndPageableTest() throws IOException
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA1).getInputStream();
        String ruleDataTest =new String(inputStreamTest.readAllBytes());
        RuleDefinition ruleDefinitionTest=objectMapperTest.readValue(ruleDataTest,RuleDefinition.class);
        Page<RuleDefinition> page =new PageImpl<>(List.of(ruleDefinitionTest)) ;
        Pageable pageable= PageRequest.of(PAGE_VALUE,PAGE_SIZE);
        Mockito. when(mockRuleDefinitionRepository.findRulesByQPageable(Q,pageable)).thenReturn(page);
        PaginationResponsePayload paginationResponsePayload = new PaginationResponsePayload();
        when(tokenUtils.getPaginationResponsePayload(any(),any())).thenReturn(paginationResponsePayload);
        mockRuleServiceImpl.getAllRules(Q,true,pageable);
        verify(mockRuleDefinitionRepository,times(1)).findRulesByQPageable(Q,pageable);
    }

    @Test
    void getAllRulesAndOnlyPageableTest() throws IOException
    {
        ObjectMapper objectMapperTest=new ObjectMapper();
        InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA1).getInputStream();
        String ruleDataTest =new String(inputStreamTest.readAllBytes());
        RuleDefinition ruleDefinitionTest=objectMapperTest.readValue(ruleDataTest,RuleDefinition.class);
        Page<RuleDefinition> page =new PageImpl<>(List.of(ruleDefinitionTest)) ;
        Pageable pageable= PageRequest.of(PAGE_VALUE,PAGE_SIZE);
        Mockito. when(mockRuleDefinitionRepository.findAll(pageable)).thenReturn(page);
        PaginationResponsePayload paginationResponsePayload = new PaginationResponsePayload();
        when(tokenUtils.getPaginationResponsePayload(any(),any())).thenReturn(paginationResponsePayload);
        mockRuleServiceImpl.getAllRules(null,true,pageable);
        verify(mockRuleDefinitionRepository,times(1)).findAll(pageable);
    }
}

