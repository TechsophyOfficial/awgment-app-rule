package com.techsophy.tsf.rule.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.rule.config.GlobalMessageSource;
import com.techsophy.tsf.rule.dto.PaginationResponsePayload;
import com.techsophy.tsf.rule.dto.RuleAuditSchema;
import com.techsophy.tsf.rule.dto.RuleResponse;
import com.techsophy.tsf.rule.entity.RuleAuditDefinition;
import com.techsophy.tsf.rule.exception.EntityIdNotFoundException;
import com.techsophy.tsf.rule.repository.RuleDefinitionAuditRepository;
import com.techsophy.tsf.rule.service.RuleAuditService;
import com.techsophy.tsf.rule.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.techsophy.tsf.rule.constants.ErrorConstants.RULE_NOT_FOUND;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class RuleAuditServiceImpl implements RuleAuditService
{
    private final RuleDefinitionAuditRepository ruleDefinitionAuditRepository;
    private final ObjectMapper objectMapper;
    private final GlobalMessageSource globalMessageSource;
    private final TokenUtils tokenUtils;

    @Override
    public RuleResponse saveRule(RuleAuditSchema ruleAuditSchema) throws JsonProcessingException
    {
        RuleAuditDefinition ruleAuditDefinition =this.objectMapper.convertValue(ruleAuditSchema,RuleAuditDefinition.class);
        ruleDefinitionAuditRepository.save(ruleAuditDefinition);
        return this.objectMapper.convertValue(ruleAuditDefinition, RuleResponse.class);
    }

    @Override
    public Stream<RuleAuditSchema> getAllRules(String id, boolean includeProcessContent, Sort sort)
    {
        return this.ruleDefinitionAuditRepository.findAllById(BigInteger.valueOf(Long.parseLong(id)),sort )
                .map(this::convertEntityToDTO)
                .map(processAuditSchema ->
                {
                    if (includeProcessContent)
                    {
                        return processAuditSchema;
                    }
                    return processAuditSchema.withContent(null);
                });
    }

    @Override
    public PaginationResponsePayload getAllRules(String id, boolean includeProcessContent, Pageable pageable)
    {
        Page<RuleAuditDefinition> ruleAuditSchemaPage= this.ruleDefinitionAuditRepository.findAllById(BigInteger.valueOf(Long.parseLong(id)),pageable );
        List<Map<String,Object>> ruleAuditSchemaList=   ruleAuditSchemaPage.stream().map(this::convertEntityToMap).collect(Collectors.toList());
        return tokenUtils.getPaginationResponsePayload(ruleAuditSchemaPage,ruleAuditSchemaList);
    }

    @Override
    public RuleAuditSchema getRulesById(String id, Integer version)
    {
        RuleAuditDefinition process=
                this.ruleDefinitionAuditRepository.findById(BigInteger.valueOf(Long.parseLong(id)),version)
                        .orElseThrow(() -> new EntityIdNotFoundException(RULE_NOT_FOUND,globalMessageSource.get(RULE_NOT_FOUND,id)));
        return this.objectMapper.convertValue(process, RuleAuditSchema.class);
    }

    private Map<String,Object> convertEntityToMap(RuleAuditDefinition ruleAuditDefinition)
    {
        RuleAuditSchema ruleAuditSchema=convertEntityToDTO(ruleAuditDefinition);
        return this.objectMapper.convertValue(ruleAuditSchema,Map.class);
    }

    private RuleAuditSchema convertEntityToDTO(RuleAuditDefinition ruleAuditDefinition)
    {
        return this.objectMapper.convertValue(ruleAuditDefinition, RuleAuditSchema.class);
    }
}
