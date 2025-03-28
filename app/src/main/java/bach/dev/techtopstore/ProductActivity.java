package bach.dev.techtopstore;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

import bach.dev.techtopstore.data.dto.OrderDto;
import bach.dev.techtopstore.data.dto.OrderItemDto;
import bach.dev.techtopstore.data.dto.ProductDto;
import bach.dev.techtopstore.data.model.ProductModel;
import bach.dev.techtopstore.ui.constract.ProductConstract;
import bach.dev.techtopstore.ui.presenter.ProductPresenter;
import bach.dev.techtopstore.util.Constants;

public class ProductActivity extends AppCompatActivity implements ProductConstract.View {
    private ImageView ivThumbnail;
    private LinearLayout llThumbnailExtraContainer; // Thay vì ImageView tĩnh
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvDescription;
    private TextView tvRating;
    private TextView tvSoldCount;
    private ImageView ivFavourite;
    private TextView tvAddToCart;
    private ImageView[] starViews; // Mảng để quản lý các ngôi sao

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
        mPresenter.getProduct(productId);
    }

    @SuppressLint("WrongViewCast")
    private void initGUI() {
        ivThumbnail = findViewById(R.id.iv_product_thumbnail);
        llThumbnailExtraContainer = findViewById(R.id.ll_thumbnail_extra_container); // Thêm ID này vào XML
        tvName = findViewById(R.id.tv_product_name);
        tvPrice = findViewById(R.id.tv_product_price);
        tvDescription = findViewById(R.id.tv_product_description);
        tvRating = findViewById(R.id.tv_product_rating);
        tvSoldCount = findViewById(R.id.tv_product_sold_count);
        ivFavourite = findViewById(R.id.iv_favourite);
        ivFavourite.setOnClickListener(listener);
        tvAddToCart = findViewById(R.id.tv_add_to_cart);
        tvAddToCart.setOnClickListener(listener);

        // Khởi tạo mảng các ngôi sao
        starViews = new ImageView[] {
                findViewById(R.id.star1), // Thêm ID cho từng ImageView trong XML
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
    public void showError(String message) {}

    @Override
    public void showProduct(ProductDto product) {
        productDto = product;
        tvName.setText(product.getName());
        tvPrice.setText(String.format("$%.2f", product.getPrice()));
        tvDescription.setText(product.getDescription());
        tvSoldCount.setText("Sold: " + product.getSoldCount());
        Picasso.get().load(product.getThumbnail()).into(ivThumbnail);

        // Hiển thị rating với các ngôi sao
        float rating = product.getRating();
        tvRating.setText(String.valueOf(rating));
        updateStarRating(rating);

        // Hiển thị extra thumbnails
        displayExtraThumbnails(product.getThumbnailExtra());

        // Kiểm tra trạng thái yêu thích
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
        if (extraThumbnails != null && !extraThumbnails.isEmpty()) {
            llThumbnailExtraContainer.removeAllViews();
            for (String url : extraThumbnails) {
                ImageView extraImage = new ImageView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
                params.setMargins(8, 0, 8, 0);
                extraImage.setLayoutParams(params);
                Picasso.get().load(url).into(extraImage);
                llThumbnailExtraContainer.addView(extraImage);
            }
        }
    }

    @Override
    public void setFavourite(boolean isFavourite) {
        if(isFavourite) {
            ivFavourite.setImageResource(R.drawable.ic_favorite);
        } else {
            ivFavourite.setImageResource(R.drawable.ic_not_favourite);
        }
    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.iv_favourite){
                actionFavourite();
            }
            if(view.getId() == R.id.tv_add_to_cart){
                addToCart();
            }
        }

        private void actionFavourite() {
            ProductModel product = new ProductModel(
                    productDto.getName(),
                    productDto.getDescription(),
                    productDto.getThumbnail(),
                    productDto.getPrice(),
                    productDto.getQuantity(),
                    productDto.getCategoryId()
            );
            mPresenter.setFavourite(product);
        }

        private void addToCart() {
            //Get Order by userID and Status
            OrderItemDto orderItemDto = new OrderItemDto(
                    productDto.getId(),
                    1,
                    100,
                    productDto.getPrice()
            );
            mPresenter.getOrderByStatus(1, "pending", orderItemDto);
        }
    };
}