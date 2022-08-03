package com.techsophy.tsf.rule.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.rule.config.GlobalMessageSource;
import com.techsophy.tsf.rule.config.LocaleConfig;
import com.techsophy.tsf.rule.dto.RuleSchema;
import com.techsophy.tsf.rule.exception.EntityIdNotFoundException;
import com.techsophy.tsf.rule.exception.UserDetailsIdNotFoundException;
import com.techsophy.tsf.rule.repository.RuleDefinitionRepository;
import com.techsophy.tsf.rule.service.impl.RuleServiceImpl;
import com.techsophy.tsf.rule.utils.UserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigInteger;
import java.util.*;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class RuleServiceExceptionTest
{
    @Mock
    UserDetails mockUserDetails;
    @Mock
    RuleDefinitionRepository mockRuleDefinitionRepository;
    @Mock
    ObjectMapper mockObjectMapper;
    @Mock
    IdGeneratorImpl mockIdGeneratorImpl;
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @Mock
    LocaleConfig mockLocaleConfig;
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
        map.put(ID,EMPTY_STRING);
        map.put(USER_NAME, USER_FIRST_NAME);
        map.put(FIRST_NAME, USER_LAST_NAME);
        map.put(LAST_NAME, USER_FIRST_NAME);
        map.put(MOBILE_NUMBER, NUMBER);
        map.put(EMAIL_ID, MAIL_ID);
        map.put(DEPARTMENT, NULL);
        userList.add(map);
    }

    @Test
    void saveRuleExceptionTest() throws JsonProcessingException
    {
          when(mockUserDetails.getUserDetails()).thenReturn(userList);
          String str=Arrays.toString(RULE_CONTENT);
          Assertions.assertThrows(UserDetailsIdNotFoundException.class,() ->
                mockRuleServiceImpl.saveRule(null, RULE_NAME, RULE_VERSION,str));
    }

    @Test
    void getRuleByIdExceptionTest()
    {
         RuleSchema ruleSchemaTest =new RuleSchema(RULE_ID, RULE_NAME, RULE_CONTENT, RULE_VERSION, CREATED_BY_ID_VALUE, CREATED_ON_NOW, CREATEDE_BY_NAME, UPDATED_BY_ID_VALUE, UPDATED_ON_NOW,UPDATED_BY_NAME);
        when(this.mockObjectMapper.convertValue(any(),eq(RuleSchema.class))).thenReturn(ruleSchemaTest);
        when(mockRuleDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong(String.valueOf(1))))).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityIdNotFoundException.class,()-> mockRuleServiceImpl.getRuleById(RULE_ID));
    }

    @Test
    void deleteRuleByIdExceptionTest()
    {
       when(mockRuleDefinitionRepository.existsById(BigInteger.valueOf(1))).thenReturn(false);
        Assertions.assertThrows(EntityIdNotFoundException.class,()->
                mockRuleServiceImpl.deleteRuleById(RULE_ID));
    }
}

