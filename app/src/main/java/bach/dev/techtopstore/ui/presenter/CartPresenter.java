package bach.dev.techtopstore.ui.presenter;

import android.content.Context;

import bach.dev.techtopstore.data.api.ApiService;
import bach.dev.techtopstore.data.api.RetrofitClient;
import bach.dev.techtopstore.data.dto.OrderDto;
import bach.dev.techtopstore.data.dto.OrderItemDto;
import bach.dev.techtopstore.ui.constract.CartConstract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter implements CartConstract.Presenter {
    private final ApiService apiService = RetrofitClient.getApiService();
    private CartConstract.View mView;
    private final Context context;
    private final int orderId; // Thêm orderId

    public CartPresenter(Context context, int orderId) {
        this.context = context;
        this.orderId = orderId;
    }

    @Override
    public void setView(CartConstract.View view) {
        this.mView = view;
    }

    @Override
    public void getCartItems(int orderId) {
        Call<java.util.List<OrderItemDto>> call = apiService.getCartItems(orderId);
        call.enqueue(new Callback<java.util.List<OrderItemDto>>() {
            @Override
            public void onResponse(Call<java.util.List<OrderItemDto>> call, Response<java.util.List<OrderItemDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mView.showCartItems(response.body());
                } else {
                    mView.showError("Không tải được giỏ hàng");
                }
            }

            @Override
            public void onFailure(Call<java.util.List<OrderItemDto>> call, Throwable t) {
                mView.showError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    @Override
    public void updateCartItem(int itemId, int newQuantity) {
        OrderItemDto orderItemUpdate = new OrderItemDto(); // Constructor mặc định
        orderItemUpdate.setId(itemId);
        orderItemUpdate.setQuantity(newQuantity);

        Call<OrderItemDto> call = apiService.updateCartItem(itemId, orderItemUpdate);
        call.enqueue(new Callback<OrderItemDto>() {
            @Override
            public void onResponse(Call<OrderItemDto> call, Response<OrderItemDto> response) {
                if (response.isSuccessful()) {
                    mView.showSuccess("Cập nhật số lượng thành công");
                } else {
                    mView.showError("Không thể cập nhật số lượng");
                }
            }

            @Override
            public void onFailure(Call<OrderItemDto> call, Throwable t) {
                mView.showError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    @Override
    public void removeCartItem(int itemId) {
        Call<Void> call = apiService.removeCartItem(itemId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mView.showSuccess("Đã xóa sản phẩm khỏi giỏ hàng");
                } else {
                    mView.showError("Không thể xóa sản phẩm");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mView.showError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    @Override
    public void checkout() {
        Call<OrderDto> call = apiService.checkout(orderId, null); // Giả định null cho OrderDto
        call.enqueue(new Callback<OrderDto>() {
            @Override
            public void onResponse(Call<OrderDto> call, Response<OrderDto> response) {
                if (response.isSuccessful()) {
                    mView.showSuccess("Thanh toán thành công");
                } else {
                    mView.showError("Không thể thanh toán");
                }
            }

            @Override
            public void onFailure(Call<OrderDto> call, Throwable t) {
                mView.showError("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}