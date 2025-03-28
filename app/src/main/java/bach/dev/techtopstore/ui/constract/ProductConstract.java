package bach.dev.techtopstore.ui.constract;

import bach.dev.techtopstore.data.dto.OrderItemDto;
import bach.dev.techtopstore.data.dto.ProductDto;
import bach.dev.techtopstore.data.model.ProductModel;

public interface ProductConstract {
    interface View {
        void showLoading();
        void hideLoading();
        void showError(String message);
        void showProduct(ProductDto product);
        void setFavourite(boolean isFavourite);
    }

    interface Presenter {
        void setView(View view);
        void getProduct(int productId);
        void setFavourite(ProductModel product);
        void checkFavourite(int productId);
        void addToCart(OrderItemDto orderItemDto);
        void getOrderByStatus(int userId, String status, OrderItemDto orderItemDto);
    }
}
