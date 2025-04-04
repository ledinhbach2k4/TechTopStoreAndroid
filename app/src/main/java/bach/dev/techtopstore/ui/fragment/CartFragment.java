package bach.dev.techtopstore.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bach.dev.techtopstore.R;
import bach.dev.techtopstore.data.dto.OrderItemDto;
import bach.dev.techtopstore.ui.adapter.CartAdapter;
import bach.dev.techtopstore.ui.constract.CartConstract;
import bach.dev.techtopstore.ui.presenter.CartPresenter;

public class CartFragment extends Fragment implements CartConstract.View, CartAdapter.CartItemListener {
    private RecyclerView rvCartItems;
    private CartAdapter cartAdapter;
    private CartConstract.Presenter mPresenter;
    private int orderId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getArguments() != null ? getArguments().getInt("orderId", 0) : 0;
        Log.i("CartFragment", "OrderId received: " + orderId);
        if (orderId == 0) {
            Toast.makeText(requireContext(), "Không có đơn hàng để hiển thị", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter = new CartPresenter(requireContext(), orderId);
        mPresenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        rvCartItems = view.findViewById(R.id.rv_cart_items);
        rvCartItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        if (orderId != 0) {
            mPresenter.getCartItems(orderId);
        }
        return view;
    }

    @Override
    public void showCartItems(List<OrderItemDto> items) {
        Log.i("CartFragment", "Showing cart items: " + items.size());
        cartAdapter = new CartAdapter(items, this);
        rvCartItems.setAdapter(cartAdapter);
    }

    @Override
    public void showSuccess(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemQuantityChanged(int itemId, int newQuantity) {
        mPresenter.updateCartItem(itemId, newQuantity);
    }

    @Override
    public void onItemRemoved(int itemId) {
        mPresenter.removeCartItem(itemId);
    }
}