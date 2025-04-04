package bach.dev.techtopstore.data.dto;

public class OrderItemDto {
    private int id; // Thêm trường id
    private int productId;
    private int quantity;
    private double price;
    private String productName;
    private String thumbnail;
    private String orderId; // Thêm trường orderId nếu cần

    // Constructor đầy đủ
    public OrderItemDto(int id, int productId, int quantity, double price, String productName, String thumbnail, String orderId) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.productName = productName;
        this.thumbnail = thumbnail;
        this.orderId = orderId;
    }

    // Constructor cho ProductActivity (không cần id và orderId ngay lập tức)
    public OrderItemDto(int productId, int quantity, double price, String productName, String thumbnail) {
        this(0, productId, quantity, price, productName, thumbnail, null); // id = 0, orderId = null mặc định
    }

    // Constructor mặc định cho CartPresenter
    public OrderItemDto() {
        this(0, 0, 0, 0.0, null, null, null);
    }

    // Getter và Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
}