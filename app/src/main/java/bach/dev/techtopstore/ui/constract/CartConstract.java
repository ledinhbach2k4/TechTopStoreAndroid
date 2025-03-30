package bach.dev.techtopstore.ui.constract;

import java.util.List;

import bach.dev.techtopstore.data.dto.OrderItemDto;

public interface CartConstract {
    interface View {
        void showLoading();
        void hideLoading();
        void showError(String message);
        void showCartItems(List<OrderItemDto> cartItems);
        void showCheckoutSuccess();
    }

    interface Presenter {
        void setView(View view);
        void getCartItems();
        void updateCartItem(int itemId, int newQuantity);
        void removeCartItem(int itemId);
        void checkout();
    }
}