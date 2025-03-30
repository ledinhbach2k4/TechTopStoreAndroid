package bach.dev.techtopstore.ui.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import bach.dev.techtopstore.data.api.ApiService;
import bach.dev.techtopstore.data.api.RetrofitClient;
import bach.dev.techtopstore.data.dto.OrderItemDto;
import bach.dev.techtopstore.ui.constract.CartConstract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter implements CartConstract.Presenter {
    private static final String TAG = "CartPresenter";
    private CartConstract.View mView;
    private final ApiService apiService = RetrofitClient.getApiService();
    private final Context context;

    public CartPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void setView(CartConstract.View view) {
        this.mView = view;
    }

    @Override
    public void getCartItems() {
        if (mView == null) return;
        mView.showLoading();
        Call<List<OrderItemDto>> call = apiService.getCartItems();
        call.enqueue(new Callback<List<OrderItemDto>>() {
            @Override
            public void onResponse(Call<List<OrderItemDto>> call, Response<List<OrderItemDto>> response) {
                if (mView == null) return;
                mView.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "getCartItems success: " + response.body().size() + " items");
                    mView.showCartItems(response.body());
                } else {
                    String errorMsg = "getCartItems failed: " + (response != null ? response.message() : "No response");
                    Log.e(TAG, errorMsg);
                    mView.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<List<OrderItemDto>> call, Throwable t) {
                if (mView == null) return;
                mView.hideLoading();
                String errorMsg = "getCartItems error: " + t.getMessage();
                Log.e(TAG, errorMsg);
                mView.showError(errorMsg);
            }
        });
    }

    @Override
    public void updateCartItem(int itemId, int newQuantity) {
        if (mView == null) return;
        mView.showLoading();
        Call<OrderItemDto> call = apiService.updateCartItem(itemId, newQuantity);
        call.enqueue(new Callback<OrderItemDto>() {
            @Override
            public void onResponse(Call<OrderItemDto> call, Response<OrderItemDto> response) {
                if (mView == null) return;
                mView.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "updateCartItem success: itemId=" + itemId + ", quantity=" + newQuantity);
                    getCartItems(); // Làm mới danh sách sau khi cập nhật
                } else {
                    String errorMsg = "updateCartItem failed: " + (response != null ? response.message() : "No response");
                    Log.e(TAG, errorMsg);
                    mView.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<OrderItemDto> call, Throwable t) {
                if (mView == null) return;
                mView.hideLoading();
                String errorMsg = "updateCartItem error: " + t.getMessage();
                Log.e(TAG, errorMsg);
                mView.showError(errorMsg);
            }
        });
    }

    @Override
    public void removeCartItem(int itemId) {
        if (mView == null) return;
        mView.showLoading();
        Call<Void> call = apiService.removeCartItem(itemId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (mView == null) return;
                mView.hideLoading();
                if (response.isSuccessful()) {
                    Log.d(TAG, "removeCartItem success: itemId=" + itemId);
                    getCartItems(); // Làm mới danh sách sau khi xóa
                } else {
                    String errorMsg = "removeCartItem failed: " + (response != null ? response.message() : "No response");
                    Log.e(TAG, errorMsg);
                    mView.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (mView == null) return;
                mView.hideLoading();
                String errorMsg = "removeCartItem error: " + t.getMessage();
                Log.e(TAG, errorMsg);
                mView.showError(errorMsg);
            }
        });
    }

    @Override
    public void checkout() {
        if (mView == null) return;
        mView.showLoading();
        Call<Void> call = apiService.checkout();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (mView == null) return;
                mView.hideLoading();
                if (response.isSuccessful()) {
                    Log.d(TAG, "checkout success");
                    mView.showCheckoutSuccess();
                } else {
                    String errorMsg = "checkout failed: " + (response != null ? response.message() : "No response");
                    Log.e(TAG, errorMsg);
                    mView.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (mView == null) return;
                mView.hideLoading();
                String errorMsg = "checkout error: " + t.getMessage();
                Log.e(TAG, errorMsg);
                mView.showError(errorMsg);
            }
        });
    }
}