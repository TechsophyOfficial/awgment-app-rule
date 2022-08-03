package com.techsophy.tsf.rule.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.rule.dto.RuleResponse;
import com.techsophy.tsf.rule.dto.RuleSchema;
import com.techsophy.tsf.rule.model.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.*;

@RequestMapping(BASE_URL+VERSION_V1)
@Validated
public interface RuleController
{
    @PostMapping(RULES_URL)
    @PreAuthorize(CREATE_OR_ALL_ACCESS)
    ApiResponse<RuleResponse> saveRule(@RequestParam(value = ID,required =false)  String id,
                                       @RequestParam(NAME) @NotEmpty @NotNull String name,
                                       @RequestParam(value =VERSION,required = false) Integer version,
                                       @RequestParam(CONTENT)   @NotEmpty @NotNull String content) throws JsonProcessingException;

    @GetMapping(RULES_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<Stream<RuleSchema>> getAllRules(@RequestParam(INCLUDE_CONTENT) boolean includeRuleContent,
                                                @RequestParam(value= DEPLOYMENT,required = false) String deploymentIdList,
                                                @RequestParam(value = QUERY,required = false) String q,
                                                @RequestParam(value = PAGE, required = false) Integer page,
                                                @RequestParam(value =SIZE, required = false) Integer pageSize,
                                                @RequestParam(value = SORT_BY, required = false) String[] sortBy);

    @GetMapping(RULE_BY_ID_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<RuleSchema> getRuleById(@PathVariable(ID) String id);

    @DeleteMapping(RULE_BY_ID_URL)
    @PreAuthorize(DELETE_OR_ALL_ACCESS)
    ApiResponse<Void> deleteRuleById(@PathVariable(ID) String id);

    @GetMapping(SEARCH_RULES_URL)
    @PreAuthorize(READ_OR_ALL_ACCESS)
    ApiResponse<Stream<RuleSchema>> searchRuleByIdOrNameLike(@RequestParam(ID_OR_NAME_LIKE) String idOrNameLike) throws UnsupportedEncodingException;
}
