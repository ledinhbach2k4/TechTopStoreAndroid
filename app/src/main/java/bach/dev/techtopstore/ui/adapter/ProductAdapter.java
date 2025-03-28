package bach.dev.techtopstore.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bach.dev.techtopstore.R;
import bach.dev.techtopstore.data.dto.ProductDto;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductDto> productList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int productId);
    }

    public ProductAdapter(List<ProductDto> productList, OnItemClickListener listener) {
        this.productList = productList != null ? productList : new ArrayList<>();
        this.onItemClickListener = listener;
    }

    // Thêm phương thức cập nhật danh sách sản phẩm
    public void updateProducts(List<ProductDto> newProducts) {
        this.productList.clear();
        this.productList.addAll(newProducts != null ? newProducts : new ArrayList<>());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDto product = productList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(String.format("$%.2f", product.getPrice()));
        Picasso.get().load(product.getThumbnail()).into(holder.ivThumbnail);

        holder.tvRating.setText(String.valueOf(product.getRating()));
        updateRatingStars(holder.ratingStars, product.getRating());

        holder.tvSoldCount.setText("Sold: " + product.getSoldCount());

        holder.ivThumbnail.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(product.getId());
            }
        });
    }

    private void updateRatingStars(List<ImageView> stars, float rating) {
        int fullStars = (int) rating;
        boolean hasHalfStar = rating - fullStars >= 0.5;

        for (int i = 0; i < 5; i++) {
            if (i < fullStars) {
                stars.get(i).setImageResource(R.drawable.ic_star);
            } else if (i == fullStars && hasHalfStar) {
                stars.get(i).setImageResource(R.drawable.ic_star_half);
            } else {
                stars.get(i).setImageResource(R.drawable.ic_star_empty);
            }
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivThumbnail;
        public TextView tvName;
        public TextView tvPrice;
        public TextView tvRating;
        public TextView tvSoldCount;
        public List<ImageView> ratingStars;

        public ViewHolder(View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.iv_product_item_thumbnail);
            tvName = itemView.findViewById(R.id.tv_product_item_name);
            tvPrice = itemView.findViewById(R.id.tv_product_item_price);
            tvRating = itemView.findViewById(R.id.tv_product_rating);
            tvSoldCount = itemView.findViewById(R.id.tv_product_sold_count);

            ratingStars = List.of(
                    itemView.findViewById(R.id.star1),
                    itemView.findViewById(R.id.star2),
                    itemView.findViewById(R.id.star3),
                    itemView.findViewById(R.id.star4),
                    itemView.findViewById(R.id.star5)
            );
        }
    }
}