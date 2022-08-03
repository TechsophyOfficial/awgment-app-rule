package com.techsophy.tsf.rule.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.rule.dto.PaginationResponsePayload;
import com.techsophy.tsf.rule.dto.RuleAuditSchema;
import com.techsophy.tsf.rule.dto.RuleResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

public interface RuleAuditService
{
    @Transactional(rollbackFor = Exception.class)
    RuleResponse saveRule(RuleAuditSchema ruleAuditSchema) throws JsonProcessingException;

    Stream<RuleAuditSchema> getAllRules(String id, boolean includeProcessContent, Sort sort);

    PaginationResponsePayload getAllRules(String id, boolean includeProcessContent, Pageable pageable);

    RuleAuditSchema getRulesById(String id, Integer version);

}
