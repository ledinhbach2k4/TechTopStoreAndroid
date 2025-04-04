package bach.dev.techtopstore.ui.presenter;

import android.content.Context;
import android.util.Log;

import bach.dev.techtopstore.data.api.ApiService;
import bach.dev.techtopstore.data.api.RetrofitClient;
import bach.dev.techtopstore.data.dto.UserDto;
import bach.dev.techtopstore.ui.constract.LoginConstract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginConstract.Presenter {
    private final ApiService apiService = RetrofitClient.getApiService();
    private LoginConstract.View mView;
    private final Context context; // Thêm Context để nhận từ LoginActivity

    public LoginPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void setView(LoginConstract.View view) {
        this.mView = view;
    }

    @Override
    public void login(String email, String password) {
        if (mView == null) return;
        mView.showLoading();

        Call<UserDto> call = apiService.login(email, password);
        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                mView.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("LoginPresenter", "Login success: " + response.body().toString());
                    mView.showLoginSuccess(response.body());
                } else {
                    String errorMsg = "Login failed: " + (response != null ? response.message() : "No response");
                    Log.e("LoginPresenter", errorMsg);
                    mView.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                mView.hideLoading();
                String errorMsg = "Login error: " + t.getMessage();
                Log.e("LoginPresenter", errorMsg);
                mView.showError(errorMsg);
            }
        });
    }
}