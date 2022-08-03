package com.techsophy.tsf.rule.repository.impl;

import com.techsophy.tsf.rule.entity.RuleDefinition;
import com.techsophy.tsf.rule.repository.RuleDefinitionCustomRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.*;

@AllArgsConstructor
public class RuleDefinitionCustomRepositoryImpl implements RuleDefinitionCustomRepository
{
    private final MongoTemplate mongoTemplate;

    @Override
    public List<RuleDefinition> findByNameOrId(String idOrNameLike)
    {
        Query query = new Query();
        String searchString = URLDecoder.decode(idOrNameLike, StandardCharsets.UTF_8);
        query.addCriteria(new Criteria().orOperator(Criteria.where(RULE_DEFINITION_ID).regex(searchString), Criteria.where(RULE_DEFINITION_NAME).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))));
        return mongoTemplate.find(query, RuleDefinition.class);
    }

    @Override
    public Stream<RuleDefinition> findRulesByQSorting(String q, Sort sort) {
        Query query = new Query();
        String searchString = URLDecoder.decode(q, StandardCharsets.UTF_8);
        query.addCriteria(new Criteria().orOperator(Criteria.where(RULE_DEFINITION_ID).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(RULE_DEFINITION_NAME).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(VERSION).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(CONTENT).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
        ));
        query.with(Sort.by(Sort.Direction.ASC, NAME));
        return mongoTemplate.find(query, RuleDefinition.class).stream();
    }

    @Override
    public Page<RuleDefinition> findRulesByQPageable(String q, Pageable pageable) {
        Query query = new Query();
        Query countQuery = new Query();
        String searchString = URLDecoder.decode(q, StandardCharsets.UTF_8);
        query.addCriteria(new Criteria().orOperator(Criteria.where(RULE_DEFINITION_ID).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(RULE_DEFINITION_NAME).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(VERSION).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(CONTENT).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
        )).with(pageable);
        List<RuleDefinition> ruleDefinitions = mongoTemplate.find(query, RuleDefinition.class);
        query.with(Sort.by(Sort.Direction.ASC, NAME));
        countQuery.addCriteria(new Criteria().orOperator(Criteria.where(RULE_DEFINITION_ID).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(RULE_DEFINITION_NAME).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(VERSION).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
                Criteria.where(CONTENT).regex(Pattern.compile(searchString, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
        ));
        long count=mongoTemplate.count(countQuery,RuleDefinition.class);
        return new PageImpl<>(ruleDefinitions, pageable,count );
    }

    @Override
    public List<RuleDefinition> findByIdIn(List<String> idList)
    {
        Query query = new Query(Criteria.where(RULE_DEFINITION_ID).in(idList));
        return mongoTemplate.find(query, RuleDefinition.class);
    }
}
