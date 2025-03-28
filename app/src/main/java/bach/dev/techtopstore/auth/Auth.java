package bach.dev.techtopstore.auth;
import android.content.Context;
import android.content.SharedPreferences;

public class Auth {
    private static final String PREF_NAME = "AuthPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_TOKEN = "userToken";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Auth(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Lưu trạng thái đăng nhập
    public void login(String email, String token) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_TOKEN, token);
        editor.apply();
    }

    // Kiểm tra xem user có đăng nhập không
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Lấy email của user
    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    // Lấy token của user
    public String getUserToken() {
        return sharedPreferences.getString(KEY_USER_TOKEN, null);
    }

    // Đăng xuất user
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
