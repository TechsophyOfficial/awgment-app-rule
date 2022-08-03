package com.techsophy.tsf.rule.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import org.springframework.http.HttpStatus;
import java.time.Instant;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.DATE_PATTERN;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.TIME_ZONE;

@Value
public class ApiErrorResponse
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = TIME_ZONE)
    Instant timestamp;
    String message;
    String error;
    HttpStatus status;
    String path;
}
