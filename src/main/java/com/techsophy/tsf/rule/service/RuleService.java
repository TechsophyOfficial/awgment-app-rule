package com.techsophy.tsf.rule.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.rule.dto.PaginationResponsePayload;
import com.techsophy.tsf.rule.dto.RuleResponse;
import com.techsophy.tsf.rule.dto.RuleSchema;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

public interface RuleService
{
    RuleResponse saveRule(String id, String name, Integer version, String content) throws JsonProcessingException;

    Stream<RuleSchema> getAllRules(boolean includeProcessContent, String deploymentIdList, String q, Sort sort);

    PaginationResponsePayload getAllRules(String q, boolean includeProcessContent, Pageable pageable);

    RuleSchema getRuleById(String id);

    void deleteRuleById(String id);

    Stream<RuleSchema> searchRuleByIdOrNameLike(String idOrNameLike) throws UnsupportedEncodingException;
}
