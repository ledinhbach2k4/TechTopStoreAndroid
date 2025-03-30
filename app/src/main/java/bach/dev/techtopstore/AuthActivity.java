package bach.dev.techtopstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bach.dev.techtopstore.auth.Auth;

public class AuthActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword, edtConfirmPassword;
    private Button btnPrimaryAction;
    private TextView toggleAuthMode, titleText;
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
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        btnPrimaryAction = findViewById(R.id.btn_primary_action);
        toggleAuthMode = findViewById(R.id.toggle_auth_mode);
        titleText = findViewById(R.id.title_text);

        // Xử lý sự kiện nút chính
        btnPrimaryAction.setOnClickListener(v -> {
            if (isLoginMode) {
                handleLogin();
            } else {
                handleRegister();
            }
        });

        // Xử lý chuyển đổi giữa đăng nhập/đăng ký
        toggleAuthMode.setOnClickListener(v -> toggleAuthMode());
    }

    private void handleLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Giả lập đăng nhập thành công, trong thực tế sẽ call API
        // và nhận token từ server
        String mockToken = "mock_token_" + System.currentTimeMillis();

        // Lưu thông tin đăng nhập
        auth.login(email, mockToken);

        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        startMainActivity();
    }

    private void handleRegister() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Giả lập đăng ký thành công, trong thực tế sẽ call API
        // Sau khi đăng ký thành công, tự động đăng nhập
        String mockToken = "mock_token_" + System.currentTimeMillis();

        // Lưu thông tin đăng nhập
        auth.login(email, mockToken);

        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
        startMainActivity();
    }

    private void toggleAuthMode() {
        isLoginMode = !isLoginMode;

        if (isLoginMode) {
            titleText.setText("Login");
            btnPrimaryAction.setText("Login");
            toggleAuthMode.setText("Don't have an account? Register");
            edtConfirmPassword.setVisibility(View.GONE);
        } else {
            titleText.setText("Register");
            btnPrimaryAction.setText("Register");
            toggleAuthMode.setText("Already have an account? Login");
            edtConfirmPassword.setVisibility(View.VISIBLE);
        }
    }

    private void startMainActivity() {
        // Chuyển đến màn hình chính của ứng dụng
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}