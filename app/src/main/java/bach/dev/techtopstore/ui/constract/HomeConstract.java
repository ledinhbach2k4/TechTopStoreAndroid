package bach.dev.techtopstore.ui.constract;

import java.util.List;

import bach.dev.techtopstore.data.dto.CategoryDto;
import bach.dev.techtopstore.data.dto.ProductDto;

public interface HomeConstract {
    interface View {
        void showLoading();
        void hideLoading();
        void showError(String message);
        void showProducts(List<ProductDto> productList);
        void showCategories(List<CategoryDto> categories);
    }

    interface Presenter {
        void setView(View view);
        void getProducts();
        void getProductsByProperty(String property, String order);
        void getCategories();
        void getProductsByCategory(int categoryId);
        void searchProduct(String keyword);
        void onProductClicked(int productId); // Thêm để xử lý click sản phẩm
    }
}