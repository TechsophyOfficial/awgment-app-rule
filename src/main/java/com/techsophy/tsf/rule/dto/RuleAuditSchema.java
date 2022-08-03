package com.techsophy.tsf.rule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.*;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleAuditSchema
{
    @NotNull(message = ID_NOT_NULL) String id;
    @NotNull(message = ID_NOT_NULL) String ruleId;
    @NotBlank(message = NAME_NOT_BLANK) String name;
    @NotNull(message = VERSION_NOT_NULL) Integer version;
    byte[] content;
    String createdById;
    Instant createdOn;
    String createdByName;
    String updatedById;
    Instant updatedOn;
    String updatedByName;
}
