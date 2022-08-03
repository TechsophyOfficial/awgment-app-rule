package com.techsophy.tsf.rule.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.rule.constants.RuleTestConstants;
import com.techsophy.tsf.rule.entity.RuleDefinition;
import com.techsophy.tsf.rule.repository.impl.RuleDefinitionCustomRepositoryImpl;
import lombok.Cleanup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.*;
import static org.mockito.ArgumentMatchers.eq;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class RuleDefinitionCustomRepositoryTest
{
    @Mock
    MongoTemplate mongoTemplate;
    @Mock
    RuleDefinition mockRuleDefinition;
    @InjectMocks
    RuleDefinitionCustomRepositoryImpl mockRuleDefinitionCustomRepositoryImpl;

    @Test
    void findByNameOrIdTest()
    {
        Mockito.when(mockRuleDefinitionCustomRepositoryImpl.findByNameOrId(ABC)).thenReturn(List.of(mockRuleDefinition));
        List<RuleDefinition> ruleDefinitionTest= mockRuleDefinitionCustomRepositoryImpl.findByNameOrId(ABC);
        Assertions.assertNotNull(ruleDefinitionTest);
    }

    @Test
    void findByIdInTest()
    {
        Mockito.when(mockRuleDefinitionCustomRepositoryImpl.findByIdIn(List.of(RuleTestConstants.ID_NUMBER,ID_2))).thenReturn(List.of(mockRuleDefinition));
        List<RuleDefinition> ruleDefinitionTest= mockRuleDefinitionCustomRepositoryImpl.findByIdIn(List.of(RuleTestConstants.ID_NUMBER,ID_2));
        Assertions.assertNotNull(ruleDefinitionTest);
    }

    @Test
    void findRulesByQPageable() throws IOException
    {
        org.springframework.data.domain.Pageable pageable= PageRequest.of(1,1);
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String ruleDataTest = new String(inputStreamTest.readAllBytes());
        RuleDefinition ruleDefinitionTest=objectMapperTest.readValue(ruleDataTest,RuleDefinition.class);
        Mockito.when(mongoTemplate.find(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(List.of(ruleDefinitionTest));
        Mockito.when(mongoTemplate.count(ArgumentMatchers.any(),eq(RuleDefinition.class))).thenReturn(Long.valueOf(TEST_ID));
        Page<RuleDefinition> ruleDefinitionListTest= mockRuleDefinitionCustomRepositoryImpl.findRulesByQPageable(ABC, pageable);
        Assertions.assertNotNull(ruleDefinitionListTest);
    }

    @Test
    void findRulesByQSortingTest() throws IOException
    {
       ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String ruleDataTest = new String(inputStreamTest.readAllBytes());
        RuleDefinition ruleDefinitionTest=objectMapperTest.readValue(ruleDataTest,RuleDefinition.class);
        Mockito.when(mongoTemplate.find(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(List.of(ruleDefinitionTest));
        Stream<RuleDefinition> ruleDefinitionListTest= mockRuleDefinitionCustomRepositoryImpl.findRulesByQSorting(ABC, Sort.unsorted());
        Assertions.assertNotNull(ruleDefinitionListTest);
    }
}
