package bach.dev.techtopstore.util;

public class Constants {
    // API Configuration
    public static final String API_BASE_PATH = "http://192.168.102.2:8080/api/";

    // API Endpoints
    public static final String LOGIN_ENDPOINT = "auth/login";
    public static final String LOGOUT_ENDPOINT = "auth/logout";
    public static final String USER_PROFILE_ENDPOINT = "user/profile";

    // Product
    public static final String PRODUCT_ID = "PRODUCT_ID";

    // SharedPreferences Configuration
    public static final String PREF_NAME = "TechTopStorePref";
    public static final int PREF_MODE = 0; // MODE_PRIVATE

    // Authentication Keys
    public static final String PREF_IS_LOGGED_IN = "is_logged_in";
    public static final String PREF_USER_ID = "user_id";
    public static final String PREF_USER_NAME = "user_name";
    public static final String PREF_USER_EMAIL = "user_email";
    public static final String PREF_USER_PHONE = "user_phone";
    public static final String PREF_USER_ADDRESS = "user_address";
    public static final String PREF_USER_AVATAR = "user_avatar";
    public static final String PREF_AUTH_TOKEN = "auth_token";
    public static final String PREF_TOKEN_EXPIRY = "token_expiry";

    // Intent/Bundle Keys
    public static final String INTENT_EXTRA_USER = "user_data";
    public static final String INTENT_EXTRA_FROM_SETTINGS = "from_settings";

    // Request Codes
    public static final int REQUEST_CODE_LOGIN = 1001;

    // Default Values
    public static final String DEFAULT_STRING_VALUE = "";
    public static final int DEFAULT_INT_VALUE = -1;
    public static final boolean DEFAULT_BOOLEAN_VALUE = false;

    // Date/Time Formats
    public static final String DATE_FORMAT_DISPLAY = "dd/MM/yyyy";
    public static final String TIME_FORMAT_DISPLAY = "HH:mm";
    public static final String DATE_TIME_FORMAT_API = "yyyy-MM-dd HH:mm:ss";

    // Pagination
    public static final int ITEMS_PER_PAGE = 10;

    // Error Messages
    public static final String ERROR_NETWORK = "Lỗi kết nối mạng";
    public static final String ERROR_SERVER = "Lỗi máy chủ";
    public static final String ERROR_UNAUTHORIZED = "Phiên đăng nhập hết hạn";
}