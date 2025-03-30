package bach.dev.techtopstore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import bach.dev.techtopstore.ui.constract.LoginConstract;
import bach.dev.techtopstore.ui.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginConstract.View {
    private LoginConstract.Presenter mPresenter;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        
        initGUI();
        initData();
    }

    private void initData() {
        mPresenter = new LoginPresenter();
        mPresenter.setView(this);
    }

    private void initGUI() {
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_primary_action);
        btnLogin.setOnClickListener(v -> {
            mPresenter.login(edtEmail.getText().toString(), edtPassword.getText().toString());
        });
    }

    @Override
    public void showErrorMessage(String message) {
        
    }

    @Override
    public void showSuccessMessage(String message) {

    }
}