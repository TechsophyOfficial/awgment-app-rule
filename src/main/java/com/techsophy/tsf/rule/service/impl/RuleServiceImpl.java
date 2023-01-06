package com.techsophy.tsf.rule.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.idgenerator.IdGeneratorImpl;
import com.techsophy.tsf.rule.config.GlobalMessageSource;
import com.techsophy.tsf.rule.dto.PaginationResponsePayload;
import com.techsophy.tsf.rule.dto.RuleAuditSchema;
import com.techsophy.tsf.rule.dto.RuleResponse;
import com.techsophy.tsf.rule.dto.RuleSchema;
import com.techsophy.tsf.rule.entity.RuleDefinition;
import com.techsophy.tsf.rule.exception.EntityIdNotFoundException;
import com.techsophy.tsf.rule.exception.UserDetailsIdNotFoundException;
import com.techsophy.tsf.rule.repository.RuleDefinitionRepository;
import com.techsophy.tsf.rule.service.RuleAuditService;
import com.techsophy.tsf.rule.service.RuleService;
import com.techsophy.tsf.rule.utils.TokenUtils;
import com.techsophy.tsf.rule.utils.UserDetails;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.techsophy.tsf.rule.constants.ErrorConstants.LOGGED_IN_USER_ID_NOT_FOUND;
import static com.techsophy.tsf.rule.constants.ErrorConstants.RULE_NOT_FOUND;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.*;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class RuleServiceImpl implements RuleService
{
    private final RuleDefinitionRepository ruleDefinitionRepository;
    private final RuleAuditService ruleAuditService;
    private final ObjectMapper objectMapper;
    private final UserDetails userDetails;
    private final IdGeneratorImpl idGeneratorImpl;
    private final GlobalMessageSource globalMessageSource;
    private final TokenUtils tokenUtils;

    @Override
    public RuleResponse saveRule(String id, String name, Integer version, String content) throws JsonProcessingException
    {
        RuleDefinition ruleData;
        BigInteger uniqueId;
        Map<String,Object> loggedInUserDetails =userDetails.getUserDetails().get(0);
        if (StringUtils.isEmpty(loggedInUserDetails.get(ID).toString()))
        {
            throw new UserDetailsIdNotFoundException(LOGGED_IN_USER_ID_NOT_FOUND,globalMessageSource.get(LOGGED_IN_USER_ID_NOT_FOUND,loggedInUserDetails.get(ID).toString()));
        }
        BigInteger loggedInUserId = BigInteger.valueOf(Long.parseLong(loggedInUserDetails.get(ID).toString()));
        if(StringUtils.isEmpty(id))
        {
            uniqueId = idGeneratorImpl.nextId();
            version = 1;
            ruleData=setCreatedDetails(uniqueId,name,version,content.getBytes(),loggedInUserDetails);
        }
        else
        {
            uniqueId= BigInteger.valueOf(Long.parseLong(id));
            if (!ruleDefinitionRepository.existsById(uniqueId))
            {
                  ruleData=setCreatedDetails(uniqueId,name,version,content.getBytes(),loggedInUserDetails);
            }
            else
            {
                ruleData= ruleDefinitionRepository.findById(uniqueId).orElseThrow(() -> new UserDetailsIdNotFoundException(LOGGED_IN_USER_ID_NOT_FOUND,globalMessageSource.get(LOGGED_IN_USER_ID_NOT_FOUND,loggedInUserDetails.get(ID).toString())));
                ruleData.setName(name);
                ruleData.setVersion(ruleData.getVersion()+1);
                ruleData.setContent(content.getBytes());
            }
            }
        ruleData.setUpdatedOn(Instant.now());
        ruleData.setUpdatedById(loggedInUserId);
        RuleDefinition ruleDefinition=this.ruleDefinitionRepository.save(ruleData);
        RuleAuditSchema ruleAuditSchema =this.objectMapper.convertValue(ruleData,RuleAuditSchema.class);
        ruleAuditSchema.setId(idGeneratorImpl.nextId().toString());
        ruleAuditSchema.setRuleId(ruleDefinition.getId().toString());
        this.ruleAuditService.saveRule(ruleAuditSchema);
        return this.objectMapper.convertValue(ruleData, RuleResponse.class);
    }

    @Override
    public Stream<RuleSchema> getAllRules(boolean includeProcessContent, String deploymentIdList, String q, Sort sort)
    {
        if(StringUtils.isNotBlank(deploymentIdList))
        {
            String[] idList = deploymentIdList.split(REGEX_COMMA);
            List<String> deploymentList = Arrays.asList(idList);
            return this.ruleDefinitionRepository.findByIdIn(deploymentList).stream()
                    .map(flows ->
                            this.objectMapper.convertValue(flows, RuleSchema.class));
        }
        if(StringUtils.isEmpty(q))
        {
            return this.ruleDefinitionRepository.findAll(sort).stream()
                    .map(this::convertEntityToDTO)
                    .map(ruleSchema ->
                    {
                        if (includeProcessContent)
                        {
                            return ruleSchema;
                        }
                        return ruleSchema.withContent(null);
                    });
        }
        return this.ruleDefinitionRepository.findRulesByQSorting(q,sort)
                .map(this::convertEntityToDTO)
                .map(ruleSchema ->
                {
                    if (includeProcessContent)
                    {
                        return ruleSchema;
                    }
                    return ruleSchema.withContent(null);
                });
    }

    @Override
    public PaginationResponsePayload getAllRules(String q, boolean includeProcessContent, Pageable pageable)
    {
        if(isEmpty(q))
        {
            Page<RuleDefinition> ruleDefinitions = this.ruleDefinitionRepository.findAll(pageable);
            List<Map<String,Object>> ruleSchemaList = ruleDefinitions.stream()
                    .map(this::convertEntityToMap).collect(Collectors.toList());
            return tokenUtils.getPaginationResponsePayload(ruleDefinitions, ruleSchemaList);
        }
        Page<RuleDefinition> ruleDefinitionPage = this.ruleDefinitionRepository.findRulesByQPageable(q,pageable);
        List<Map<String,Object>> ruleSchemaList = ruleDefinitionPage.stream()
                .map(this::convertEntityToMap).collect(Collectors.toList());
        return tokenUtils.getPaginationResponsePayload(ruleDefinitionPage, ruleSchemaList);
    }

    @Override
    public RuleSchema getRuleById(String id)
    {
        RuleDefinition rule =
                this.ruleDefinitionRepository.findById(BigInteger.valueOf(Long.parseLong(id)))
                        .orElseThrow(() -> new EntityIdNotFoundException(RULE_NOT_FOUND,globalMessageSource.get(RULE_NOT_FOUND,id)));
        return this.objectMapper.convertValue(rule,RuleSchema.class);
    }

    @Override
    public void deleteRuleById(String id)
    {
        if (!ruleDefinitionRepository.existsById(BigInteger.valueOf(Long.parseLong(id))))
        {

            throw new EntityIdNotFoundException(RULE_NOT_FOUND,globalMessageSource.get(RULE_NOT_FOUND,id));
        }
        this.ruleDefinitionRepository.deleteById(BigInteger.valueOf(Long.parseLong(id)));
    }

    @Override
    public Stream<RuleSchema> searchRuleByIdOrNameLike(String idOrNameLike) throws UnsupportedEncodingException
    {
        return this.ruleDefinitionRepository.findByNameOrId(idOrNameLike).stream().
                map(this::convertEntityToDTO).map(ruleSchema ->ruleSchema.withContent(null));
    }

    private Map<String,Object> convertEntityToMap(RuleDefinition ruleDefinition)
    {
        RuleSchema ruleSchema=convertEntityToDTO(ruleDefinition);
        return this.objectMapper.convertValue(ruleSchema,Map.class);
    }

    private RuleSchema convertEntityToDTO(RuleDefinition ruleDefinition)
    {
        return this.objectMapper.convertValue(ruleDefinition, RuleSchema.class);
    }

    private RuleDefinition setCreatedDetails(BigInteger uniqueId, String name, Integer version, byte[] content, Map<String,Object> loggedInUserDetails)
    {
        RuleDefinition ruleData;
        BigInteger loggedInUserId = BigInteger.valueOf(Long.parseLong(loggedInUserDetails.get(ID).toString()));
        ruleData = new RuleDefinition(uniqueId, name, version, content);
        ruleData.setCreatedOn(Instant.now());
        ruleData.setCreatedById(loggedInUserId);
        return ruleData;
    }
}

