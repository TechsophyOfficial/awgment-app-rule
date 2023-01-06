package com.techsophy.tsf.rule.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RuleModelerConstants
{
    //LoggingHandler
    public static final String CONTROLLER_CLASS_PATH = "execution(* com.techsophy.tsf.rule.controller..*(..))";
    public static final String SERVICE_CLASS_PATH= "execution(* com.techsophy.tsf.rule.service..*(..))";
    public static final String EXCEPTION = "ex";
    public static final String IS_INVOKED_IN_CONTROLLER= "() is invoked in controller ";
    public static final String IS_INVOKED_IN_SERVICE= "() is invoked in service ";
    public static final String EXECUTION_IS_COMPLETED_IN_CONTROLLER="() execution is completed  in controller";
    public static final String EXECUTION_IS_COMPLETED_IN_SERVICE="() execution is completed  in service";
    public static final String EXCEPTION_THROWN="An exception has been thrown in ";
    public static final String CAUSE="Cause : ";
    public static final String BRACKETS_IN_CONTROLLER="() in controller";
    public static final String BRACKETS_IN_SERVICE="() in service";

    //JWTRoleConverter
    public static final String CLIENT_ROLES="clientRoles";
    public static final String USER_INFO_URL= "/protocol/openid-connect/userinfo";
    public static final String TOKEN_VERIFICATION_FAILED="Token verification failed";
    public static final String AWGMENT_ROLES_MISSING_IN_CLIENT_ROLES="AwgmentRoles are missing in clientRoles";
    public static final String CLIENT_ROLES_MISSING_IN_USER_INFORMATION="ClientRoles are missing in the userInformation";

    /*LocaleConfig Constants*/
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String BASENAME_ERROR_MESSAGES = "classpath:errorMessages";
    public static final String BASENAME_MESSAGES = "classpath:messages";
    public static final Long   CACHEMILLIS = 3600L;
    public static final Boolean USE_DEFAULT_CODE_MESSAGE = true;

    /*TenantAuthenticationManagerConstants*/
    public static final String KEYCLOAK_ISSUER_URI = "${keycloak.issuer-uri}";

    /*TokenConfigConstants*/
    public static final String TOKEN_AUTHORIZATION="Authorization";

    // Roles
    public static final String OR=" or ";
    public static final String HAS_ANY_AUTHORITY="hasAnyAuthority('";
    public static final String HAS_ANY_AUTHORITY_ENDING="')";
    public static final String AWGMENT_RULE_CREATE_OR_UPDATE = "awgment-rule-create-or-update";
    public static final String AWGMENT_RULE_READ = "awgment-rule-read";
    public static final String AWGMENT_RULE_DELETE = "awgment-rule-delete";
    public static final String AWGMENT_RULE_ALL ="awgment-rule-all";
    public static final String CREATE_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_RULE_CREATE_OR_UPDATE +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+AWGMENT_RULE_ALL+HAS_ANY_AUTHORITY_ENDING;
    public static final String READ_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_RULE_READ +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+AWGMENT_RULE_ALL+HAS_ANY_AUTHORITY_ENDING;
    public static final String DELETE_OR_ALL_ACCESS =HAS_ANY_AUTHORITY+ AWGMENT_RULE_DELETE +HAS_ANY_AUTHORITY_ENDING+OR+HAS_ANY_AUTHORITY+AWGMENT_RULE_ALL+HAS_ANY_AUTHORITY_ENDING;

    /*RuleControllerConstants*/
    public static final String BASE_URL = "/rule-modeler";
    public static final String VERSION_V1 = "/v1";
    public static final String HISTORY ="/history";
    public static final String RULES_URL = "/rules";
    public static final String RULE_BY_ID_URL = "/rules/{id}";
    public static final String RULE_VERSION_BY_ID_URL = "/rules/{id}/{version}";
    public static final String SEARCH_RULES_URL = "/rules/search";
    public static final String ID = "id";
    public static final String NAME="name";
    public static final String VERSION="version";
    public static final String CONTENT="content";
    public static final String PAGE="page";
    public static final String SIZE="size";
    public static final String SORT_BY="sort-by";
    public static final String QUERY ="q";
    public static final String INCLUDE_CONTENT = "include-content";
    public static final String ID_OR_NAME_LIKE = "idOrNameLike";
    public static final String DEPLOYMENT = "deployment";
    public static final String ACCOUNTS_URL ="/accounts/v1/users";
    public static final String MANDATORY_FIELDS="?only-mandatory-fields=true&";
    public static final String SAVE_RULE_SUCCESS="SAVE_RULE_SUCCESS";
    public static final String GET_RULE_SUCCESS="GET_RULE_SUCCESS";
    public static final String DELETE_RULE_SUCCESS="DELETE_RULE_SUCCESS";

    /*RuleSchemaConstants*/
    public static final String NAME_NOT_BLANK="Name should not be blank";
    public static final String VERSION_NOT_NULL="Version should not be null";
    public static final String ID_NOT_NULL= "Id should not be null";
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String TIME_ZONE = "UTC";

    /*RuleDefinitionAuditRepositoryConstants*/
    public static final String FIND_BY_ID_QUERY ="{'ruleId' : ?0 , 'version' : ?1}";
    public static final String FIND_ALL_BY_ID_QUERY="{'ruleId' : ?0 }";

    /*RuleDefinitionConstants*/
    public static final String TP_RULE_DEFINITION_COLLECTION="tp_rule_definition";
    public static final String TP_RULE_DEFINITION_AUDIT_COLLECTION ="tp_rule_definition_audit";

    /*RuleDefinitionCustomRepositoryImplConstants*/
    public static final String RULE_DEFINITION_ID="_id";
    public static final String RULE_DEFINITION_NAME ="name";

    /*RuleServiceImplConstants*/
    public static final String REGEX_COMMA=",";
    public static final String FIRST_NAME="firstName";
    public static final String LAST_NAME="lastName";

    /*TokenUtilsAndWebclientWrapperConstants*/
    public static final String PREFERED_USERNAME="preferred_username";
    public static final String EMPTY_STRING="";
    public static final String BEARER="Bearer ";
    public static final String REGEX_SPLIT="\\.";
    public static final String ISS="iss";
    public static final String URL_SEPERATOR="/";
    public static final int SEVEN=7;
    public static final int ONE=1;
    public static final String DESCENDING="desc";
    public static final String CREATED_ON="createdOn";
    public static final String CREATED_ON_ASC="createdOn: ASC";
    public static final String  COLON=":";
    public static final String DEFAULT_PAGE_LIMIT= "${default.pagelimit}";

    /*UserDetailsConstants*/
    public static final String GATEWAY_URI="${gateway.uri}";
    public static final String LOGGED_USER="loggeduser";
    public static final String TOKEN="token";
    public static final String AUTHORIZATION="Authorization";
    public static final String CONTENT_TYPE="Content-Type";
    public static final String APPLICATION_JSON ="application/json";
    public static final String FILTER_COLUMN="filter-column=loginId&filter-value=";
    public static final String RESPONSE="response";
    public static final String DATA="data";
    public static final String GATEWAY="gateway";
    public static final String GET="GET";
    public static final String PUT="PUT";
    public static final String DELETE="DELETE";

    /*MainMethodConstants*/
    public static final String PACKAGE_NAME ="com.techsophy.tsf.rule.*";
    public static final String MULTITENANCY_PACKAGE_NAME ="com.techsophy.multitenancy.mongo.*";
    public static final String RULE_MODELER ="tp-app-rule";
    public static final String RULE_MODELER_API_VERSION_1 = "Rule Modeler API v1";
    public static final String GATEWAY_URL ="${gateway.uri}";
    public static final String SERVICE = "service";

}
