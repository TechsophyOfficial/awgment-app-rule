package com.techsophy.tsf.rule.config;

import com.techsophy.idgenerator.IdGeneratorImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import static com.techsophy.tsf.rule.constants.RuleModelerConstants.*;

@Configuration
public class ApplicationConfig
{
    @Bean
    public IdGeneratorImpl idGeneratorImpl()
    {
        return new IdGeneratorImpl();
    }

    @Value(GATEWAY_URL)
    String gatewayUrl;
    @Bean
    public OpenAPI customOpenAPI()
    {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title(RULE_MODELER).version(VERSION_V1).description(RULE_MODELER_API_VERSION_1))
                .servers( List.of(new Server().url(gatewayUrl)));
    }
}
