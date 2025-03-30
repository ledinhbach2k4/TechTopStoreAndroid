package bach.dev.techtopstore.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import bach.dev.techtopstore.R;
import bach.dev.techtopstore.LoginActivity;
import bach.dev.techtopstore.util.Constants;

public class SettingFragment extends Fragment {

    private LinearLayout layoutUserInfo;
    private TextView tvUserName, tvUserEmail;
    private Button btnLogin, btnLogout;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Initialize views
        layoutUserInfo = view.findViewById(R.id.layoutUserInfo);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnLogout = view.findViewById(R.id.btnLogout);

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(Constants.PREF_NAME, Constants.PREF_MODE);

        // Check login status and update UI
        checkLoginStatus();

        // Set click listeners
        btnLogin.setOnClickListener(v -> navigateToLogin());
        btnLogout.setOnClickListener(v -> performLogout());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check login status again when fragment resumes
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean(Constants.PREF_IS_LOGGED_IN, false);

        if (isLoggedIn) {
            // User is logged in
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            layoutUserInfo.setVisibility(View.VISIBLE);

            // Display user info
            String name = sharedPreferences.getString(Constants.PREF_USER_NAME, "");
            String email = sharedPreferences.getString(Constants.PREF_USER_EMAIL, "");

            tvUserName.setText(name.isEmpty() ? "Người dùng" : name);
            tvUserEmail.setText(email);
        } else {
            // User is not logged in
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            layoutUserInfo.setVisibility(View.GONE);
        }
    }

    private void navigateToLogin() {
        startActivity(new Intent(requireActivity(), LoginActivity.class));
    }

    private void performLogout() {
        // Clear user data from SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.PREF_IS_LOGGED_IN);
        editor.remove(Constants.PREF_USER_NAME);
        editor.remove(Constants.PREF_USER_EMAIL);
        editor.remove(Constants.PREF_AUTH_TOKEN);
        editor.apply();

        // Update UI
        checkLoginStatus();

        // Optional: Show logout message
        if (getActivity() != null) {
            // You can use Toast or Snackbar here
            // Toast.makeText(getActivity(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
        }
    }
}