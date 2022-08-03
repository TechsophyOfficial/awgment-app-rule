package com.techsophy.tsf.rule.repository;

import com.techsophy.tsf.rule.entity.RuleDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface RuleDefinitionRepository extends MongoRepository<RuleDefinition, Long>, RuleDefinitionCustomRepository
{
    Optional<RuleDefinition> findById(BigInteger id);

    boolean existsById(BigInteger id);

    int deleteById(BigInteger id);
}
