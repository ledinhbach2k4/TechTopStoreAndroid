package bach.dev.techtopstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bach.dev.techtopstore.auth.Auth;

public class AuthActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button loginButton;
    private TextView toggleAuthMode, titleText;
    private ProgressBar progressBar;
    private boolean isLoginMode = true;
    private Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = new Auth(this);

        // Kiểm tra nếu đã đăng nhập thì chuyển sang màn hình chính
        if (auth.isLoggedIn()) {
            startMainActivity();
            finish();
            return;
        }

        // Ánh xạ view
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        // Các thành phần không có trong activity_login.xml hiện tại
        // Sẽ cần thêm vào layout nếu muốn giữ đầy đủ chức năng
        confirmPasswordEditText = findViewById(R.id.edt_confirm_password); // Chưa có trong layout
        toggleAuthMode = findViewById(R.id.toggle_auth_mode);             // Chưa có trong layout
        titleText = findViewById(R.id.title_text);                        // Chưa có trong layout

        // Xử lý sự kiện nút chính
        loginButton.setOnClickListener(v -> {
            if (isLoginMode) {
                handleLogin();
            } else {
                handleRegister();
            }
        });

        // Xử lý chuyển đổi giữa đăng nhập/đăng ký (nếu toggleAuthMode tồn tại)
        if (toggleAuthMode != null) {
            toggleAuthMode.setOnClickListener(v -> toggleAuthMode());
        }
    }

    private void handleLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ các trường", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);

        // Giả lập đăng nhập thành công
        String mockToken = "mock_token_" + System.currentTimeMillis();
        auth.login(email, mockToken);

        progressBar.setVisibility(View.GONE);
        loginButton.setEnabled(true);
        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        startMainActivity();
    }

    private void handleRegister() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText != null ? confirmPasswordEditText.getText().toString().trim() : "";

        if (email.isEmpty() || password.isEmpty() || (!isLoginMode && confirmPassword.isEmpty())) {
            Toast.makeText(this, "Vui lòng điền đầy đủ các trường", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isLoginMode && !password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);

        // Giả lập đăng ký thành công
        String mockToken = "mock_token_" + System.currentTimeMillis();
        auth.login(email, mockToken);

        progressBar.setVisibility(View.GONE);
        loginButton.setEnabled(true);
        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        startMainActivity();
    }

    private void toggleAuthMode() {
        isLoginMode = !isLoginMode;

        if (isLoginMode) {
            if (titleText != null) titleText.setText("Đăng nhập");
            loginButton.setText("Đăng nhập");
            if (toggleAuthMode != null) toggleAuthMode.setText("Chưa có tài khoản? Đăng ký");
            if (confirmPasswordEditText != null) confirmPasswordEditText.setVisibility(View.GONE);
        } else {
            if (titleText != null) titleText.setText("Đăng ký");
            loginButton.setText("Đăng ký");
            if (toggleAuthMode != null) toggleAuthMode.setText("Đã có tài khoản? Đăng nhập");
            if (confirmPasswordEditText != null) confirmPasswordEditText.setVisibility(View.VISIBLE);
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}