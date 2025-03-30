package bach.dev.techtopstore.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bach.dev.techtopstore.R;
import bach.dev.techtopstore.data.dto.OrderItemDto;
import bach.dev.techtopstore.ui.adapter.CartAdapter;
import bach.dev.techtopstore.ui.constract.CartConstract;
import bach.dev.techtopstore.ui.presenter.CartPresenter;

public class CartFragment extends Fragment implements CartConstract.View, CartAdapter.OnCartItemChangeListener {
    private RecyclerView rvCartItems;
    private ProgressBar progressBar;
    private TextView tvTotalPrice;
    private Button btnCheckout;
    private CartConstract.Presenter mPresenter;
    private CartAdapter cartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        initGUI(rootView);
        initData();
        return rootView;
    }

    private void initGUI(View rootView) {
        progressBar = rootView.findViewById(R.id.progressBar);
        tvTotalPrice = rootView.findViewById(R.id.tv_total_price);
        btnCheckout = rootView.findViewById(R.id.btn_checkout);
        btnCheckout.setOnClickListener(v -> {
            if (mPresenter != null) {
                mPresenter.checkout();
            }
        });

        rvCartItems = rootView.findViewById(R.id.rv_cart_items);
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        // Khởi tạo adapter một lần
        cartAdapter = new CartAdapter(new ArrayList<>(), this);
        rvCartItems.setAdapter(cartAdapter);
    }

    private void initData() {
        mPresenter = new CartPresenter(getContext());
        mPresenter.setView(this);
        mPresenter.getCartItems();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCartItems(List<OrderItemDto> cartItems) {
        cartAdapter.updateCartItems(cartItems);
        updateTotalPrice(cartItems);
    }

    @Override
    public void showCheckoutSuccess() {
        Toast.makeText(getContext(), "Checkout successful!", Toast.LENGTH_SHORT).show();
        cartAdapter.updateCartItems(new ArrayList<>()); // Xóa giỏ hàng sau khi thanh toán
        updateTotalPrice(new ArrayList<>());
    }

    @Override
    public void onItemQuantityChanged(int itemId, int newQuantity) {
        if (mPresenter != null) {
            mPresenter.updateCartItem(itemId, newQuantity);
        }
    }

    @Override
    public void onItemRemoved(int itemId) {
        if (mPresenter != null) {
            mPresenter.removeCartItem(itemId);
        }
    }

    private void updateTotalPrice(List<OrderItemDto> cartItems) {
        double total = 0;
        for (OrderItemDto item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        tvTotalPrice.setText(String.format("Total: $%.2f", total));
    }
}