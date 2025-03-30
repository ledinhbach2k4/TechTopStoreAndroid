package bach.dev.techtopstore.data.dto;

import com.google.gson.annotations.SerializedName;

public class OrderItemDto {
    @SerializedName("id")
    private int id;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("price")
    private double price;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("order_id")
    private int orderId;

    // Constructor mặc định
    public OrderItemDto() {
        this.productName = "";
        this.price = 0.0;
        this.quantity = 0;
        this.thumbnail = "";
        this.orderId = 0;
    }

    // Constructor đầy đủ
    public OrderItemDto(int id, String productName, double price, int quantity, String thumbnail, int orderId) {
        this.id = id;
        this.productName = productName != null ? productName : "";
        this.price = price;
        this.quantity = quantity;
        this.thumbnail = thumbnail != null ? thumbnail : "";
        this.orderId = orderId;
    }

    // Constructor mới cho 4 tham số
    public OrderItemDto(int id, int orderId, int quantity, double price) {
        this.id = id;
        this.orderId = orderId;
        this.quantity = quantity;
        this.price = price;
        this.productName = ""; // Mặc định nếu không có tên sản phẩm
        this.thumbnail = "";   // Mặc định nếu không có thumbnail
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName != null ? productName : "";
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail != null ? thumbnail : "";
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", thumbnail='" + thumbnail + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}