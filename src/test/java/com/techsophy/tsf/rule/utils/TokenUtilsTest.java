package com.techsophy.tsf.rule.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.tsf.rule.config.GlobalMessageSource;
import com.techsophy.tsf.rule.dto.PaginationResponsePayload;
import com.techsophy.tsf.rule.entity.RuleAuditDefinition;
import com.techsophy.tsf.rule.exception.InvalidInputException;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.Cleanup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.techsophy.tsf.rule.constants.RuleTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles(TEST_ACTIVE_PROFILE)
class TokenUtilsTest
{
    @Mock
    SecurityContext securityContext;
    @Mock
    SecurityContextHolder securityContextHolder;
    @Mock
    GlobalMessageSource mockGlobalMessageSource;
    @InjectMocks
    TokenUtils tokenUtils;

    @BeforeEach
    public void init()
    {
        ReflectionTestUtils.setField(tokenUtils,"defaultPageLimit", 20);
    }

    @Test
    void getTokenFromIssuerTest() throws Exception
    {
        InputStream resource = new ClassPathResource(TOKEN_TXT_PATH).getInputStream();
        String result = IOUtils.toString(resource, StandardCharsets.UTF_8);
        String tenant = tokenUtils.getIssuerFromToken(result);
        assertThat(tenant).isEqualTo(TECHSOPHY_PLATFORM);
    }

    @Test
    void getPaginationResponsePayloadExceptionTest()
    {

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> tokenUtils.getPageRequest(null,1,null));
    }

    @Test
    void getSortByTest()
    {
        String s[]={"abc","xyz"};
        Sort tenant = tokenUtils.getSortBy(s);
        assertTrue(true);
    }

    @Test
    void getPaginationResponsePayloadTest1() throws IOException
    {
        List<Map<String,Object>> list=new ArrayList<>();
        list.add(Map.of("ab","ab"));
        ObjectMapper objectMapperTest = new ObjectMapper();
        @Cleanup InputStream inputStreamTest = new ClassPathResource(TEST_RULES_DATA).getInputStream();
        String rulesDataTest=new String(inputStreamTest.readAllBytes());
        RuleAuditDefinition ruleDefinitionTest=objectMapperTest.readValue(rulesDataTest,RuleAuditDefinition.class);
        Page<RuleAuditDefinition> page =new PageImpl<>(List.of(ruleDefinitionTest)) ;
        PaginationResponsePayload tenant = tokenUtils.getPaginationResponsePayload(page,list);
        assertTrue(true);
    }

    @Test
    void getTokenFromContext()
    {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Jwt jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        String token = tokenUtils.getTokenFromContext();
        assertThat(token).isNull();
    }

    @Test
    void getTokenFromContextException()
    {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        assertThatExceptionOfType(SecurityException.class)
                .isThrownBy(() -> tokenUtils.getLoggedInUserId());
    }

    @Test
    void getLoggedInUserIdTest()
    {
        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        assertThatExceptionOfType(SecurityException.class)
                .isThrownBy(() -> tokenUtils.getLoggedInUserId());
    }

    @Test
    void getPageRequestPageSizeNegativeTest()
    {
        PageRequest pageRequest=tokenUtils.getPageRequest(1,-1, new String[]{TEST_SORT});
        Assertions.assertNotNull(pageRequest);
    }

    @Test
    void getPageRequestInvalidInputException()
    {
        Assertions.assertThrows(InvalidInputException.class, () ->
                tokenUtils.getPageRequest(null,null,null));
    }
}
