package com.techsophy.tsf.rule.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.math.BigInteger;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.TP_RULE_DEFINITION_COLLECTION;

@EqualsAndHashCode(callSuper = true)
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = TP_RULE_DEFINITION_COLLECTION)
public class RuleDefinition extends Auditable implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    private BigInteger id;
    private String name;
    private Integer version;
    private byte[] content;
}
