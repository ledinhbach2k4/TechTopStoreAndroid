package bach.dev.techtopstore.ui.constract;

import bach.dev.techtopstore.data.dto.UserDto;

public interface LoginConstract {
    interface View {
        void showLoading();              // Hiển thị giao diện loading
        void hideLoading();              // Ẩn giao diện loading
        void showLoginSuccess(UserDto user); // Hiển thị khi đăng nhập thành công
        void showError(String message);      // Hiển thị thông báo lỗi
    }

    interface Presenter {
        void setView(View view);
        void login(String email, String password);
    }
}
