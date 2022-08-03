package com.techsophy.tsf.rule.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RuleTestConstants
{
    //GlobalMessageSourceConstants
    public static final String  KEY="key";
    public static final String ARGS="args";

    //RuleControllerExceptionTest
    public static final String USER_DETAILS_NOT_FOUND_WITH_GIVEN_ID="UserDetails Not found with given id";
    public static final String RULE_NOT_FOUND_WITH_GIVEN_ID="Rule Not Found With Id";

    //RuleControllerConstants
    public final static String RULE_ID ="1";
    public final static String RULE_NAME = "RuleTest";
    public final static byte[] RULE_CONTENT ={10,20};
    public final static Integer RULE_VERSION = 1;
    public static final String TEST_ACTIVE_PROFILE ="test";
    public static final String BASE_URL_RULE = "/rule-modeler";
    public static final String VERSION_V1_1 = "/v1";
    public static final String RULES_URL = "/rules";
    public static final String RULES_BY_ID_URL ="/rules/{id}";
    public static final String SEARCH_RULES_URL = "/rules/search";
    public static final String CREATED_BY_ID_VALUE ="1";
    public static final Instant CREATED_ON_NOW =Instant.now();
    public static final String UPDATED_BY_ID_VALUE ="1";
    public static final String CREATED_BY_NAME="createdByName";
    public static final Instant UPDATED_ON_NOW =Instant.now();
    public final static String ID_OR_NAME_LIKE ="abc";
    public final static String PARAM_ID="id";
    public final static String PARAM_ID_VALUE="1";
    public final static String PARAM_NAME="name";
    public final static String PARAM_NAME_VALUE="RuleTest";
    public final static String PARAM_VERSION="version";
    public final static String PARAM_VERSION_VALUE="1";
    public final static String PARAM_CONTENT="content";
    public final static String PARAM_CONTENT_VALUE="abc";
    public final static String PARAM_INCLUDE_CONTENT="include-content";
    public final static String PARAM_DEPLOYMENT="deployment";
    public final static String PARAM_DEPLOYMENT_VALUE ="a";
    public final static String PARAM_ID_OR_NAME_LIKE ="idOrNameLike";
    public final static String PARAM_ID_OR_NAME_LIKE_VALUE ="abc";
    public static final String TENANT="tenant";
    public static final String TEST_ID="1";
    public static final Integer PAGE_VALUE=1;
    public static final Integer PAGE_SIZE=1;
    public static final String PAGE_NUMBER="1";
    public static final String PAGE_SIZE_KEY="pageSize";
    public static final String PAGE_SIZE_VALUE="1";
    public static final String TRUE="true";

    //RuleDefinitionCustomRepository
    public static final String ID_NUMBER ="1";
    public static final String ID_2="2";

    //RuleServiceConstants
    public static final String TEST_RULES_DATA = "testdata/rule-schema1.json";
    public static final String  TEST_RULES_DATA1= "testdata/rule-schema3.json";
    public static final String TEST_RULES_DATA_SCHEMA = "testdata/rule-schema2.json";
    public static final String Q="abc";

    //INITILIZATION CONSTANTS
    public static final String DEPARTMENT="department";
    public static final String  NULL=null;
    public static final String EMAIL_ID="emailId";
    public static final String MOBILE_NUMBER="mobileNumber";
    public static final String LAST_NAME="lastName";
    public static final String  FIRST_NAME="firstName";
    public static final String USER_NAME="userName";
    public static final String ID="id";
    public static final String UPDATED_ON="updatedOn";
    public static final String UPDATED_BY_NAME="updatedByName";
    public static final String UPDATED_BY_ID="updatedById";
    public static final String CREATED_ON="createdOn";
    public static final String CREATEDE_BY_NAME ="createdByName";
    public static final String CREATED_BY_ID="createdById";
    public static final String USER_FIRST_NAME ="tejaswini";
    public static final String USER_LAST_NAME ="Kaza";
    public static final String NUMBER="1234567890";
    public static final String MAIL_ID ="tejaswini.k@techsophy.com";
    public static final String LOGGED_USER_ID = "847117072898674688";
    public static final String EMPTY_STRING="";

    //TokenUtilsTest
    public static final String TOKEN_TXT_PATH = "testdata/token.txt";
    public static final String TECHSOPHY_PLATFORM="techsophy-platform";
    public final static String TEST_SORT="name";

    //UserDetailsTestConstants
    public static final String  USER_DETAILS_RETRIEVED_SUCCESS="User details retrieved successfully";
    public static final String  ABC="abc";
    public static final String TEST_TOKEN="token";
    public static final String INITIALIZATION_DATA="{\"data\":[{\"id\":\"847117072898674688\",\"userName\":\"tejaswini\",\"firstName\":\"Kaza\",\"lastName\":\"Tejaswini\",\"mobileNumber\":\"1234567890\",\"emailId\":\"tejaswini.k@techsophy.com\",\"department\":null,\"createdById\":null,\"createdByName\":null,\"createdOn\":null,\"updatedById\":null,\"updatedByName\":null,\"updatedOn\":null}],\"success\":true,\"message\":\"User details retrieved successfully\"}\n";

    //WEBCLIENTWrapperTestConstants
    public static final String LOCAL_HOST_URL="http://localhost:8080";
    public static final String TEST="test";

    //WebClientWrapper
    public static final String GET="GET";
    public static final String PUT="PUT";
    public static final String DELETE="DELETE";
    public static final String POST="POST";
}


