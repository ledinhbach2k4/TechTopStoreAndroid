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
import bach.dev.techtopstore.data.dto.CategoryDto;
import bach.dev.techtopstore.util.CircleTransform;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoryDto> categoryList;
    private OnCategoryClickListener clickListener;

    public interface OnCategoryClickListener {
        void onCategoryClick(CategoryDto category);
    }

    public CategoryAdapter(List<CategoryDto> categoryList, OnCategoryClickListener clickListener) {
        this.categoryList = categoryList != null ? categoryList : new ArrayList<>();
        this.clickListener = clickListener;
    }

    // Phương thức cập nhật danh sách danh mục
    public void updateCategories(List<CategoryDto> newCategories) {
        this.categoryList.clear();
        this.categoryList.addAll(newCategories != null ? newCategories : new ArrayList<>());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryDto category = categoryList.get(position);
        holder.tvName.setText(category.getName());
        Picasso.get()
                .load(category.getThumbnail())
                .transform(new CircleTransform())
                .into(holder.ivThumbnail);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivThumbnail;
        private TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.iv_category_thumbnail);
            tvName = itemView.findViewById(R.id.tv_category_name);
        }
    }
}