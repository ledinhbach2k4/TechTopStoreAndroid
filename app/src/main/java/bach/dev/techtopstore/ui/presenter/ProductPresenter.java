package bach.dev.techtopstore.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bach.dev.techtopstore.LoginActivity;
import bach.dev.techtopstore.auth.Auth;
import bach.dev.techtopstore.data.api.ApiService;
import bach.dev.techtopstore.data.api.RetrofitClient;
import bach.dev.techtopstore.data.dao.ProductDao;
import bach.dev.techtopstore.data.dto.OrderDto;
import bach.dev.techtopstore.data.dto.OrderItemDto;
import bach.dev.techtopstore.data.dto.ProductDto;
import bach.dev.techtopstore.data.dto.UserDto;
import bach.dev.techtopstore.data.model.ProductModel;
import bach.dev.techtopstore.data.room.RoomHelper;
import bach.dev.techtopstore.ui.constract.ProductConstract;
import retrofit2.Call;

public class ProductPresenter implements ProductConstract.Presenter {
    private ProductConstract.View view;
    private ApiService apiService = RetrofitClient.getApiService();
    private Context context;

    public ProductPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void setView(ProductConstract.View view) {
        this.view = view;
    }

    @Override
    public void getProduct(int productId) {
        Log.d("ProductPresenter", "Requested Product ID: " + productId); // Log productId
        Call<ProductDto> call = apiService.getProduct(productId);
        call.enqueue(new retrofit2.Callback<ProductDto>() {
            @Override
            public void onResponse(Call<ProductDto> call, retrofit2.Response<ProductDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductDto product = response.body();
                    Log.d("ProductPresenter", "API Response - Thumbnail Extra: " +
                            (product.getThumbnailExtra() != null ? product.getThumbnailExtra().toString() : "null"));
                    view.showProduct(product);
                } else {
                    String errorMsg = "Error: " + (response != null ? response.message() : "No response");
                    Log.e("ProductPresenter", errorMsg);
                    view.showError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<ProductDto> call, Throwable t) {
                String errorMsg = "Error: " + t.getMessage();
                Log.e("ProductPresenter", errorMsg);
                view.showError(errorMsg);
            }
        });
    }

    @Override
    public void setFavourite(ProductModel product) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            ProductDao productDao = RoomHelper.getDatabase(context).productDao();
            ProductModel find = productDao.findById(product.id);
            if (find != null) {
                productDao.delete(find);
            } else {
                productDao.insert(product);
            }

            handler.post(() -> checkFavourite(product.id));
        });
    }

    @Override
    public void checkFavourite(int productId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            ProductDao productDao = RoomHelper.getDatabase(context).productDao();
            ProductModel product = productDao.findById(productId);

            handler.post(() -> view.setFavourite(product != null));
        });
    }

    @Override
    public void addToCart(OrderItemDto orderItemDto) {
        Auth auth = new Auth(context);
        UserDto userDto = new UserDto(auth.getUserEmail(), "");
        Call<OrderItemDto> call = apiService.addToCart(orderItemDto);
        call.enqueue(new retrofit2.Callback<OrderItemDto>() {
            @Override
            public void onResponse(Call<OrderItemDto> call, retrofit2.Response<OrderItemDto> response) {
                if (response.isSuccessful()) {
                    Log.i("ProductPresenter", "Add to cart success: " + response.body().toString());
                    view.showError("Thêm vào giỏ hàng thành công"); // Có thể thay bằng showSuccess
                } else {
                    view.showError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<OrderItemDto> call, Throwable t) {
                view.showError("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void getOrderByStatus(int userId, String status, OrderItemDto orderItemDto) {
        Call<OrderDto> call = apiService.getOrderByStatus(userId, status);
        call.enqueue(new retrofit2.Callback<OrderDto>() {
            @Override
            public void onResponse(Call<OrderDto> call, retrofit2.Response<OrderDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("ProductPresenter", "Order by status: " + response.body().toString());
                    OrderDto orderDto = response.body();
                    orderItemDto.setOrderId(orderDto.getId());
                    addToCart(orderItemDto);
                } else {
                    view.showError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<OrderDto> call, Throwable t) {
                view.showError("Error: " + t.getMessage());
            }
        });
    }

    private void redirectToLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}