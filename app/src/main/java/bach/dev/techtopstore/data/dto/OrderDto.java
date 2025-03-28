package bach.dev.techtopstore.data.dto;

public class OrderDto {
    private int id;
    private String code;
    private int userId;
    private String status;

    public OrderDto(int id, String code, int userId, String status) {
        this.id = id;
        this.code = code;
        this.userId = userId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
