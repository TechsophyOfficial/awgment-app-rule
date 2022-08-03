package com.techsophy.tsf.rule.dto;

import lombok.Value;
import lombok.With;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@With
@Value
public class RuleResponse
{
    @NotBlank String id;
    @NotNull Integer version;
}
