package bach.dev.techtopstore.ui.presenter;

import android.content.Context;
import android.util.Log;

import bach.dev.techtopstore.data.api.ApiService;
import bach.dev.techtopstore.data.api.RetrofitClient;
import bach.dev.techtopstore.data.dto.OrderDto;
import bach.dev.techtopstore.data.dto.OrderItemDto;
import bach.dev.techtopstore.data.dto.ProductDto;
import bach.dev.techtopstore.data.model.ProductModel;
import bach.dev.techtopstore.ui.constract.ProductConstract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPresenter implements ProductConstract.Presenter {
    private final ApiService apiService = RetrofitClient.getApiService();
    private ProductConstract.View mView;
    private final Context context;

    public ProductPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void setView(ProductConstract.View view) {
        this.mView = view;
    }

    @Override
    public void getProduct(int productId) {
        Call<ProductDto> call = apiService.getProduct(productId);
        call.enqueue(new Callback<ProductDto>() {
            @Override
            public void onResponse(Call<ProductDto> call, Response<ProductDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mView.showProduct(response.body());
                } else {
                    mView.showError("Không tải được sản phẩm");
                }
            }

            @Override
            public void onFailure(Call<ProductDto> call, Throwable t) {
                mView.showError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    @Override
    public void checkFavourite(int productId) {
        // Logic kiểm tra yêu thích (giả định)
    }

    @Override
    public void setFavourite(ProductModel product, boolean isFavourite) {
        // Logic cập nhật yêu thích (giả định)
    }

    @Override
    public void getOrderByStatus(int userId, String status, OrderItemDto orderItemDto) {
        Call<OrderDto> call = apiService.getOrderByStatus(userId, status);
        call.enqueue(new Callback<OrderDto>() {
            @Override
            public void onResponse(Call<OrderDto> call, Response<OrderDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("ProductPresenter", "Order found: " + response.body().toString());
                    addToCart(response.body().getId(), orderItemDto);
                } else {
                    createPendingOrder(userId, orderItemDto);
                }
            }

            @Override
            public void onFailure(Call<OrderDto> call, Throwable t) {
                mView.showError("Lỗi truy vấn đơn hàng: " + t.getMessage());
            }
        });
    }

    private void createPendingOrder(int userId, OrderItemDto orderItemDto) {
        OrderDto newOrder = new OrderDto(0, "ORDER_" + System.currentTimeMillis(), userId, "pending");
        Call<OrderDto> call = apiService.createOrder(newOrder);
        call.enqueue(new Callback<OrderDto>() {
            @Override
            public void onResponse(Call<OrderDto> call, Response<OrderDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addToCart(response.body().getId(), orderItemDto);
                } else {
                    mView.showError("Không thể tạo đơn hàng mới");
                }
            }

            @Override
            public void onFailure(Call<OrderDto> call, Throwable t) {
                mView.showError("Lỗi tạo đơn hàng: " + t.getMessage());
            }
        });
    }

    public void addToCart(int orderId, OrderItemDto orderItemDto) {
        Call<OrderItemDto> call = apiService.addToCart(
                orderItemDto.getProductId(),
                orderId,
                orderItemDto.getQuantity(),
                orderItemDto.getPrice()
        );
        call.enqueue(new Callback<OrderItemDto>() {
            @Override
            public void onResponse(Call<OrderItemDto> call, Response<OrderItemDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mView.showSuccess("Đã thêm vào giỏ hàng, Order ID: " + orderId);
                } else {
                    mView.showError("Không thể thêm vào giỏ hàng: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<OrderItemDto> call, Throwable t) {
                mView.showError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    @Override
    public void addToCart(OrderItemDto orderItemDto) {
        int userId = 1; // Thay bằng logic lấy userId thực tế (ví dụ: từ Auth)
        getOrderByStatus(userId, "pending", orderItemDto);
    }
}