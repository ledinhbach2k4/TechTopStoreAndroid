package bach.dev.techtopstore.ui.presenter;

import android.util.Log;

import bach.dev.techtopstore.data.api.ApiService;
import bach.dev.techtopstore.data.api.RetrofitClient;
import bach.dev.techtopstore.data.dto.UserDto;
import bach.dev.techtopstore.ui.constract.LoginConstract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginConstract.Presenter {
    ApiService apiService = RetrofitClient.getApiService();
    private LoginConstract.View mView;
    @Override
    public void setView(LoginConstract.View view) {
        mView = view;
    }

    @Override
    public void login(String email, String password) {
        UserDto userDto = new UserDto(email, password);
        Call<UserDto> call = apiService.login(userDto);
        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                Log.i("UserDto", response.body().toString());
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {

            }
        });
    }
}
