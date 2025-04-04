package bach.dev.techtopstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

import bach.dev.techtopstore.data.dto.OrderItemDto;
import bach.dev.techtopstore.data.dto.ProductDto;
import bach.dev.techtopstore.data.model.ProductModel;
import bach.dev.techtopstore.ui.constract.ProductConstract;
import bach.dev.techtopstore.ui.presenter.ProductPresenter;
import bach.dev.techtopstore.util.Constants;

public class ProductActivity extends AppCompatActivity implements ProductConstract.View {
    private ImageView ivThumbnail;
    private LinearLayout llThumbnailExtraContainer;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvDescription;
    private TextView tvRating;
    private TextView tvSoldCount;
    private ImageView ivFavourite;
    private TextView tvAddToCart;
    private ImageView[] starViews;

    private ProductDto productDto;
    private ProductConstract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);

        initGUI();
        initData();
    }

    private void initData() {
        mPresenter = new ProductPresenter(this);
        mPresenter.setView(this);
        int productId = getIntent().getIntExtra(Constants.PRODUCT_ID, 0);
        Log.i("ProductActivity", "Received productId from Intent: " + productId);
        mPresenter.getProduct(productId);
    }

    @SuppressLint("WrongViewCast")
    private void initGUI() {
        ivThumbnail = findViewById(R.id.iv_product_thumbnail);
        llThumbnailExtraContainer = findViewById(R.id.ll_thumbnail_extra_container);
        tvName = findViewById(R.id.tv_product_name);
        tvPrice = findViewById(R.id.tv_product_price);
        tvDescription = findViewById(R.id.tv_product_description);
        tvRating = findViewById(R.id.tv_product_rating);
        tvSoldCount = findViewById(R.id.tv_product_sold_count);
        ivFavourite = findViewById(R.id.iv_favourite);
        ivFavourite.setOnClickListener(listener);
        tvAddToCart = findViewById(R.id.tv_add_to_cart);
        tvAddToCart.setOnClickListener(listener);

        starViews = new ImageView[] {
                findViewById(R.id.star1),
                findViewById(R.id.star2),
                findViewById(R.id.star3),
                findViewById(R.id.star4),
                findViewById(R.id.star5)
        };
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    @Override
    public void showSuccess(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            try {
                String[] parts = message.split("Order ID: ");
                if (parts.length > 1) {
                    int orderId = Integer.parseInt(parts[1].trim());
                    Log.i("ProductActivity", "Navigating to CartActivity with orderId: " + orderId);
                    Intent intent = new Intent(ProductActivity.this, CartActivity.class);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                } else {
                    Log.w("ProductActivity", "Message does not contain Order ID: " + message);
                }
            } catch (NumberFormatException e) {
                Log.e("ProductActivity", "Failed to parse orderId from message: " + message, e);
                Toast.makeText(this, "Lỗi khi chuyển sang giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showError(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void showProduct(ProductDto product) {
        productDto = product;
        Log.i("ProductActivity", "Product loaded - ID: " + product.getId() + ", Price: " + product.getPrice());
        tvName.setText(product.getName());
        tvPrice.setText(String.format("$%.2f", product.getPrice()));
        tvDescription.setText(product.getDescription());
        tvSoldCount.setText("Sold: " + product.getSoldCount());
        Picasso.get().load(product.getThumbnail()).into(ivThumbnail);

        float rating = product.getRating();
        tvRating.setText(String.valueOf(rating));
        updateStarRating(rating);

        displayExtraThumbnails(product.getThumbnailExtra());
        mPresenter.checkFavourite(product.getId());
    }

    

    private void updateStarRating(float rating) {
        for (int i = 0; i < starViews.length; i++) {
            if (i < Math.floor(rating)) {
                starViews[i].setImageResource(R.drawable.ic_star);
            } else if (i < rating) {
                starViews[i].setImageResource(R.drawable.ic_star_half);
            } else {
                starViews[i].setImageResource(R.drawable.ic_star_empty);
            }
        }
    }

    private void displayExtraThumbnails(List<String> extraThumbnails) {
        Log.i("ProductActivity", "Extra thumbnails: " + (extraThumbnails != null ? extraThumbnails.toString() : "null"));
        if (extraThumbnails != null && !extraThumbnails.isEmpty()) {
            llThumbnailExtraContainer.removeAllViews();
            for (String url : extraThumbnails) {
                Log.i("ProductActivity", "Loading extra thumbnail: " + url);
                ImageView extraImage = new ImageView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
                params.setMargins(8, 0, 8, 0);
                extraImage.setLayoutParams(params);
                Picasso.get().load(url).into(extraImage);
                llThumbnailExtraContainer.addView(extraImage);
            }
        } else {
            Log.w("ProductActivity", "No extra thumbnails to display");
        }
    }

    @Override
    public void setFavourite(boolean isFavourite) {
        runOnUiThread(() -> {
            ivFavourite.setTag(isFavourite ? "favorite" : "not_favorite");
            ivFavourite.setImageResource(isFavourite ? R.drawable.ic_favorite : R.drawable.ic_not_favorite);
        });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.iv_favourite) {
                actionFavourite();
            }
            if (view.getId() == R.id.tv_add_to_cart) {
                addToCart();
            }
        }

        private void actionFavourite() {
            if (productDto == null) return;

            boolean newFavouriteStatus = !"favorite".equals(ivFavourite.getTag());
            ivFavourite.setTag(newFavouriteStatus ? "favorite" : "not_favorite");
            setFavourite(newFavouriteStatus);

            ProductModel product = new ProductModel(
                    productDto.getName(),
                    productDto.getDescription(),
                    productDto.getThumbnail(),
                    productDto.getPrice(),
                    productDto.getQuantity(),
                    productDto.getCategoryId()
            );
            mPresenter.setFavourite(product, newFavouriteStatus);
        }

        private void addToCart() {
            if (productDto == null) {
                Toast.makeText(ProductActivity.this, "Không có sản phẩm để thêm", Toast.LENGTH_SHORT).show();
                return;
            }

            int productId = productDto.getId();
            double price = productDto.getPrice();
            String productName = productDto.getName();
            String thumbnail = productDto.getThumbnail();
            int quantity = 1;

            Log.i("ProductActivity", "Adding to cart - Product ID: " + productId + ", Price: " + price);

            OrderItemDto orderItemDto = new OrderItemDto(
                    productId,
                    quantity,
                    price,
                    productName,
                    thumbnail
            );

            mPresenter.addToCart(orderItemDto);
        }
    };
}