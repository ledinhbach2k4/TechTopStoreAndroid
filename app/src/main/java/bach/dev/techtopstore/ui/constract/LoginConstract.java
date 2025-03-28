package bach.dev.techtopstore.ui.constract;

public interface LoginConstract {
    interface Presenter {
        void setView(View view);
        void login(String email, String password);
    }

    interface View {
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }
}
