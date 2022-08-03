package com.techsophy.tsf.rule.repository;

import com.techsophy.tsf.rule.entity.RuleAuditDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Stream;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.FIND_ALL_BY_ID_QUERY;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.FIND_BY_ID_QUERY;

public interface RuleDefinitionAuditRepository extends MongoRepository<RuleAuditDefinition, Long>
{
    @Query(FIND_BY_ID_QUERY)
    Optional<RuleAuditDefinition> findById(BigInteger id, Integer version);

    @Query(FIND_ALL_BY_ID_QUERY)
    Stream<RuleAuditDefinition> findAllById(BigInteger id, Sort sort);

    @Query(FIND_ALL_BY_ID_QUERY)
    Page<RuleAuditDefinition> findAllById(BigInteger id, Pageable pageable);
}
