package com.github.edu.client.common.constant;

public class CommonConstants {

    public final static String RESOURCE_TYPE_MENU = "menu";

    public final static String RESOURCE_TYPE_BTN = "button";

    public static final Integer EX_TOKEN_ERROR_CODE = 40211;

    // 用户token异常
    public static final Integer EX_USER_INVALID_CODE = 40221;
    // 客户端token异常
    public static final Integer EX_CLIENT_INVALID_CODE = 40331;

    public static final Integer EX_CLIENT_FORBIDDEN_CODE = 40341;

    public static final Integer EX_OTHER_CODE = 500;

    public static final String CONTEXT_KEY_USER_ID = "currentUserId";

    public static final String CONTEXT_KEY_USERNAME = "currentUserName";

    public static final String CONTEXT_KEY_USER_NAME = "currentUser";

    public static final String CONTEXT_KEY_USER_TOKEN = "currentUserToken";

    public static final String CONTEXT_KEY_USER_TYPE="currentUserType";

    public static final String JWT_KEY_USER_ID = "userId";

    public static final String WX_OPENID="openId";

    public static final String WX_IMAGE="wxImage";

    public static final String JWT_KEY_NAME = "name";

    public static final String JWT_USER_NAME="name";

    public static final String JWT_USER_PASSWORD="password";

    public static final String JWT_KEY_USER_TYPE="userType";

    public static final String JWT_KEY_TIME="time";

    public static final String MENU_TREE="treeMenu";

    //用户密码为空
    public static final Integer LOGIN_PASSWORD_ERROR_CODE=10001;

    //用户登录账号不存在，或者用户账号为空
    public static final Integer LOGIN_USERNAME_ERROR_CODE=10002;

    //用户名或者密码为空
    public static final Integer LOGIN_ERROR_CODE=10003;

    //常规系统异常
    public static final Integer BUSINESS_ERROR_CODE=50001;

    /**
     * 系统公钥
     */
    public static  String SYS_PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJAYSB4ydNwARAcBVH9tPb+CFRjE9LGKERRK43zKQjfW" +
            "6icq5fLoaV/CtDjqCs3T+wL0A0rk69MQyC2wJqnqGtsCAwEAAQ==";

    /**
     * 系统私钥
     */
    public static  String SYS_PRIVATE_KEY= "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAkBhIHjJ03ABEBwFUf209v4IVGMT0" +
            "sYoRFErjfMpCN9bqJyrl8uhpX8K0OOoKzdP7AvQDSuTr0xDILbAmqeoa2wIDAQABAkBFTxipRbXF" +
            "+G7AO9iL7lysGALTxjC4kOOEMj1X0eWl+4UKIO4oHgSwD353RmlAMpZoGyDcDcecXVmYSbEjy73p" +
            "AiEA1izn9AAZVXXI6ewDn8PuOeGlzNFcDy37KRTTYTOqXcUCIQCsO+VnjJA7BCX/OihdbD6op932" +
            "v1JfUc+9eQmnJnHAHwIgSUJJAQQ7pijI1YLeZnqtdO0DOoDuXV7cB1xRRXFXuAUCIDsKpU9j7iVs" +
            "/KGLjD7KVoh2Uy680z39y5sGHPsBwMi9AiEAubEMyKZJmY6lr8Cd35XrYIG9ir9yG6vJUXzprEBv" +
            "+78=";

}
