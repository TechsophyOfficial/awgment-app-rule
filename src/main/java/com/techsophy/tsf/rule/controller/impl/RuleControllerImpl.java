package com.techsophy.tsf.rule.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.rule.config.GlobalMessageSource;
import com.techsophy.tsf.rule.controller.RuleController;
import com.techsophy.tsf.rule.dto.PaginationResponsePayload;
import com.techsophy.tsf.rule.dto.RuleResponse;
import com.techsophy.tsf.rule.dto.RuleSchema;
import com.techsophy.tsf.rule.model.ApiResponse;
import com.techsophy.tsf.rule.service.RuleService;
import com.techsophy.tsf.rule.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.*;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class RuleControllerImpl implements RuleController
{
    private final RuleService ruleService;
    private final GlobalMessageSource globalMessageSource;
    private final TokenUtils tokenUtils;

    @Override
    public ApiResponse<RuleResponse> saveRule(String id, String name, Integer version, String content) throws JsonProcessingException
    {
        RuleResponse data= ruleService.saveRule(id,name,version,content);
        return new ApiResponse<>(data, true, globalMessageSource.get(SAVE_RULE_SUCCESS));
    }

    @Override
    public ApiResponse getAllRules(boolean includeRuleContent, String deploymentIdList,String q, Integer page, Integer pageSize, String[] sortBy)
    {
        if (page == null)
        {
            return new ApiResponse<>(ruleService.getAllRules(includeRuleContent, deploymentIdList, q, tokenUtils.getSortBy(sortBy)), true,
                    globalMessageSource.get(GET_RULE_SUCCESS));
        }
        PaginationResponsePayload paginationResponsePayload = ruleService.getAllRules(q, includeRuleContent, tokenUtils.getPageRequest(page, pageSize, sortBy));
        return new ApiResponse<>(paginationResponsePayload, true, globalMessageSource.get(GET_RULE_SUCCESS));
    }

    @Override
    public ApiResponse<RuleSchema> getRuleById(String id)
   {   ruleService.getRuleById(id);
     return new ApiResponse<>(ruleService.getRuleById(id), true, globalMessageSource.get(GET_RULE_SUCCESS));
   }

    @Override
    public ApiResponse<Void> deleteRuleById(String id)
    {
        ruleService.deleteRuleById(id);
        return new ApiResponse<>(null, true, globalMessageSource.get(DELETE_RULE_SUCCESS));
    }

    @Override
    public ApiResponse<Stream<RuleSchema>> searchRuleByIdOrNameLike(String idOrNameLike) throws UnsupportedEncodingException
    {
        return new ApiResponse<>(this.ruleService.searchRuleByIdOrNameLike(idOrNameLike), true, globalMessageSource.get(GET_RULE_SUCCESS));
    }
}
