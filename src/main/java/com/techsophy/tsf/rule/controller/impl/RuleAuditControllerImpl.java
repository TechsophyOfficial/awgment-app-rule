package com.techsophy.tsf.rule.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.rule.config.GlobalMessageSource;
import com.techsophy.tsf.rule.controller.RuleAuditController;
import com.techsophy.tsf.rule.dto.RuleAuditSchema;
import com.techsophy.tsf.rule.dto.RuleResponse;
import com.techsophy.tsf.rule.model.ApiResponse;
import com.techsophy.tsf.rule.service.RuleAuditService;
import com.techsophy.tsf.rule.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.GET_RULE_SUCCESS;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.SAVE_RULE_SUCCESS;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class RuleAuditControllerImpl implements RuleAuditController
{
    private final RuleAuditService ruleAuditService;
    private final GlobalMessageSource globalMessageSource;
    private final TokenUtils tokenUtils;

    public ApiResponse<RuleResponse> saveRule(RuleAuditSchema ruleAuditSchema) throws JsonProcessingException
    {
        return new ApiResponse<>(ruleAuditService.saveRule(ruleAuditSchema),
                true, globalMessageSource.get(SAVE_RULE_SUCCESS));
    }

    @Override
    public ApiResponse getAllRules(String id, boolean includeProcessContent, Integer page, Integer pageSize, String[] sortBy)
    {
        if(page==null)
        {
            return new ApiResponse<>(ruleAuditService.getAllRules(id, includeProcessContent,tokenUtils.getSortBy(sortBy) ), true, globalMessageSource.get(GET_RULE_SUCCESS));
        }
        return new ApiResponse<>(ruleAuditService.getAllRules(id, includeProcessContent, tokenUtils.getPageRequest(page,pageSize,sortBy)), true, globalMessageSource.get(GET_RULE_SUCCESS));
    }

    @Override
    public ApiResponse getRulesById(String id, Integer version)
    {
        return new ApiResponse<>(ruleAuditService.getRulesById(id,version), true, globalMessageSource.get(GET_RULE_SUCCESS));
    }
}
