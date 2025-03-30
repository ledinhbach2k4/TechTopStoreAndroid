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
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPresenter implements ProductConstract.Presenter {
    private ProductConstract.View view;
    private ApiService apiService = RetrofitClient.getApiService();
    private Context context;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    public ProductPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void setView(ProductConstract.View view) {
        this.view = view;
    }

    @Override
    public void getProduct(int productId) {
        Log.d("ProductPresenter", "Requested Product ID: " + productId);
        Call<ProductDto> call = apiService.getProduct(productId);
        call.enqueue(new Callback<ProductDto>() {
            @Override
            public void onResponse(Call<ProductDto> call, Response<ProductDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductDto product = response.body();
                    Log.d("ProductPresenter", "API Response - Thumbnail Extra: " +
                            (product.getThumbnailExtra() != null ? product.getThumbnailExtra().toString() : "null"));
                    view.showProduct(product);
                } else {
                    Log.e("ProductPresenter", "Error: " + response.message());
                    view.showError("Lỗi tải sản phẩm: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductDto> call, Throwable t) {
                Log.e("ProductPresenter", "Error: " + t.getMessage());
                view.showError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    @Override
    public void setFavourite(ProductModel product, boolean isFavourite) {
        executor.execute(() -> {
            ProductDao productDao = RoomHelper.getDatabase(context).productDao();

            if (isFavourite) {
                productDao.insert(product);
            } else {
                productDao.delete(product);
            }

            handler.post(() -> view.setFavourite(isFavourite));
        });
    }


    @Override
    public void checkFavourite(int productId) {
        executor.execute(() -> {
            ProductDao productDao = RoomHelper.getDatabase(context).productDao();
            ProductModel product = productDao.findById(productId);

            boolean isFavourite = (product != null);
            handler.post(() -> view.setFavourite(isFavourite));
        });
    }

    @Override
    public void addToCart(OrderItemDto orderItemDto) {
        Auth auth = new Auth(context);
        UserDto userDto = new UserDto(auth.getUserEmail(), "");
        Call<OrderItemDto> call = apiService.addToCart(orderItemDto);
        call.enqueue(new Callback<OrderItemDto>() {
            @Override
            public void onResponse(Call<OrderItemDto> call, Response<OrderItemDto> response) {
                if (response.isSuccessful()) {
                    Log.i("ProductPresenter", "Add to cart success: " + response.body());
                    view.showSuccess("Thêm vào giỏ hàng thành công"); // Cần định nghĩa showSuccess trong View
                } else {
                    view.showError("Lỗi khi thêm vào giỏ hàng: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<OrderItemDto> call, Throwable t) {
                view.showError("Lỗi kết nối khi thêm vào giỏ hàng: " + t.getMessage());
            }
        });
    }

    @Override
    public void getOrderByStatus(int userId, String status, OrderItemDto orderItemDto) {
        Call<OrderDto> call = apiService.getOrderByStatus(userId, status);
        call.enqueue(new Callback<OrderDto>() {
            @Override
            public void onResponse(Call<OrderDto> call, Response<OrderDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("ProductPresenter", "Order by status: " + response.body());
                    OrderDto orderDto = response.body();
                    orderItemDto.setOrderId(orderDto.getId());
                    addToCart(orderItemDto);
                } else {
                    view.showError("Lỗi khi lấy đơn hàng: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<OrderDto> call, Throwable t) {
                view.showError("Lỗi kết nối khi lấy đơn hàng: " + t.getMessage());
            }
        });
    }

    private void redirectToLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
