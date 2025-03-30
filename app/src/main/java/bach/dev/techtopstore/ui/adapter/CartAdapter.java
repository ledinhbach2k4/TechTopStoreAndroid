package bach.dev.techtopstore.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bach.dev.techtopstore.R;
import bach.dev.techtopstore.data.dto.OrderItemDto;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<OrderItemDto> cartItems;
    private OnCartItemChangeListener listener;

    public interface OnCartItemChangeListener {
        void onItemQuantityChanged(int itemId, int newQuantity);
        void onItemRemoved(int itemId);
    }

    public CartAdapter(List<OrderItemDto> cartItems, OnCartItemChangeListener listener) {
        this.cartItems = cartItems != null ? cartItems : new ArrayList<>();
        this.listener = listener;
    }

    public void updateCartItems(List<OrderItemDto> newItems) {
        this.cartItems.clear();
        this.cartItems.addAll(newItems != null ? newItems : new ArrayList<>());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItemDto item = cartItems.get(position);
        holder.tvName.setText(item.getProductName());
        holder.tvPrice.setText(String.format("$%.2f", item.getPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        Picasso.get().load(item.getThumbnail()).into(holder.ivThumbnail);

        holder.btnIncrease.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() + 1;
            holder.tvQuantity.setText(String.valueOf(newQuantity));
            if (listener != null) {
                listener.onItemQuantityChanged(item.getId(), newQuantity);
            }
        });

        holder.btnDecrease.setOnClickListener(v -> {
            int newQuantity = Math.max(1, item.getQuantity() - 1);
            holder.tvQuantity.setText(String.valueOf(newQuantity));
            if (listener != null) {
                listener.onItemQuantityChanged(item.getId(), newQuantity);
            }
        });

        holder.btnRemove.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemRemoved(item.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivThumbnail;
        public TextView tvName;
        public TextView tvPrice;
        public TextView tvQuantity;
        public Button btnIncrease;
        public Button btnDecrease;
        public Button btnRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.iv_cart_thumbnail);
            tvName = itemView.findViewById(R.id.tv_cart_name);
            tvPrice = itemView.findViewById(R.id.tv_cart_price);
            tvQuantity = itemView.findViewById(R.id.tv_cart_quantity);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            btnRemove = itemView.findViewById(R.id.btn_remove);
        }
    }
}