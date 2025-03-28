package bach.dev.techtopstore.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {
    private int id;
    private String name;
    private String description;
    private String thumbnail;
    @SerializedName("thumbnail_extra") // Đảm bảo khớp với tên trường trong JSON
    private List<String> thumbnailExtra;
    private double price;
    private int quantity;
    private int categoryId;
    private float rating;
    private int soldCount;

    // Constructor mặc định
    public ProductDto() {
        this.name = "";
        this.description = "";
        this.thumbnail = "";
        this.thumbnailExtra = new ArrayList<>();
        this.price = 0.0;
        this.quantity = 0;
        this.categoryId = 0;
        this.rating = 0.0f;
        this.soldCount = 0;
    }

    // Constructor đầy đủ
    public ProductDto(int id, String name, String description, String thumbnail, List<String> thumbnailExtra,
                      double price, int quantity, int categoryId, float rating, int soldCount) {
        this.id = id;
        this.name = name != null ? name : "";
        this.description = description != null ? description : "";
        this.thumbnail = thumbnail != null ? thumbnail : "";
        this.thumbnailExtra = (thumbnailExtra != null) ? thumbnailExtra : new ArrayList<>();
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.rating = rating;
        this.soldCount = soldCount;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name : "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail != null ? thumbnail : "";
    }

    public List<String> getThumbnailExtra() {
        return thumbnailExtra;
    }

    public void setThumbnailExtra(List<String> thumbnailExtra) {
        this.thumbnailExtra = (thumbnailExtra != null) ? thumbnailExtra : new ArrayList<>();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", thumbnailExtra=" + thumbnailExtra +
                ", price=" + price +
                ", quantity=" + quantity +
                ", categoryId=" + categoryId +
                ", rating=" + rating +
                ", soldCount=" + soldCount +
                '}';
    }
}