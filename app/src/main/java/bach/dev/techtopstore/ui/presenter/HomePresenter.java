package bach.dev.techtopstore.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import bach.dev.techtopstore.ProductActivity;
import bach.dev.techtopstore.data.api.ApiService;
import bach.dev.techtopstore.data.api.RetrofitClient;
import bach.dev.techtopstore.data.dto.CategoryDto;
import bach.dev.techtopstore.data.dto.ProductDto;
import bach.dev.techtopstore.ui.constract.HomeConstract;
import bach.dev.techtopstore.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeConstract.Presenter {
    private static final String TAG = "HomePresenter";
    private HomeConstract.View mView;
    private final ApiService apiService = RetrofitClient.getApiService();
    private final Context context; // Thêm context để điều hướng

    public HomePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void setView(HomeConstract.View view) {
        this.mView = view;
    }

    @Override
    public void getProducts() {
        if (mView == null) return;
        mView.showLoading();
        Call<List<ProductDto>> call = apiService.getProducts();
        call.enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                if (mView == null) return;
                mView.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "getProducts success: " + response.body().size() + " items");
                    mView.showProducts(response.body());
                } else {
                    String errorMsg = "getProducts failed: " + (response != null ? response.message() : "No response");
                    Log.e(TAG, errorMsg);
                    mView.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable t) {
                if (mView == null) return;
                mView.hideLoading();
                String errorMsg = "getProducts error: " + t.getMessage();
                Log.e(TAG, errorMsg);
                mView.showError(errorMsg);
            }
        });
    }

    @Override
    public void getProductsByProperty(String property, String order) {
        if (mView == null) return;
        mView.showLoading();
        Call<List<ProductDto>> call = apiService.getProductsByProperty(property, order);
        call.enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                if (mView == null) return;
                mView.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "getProductsByProperty success: " + response.body().size() + " items");
                    mView.showProducts(response.body());
                } else {
                    String errorMsg = "getProductsByProperty failed: " + (response != null ? response.message() : "No response");
                    Log.e(TAG, errorMsg);
                    mView.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable t) {
                if (mView == null) return;
                mView.hideLoading();
                String errorMsg = "getProductsByProperty error: " + t.getMessage();
                Log.e(TAG, errorMsg);
                mView.showError(errorMsg);
            }
        });
    }

    @Override
    public void getCategories() {
        if (mView == null) return;
        mView.showLoading(); // Thêm để nhất quán UX
        Call<List<CategoryDto>> call = apiService.getCategories();
        call.enqueue(new Callback<List<CategoryDto>>() {
            @Override
            public void onResponse(Call<List<CategoryDto>> call, Response<List<CategoryDto>> response) {
                if (mView == null) return;
                mView.hideLoading(); // Thêm để nhất quán UX
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "getCategories success: " + response.body().size() + " items");
                    mView.showCategories(response.body());
                } else {
                    String errorMsg = "getCategories failed: " + (response != null ? response.message() : "No response");
                    Log.e(TAG, errorMsg);
                    mView.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<List<CategoryDto>> call, Throwable t) {
                if (mView == null) return;
                mView.hideLoading(); // Thêm để nhất quán UX
                String errorMsg = "getCategories error: " + t.getMessage();
                Log.e(TAG, errorMsg);
                mView.showError(errorMsg);
            }
        });
    }

    @Override
    public void getProductsByCategory(int categoryId) {
        if (mView == null) return;
        mView.showLoading(); // Thêm để nhất quán UX
        Call<List<ProductDto>> call = apiService.getProductsByCategory(categoryId);
        call.enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                if (mView == null) return;
                mView.hideLoading(); // Thêm để nhất quán UX
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "getProductsByCategory success: " + response.body().size() + " items");
                    mView.showProducts(response.body());
                } else {
                    String errorMsg = "getProductsByCategory failed: " + (response != null ? response.message() : "No response");
                    Log.e(TAG, errorMsg);
                    mView.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable t) {
                if (mView == null) return;
                mView.hideLoading(); // Thêm để nhất quán UX
                String errorMsg = "getProductsByCategory error: " + t.getMessage();
                Log.e(TAG, errorMsg);
                mView.showError(errorMsg);
            }
        });
    }

    @Override
    public void searchProduct(String keyword) {
        if (mView == null) return;
        mView.showLoading();
        Call<List<ProductDto>> call = apiService.searchProducts(keyword);
        call.enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                if (mView == null) return;
                mView.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "searchProduct success: " + response.body().size() + " items");
                    mView.showProducts(response.body());
                } else {
                    String errorMsg = "searchProduct failed: " + (response != null ? response.message() : "No response");
                    Log.e(TAG, errorMsg);
                    mView.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable t) {
                if (mView == null) return;
                mView.hideLoading();
                String errorMsg = "searchProduct error: " + t.getMessage();
                Log.e(TAG, errorMsg);
                mView.showError(errorMsg);
            }
        });
    }

    @Override
    public void onProductClicked(int productId) {
        if (context == null) {
            Log.e(TAG, "Context is null, cannot navigate to ProductActivity");
            return;
        }
        Log.d(TAG, "Navigating to ProductActivity with productId: " + productId);
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra(Constants.PRODUCT_ID, productId);
        context.startActivity(intent);
    }
}