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

import java.util.List;

import bach.dev.techtopstore.R;
import bach.dev.techtopstore.data.dto.OrderItemDto;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<OrderItemDto> items;
    private CartItemListener listener;

    public CartAdapter(List<OrderItemDto> items, CartItemListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItemDto item = items.get(position);

        holder.tvName.setText(item.getProductName());
        holder.tvPrice.setText(String.format("$%.2f", item.getPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        Picasso.get().load(item.getThumbnail()).into(holder.ivThumbnail);

        // Thay tất cả item.getId() thành item.getProductId()
        holder.btnIncrease.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() + 1;
            holder.tvQuantity.setText(String.valueOf(newQuantity));
            listener.onItemQuantityChanged(item.getProductId(), newQuantity); // Sửa thành getProductId()
        });

        holder.btnDecrease.setOnClickListener(v -> {
            int newQuantity = Math.max(1, item.getQuantity() - 1);
            holder.tvQuantity.setText(String.valueOf(newQuantity));
            listener.onItemQuantityChanged(item.getProductId(), newQuantity); // Sửa thành getProductId()
        });

        holder.btnRemove.setOnClickListener(v -> {
            listener.onItemRemoved(item.getProductId()); // Sửa thành getProductId()
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvName;
        TextView tvPrice;
        TextView tvQuantity;
        Button btnIncrease;
        Button btnDecrease;
        Button btnRemove;

        public ViewHolder(@NonNull View itemView) {
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

    public interface CartItemListener {
        void onItemQuantityChanged(int itemId, int newQuantity);
        void onItemRemoved(int itemId);
    }
}