package com.techsophy.tsf.rule.repository;

import com.techsophy.tsf.rule.entity.RuleDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Stream;

public interface RuleDefinitionCustomRepository
{
    List<RuleDefinition> findByNameOrId(String idOrNameLike) throws UnsupportedEncodingException;
    Stream<RuleDefinition> findRulesByQSorting(String q, Sort sort);
    Page<RuleDefinition> findRulesByQPageable(String q, Pageable pageable);
    List<RuleDefinition> findByIdIn(List<String> idList);
}
