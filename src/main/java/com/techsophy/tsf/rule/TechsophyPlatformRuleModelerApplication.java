package com.techsophy.tsf.rule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.MULTITENANCY_PACKAGE_NAME;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.PACKAGE_NAME;

@RefreshScope
@EnableMongoRepositories
@EnableMongoAuditing
@SpringBootApplication
@ComponentScan({PACKAGE_NAME, MULTITENANCY_PACKAGE_NAME})
public class TechsophyPlatformRuleModelerApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(TechsophyPlatformRuleModelerApplication.class, args);
	}
}
