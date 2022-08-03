package com.techsophy.tsf.rule.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.rule.dto.RuleAuditSchema;
import com.techsophy.tsf.rule.dto.RuleResponse;
import com.techsophy.tsf.rule.model.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Stream;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.*;

@RequestMapping(BASE_URL+VERSION_V1+ HISTORY)
@Validated
public interface RuleAuditController
{
    @PostMapping(RULES_URL)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    ApiResponse<RuleResponse> saveRule(RuleAuditSchema ruleAuditSchema) throws JsonProcessingException;

    @GetMapping(RULE_BY_ID_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<Stream<RuleAuditSchema>> getAllRules(@PathVariable(ID) String id,
                                                     @RequestParam(INCLUDE_CONTENT) boolean includeProcessContent,
                                                     @RequestParam(value = PAGE, required = false) Integer page,
                                                     @RequestParam(value =SIZE, required = false) Integer pageSize,
                                                     @RequestParam(value = SORT_BY, required = false) String[] sortBy);

    @GetMapping(RULE_VERSION_BY_ID_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<RuleAuditSchema> getRulesById(@PathVariable(ID) String id, @PathVariable(VERSION) Integer version);
}
