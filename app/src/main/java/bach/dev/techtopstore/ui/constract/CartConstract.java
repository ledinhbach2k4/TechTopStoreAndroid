package bach.dev.techtopstore.ui.constract;

import java.util.List;

import bach.dev.techtopstore.data.dto.OrderItemDto;

public interface CartConstract {
    interface View {
        void showCartItems(List<OrderItemDto> items);
        void showSuccess(String message);
        void showError(String message);
    }

    interface Presenter {
        void setView(View view);
        void getCartItems(int orderId);
        void updateCartItem(int itemId, int newQuantity);
        void removeCartItem(int itemId);
        void checkout(); // Thêm phương thức checkout
    }
}