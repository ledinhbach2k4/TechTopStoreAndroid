package bach.dev.techtopstore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import bach.dev.techtopstore.data.dto.UserDto;
import bach.dev.techtopstore.ui.constract.LoginConstract;
import bach.dev.techtopstore.ui.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginConstract.View {
    private LoginPresenter mPresenter;
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo UI
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        // Khởi tạo Presenter
        mPresenter = new LoginPresenter(this);
        mPresenter.setView(this);

        // Xử lý sự kiện nút đăng nhập
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            mPresenter.login(email, password);
        });
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        loginButton.setEnabled(true);
    }

    @Override
    public void showLoginSuccess(UserDto user) {
        Toast.makeText(this, "Đăng nhập thành công: " + user.getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}