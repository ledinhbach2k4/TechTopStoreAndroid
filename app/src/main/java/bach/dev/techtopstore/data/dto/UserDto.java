package bach.dev.techtopstore.data.dto;

import com.google.gson.annotations.SerializedName;

public class UserDto {
    @SerializedName("id")
    private int id;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("username")
    private String username;

    // Constructor mặc định (cần cho Gson)
    public UserDto() {
        this.id = 0;
        this.email = "";
        this.password = "";
        this.username = "";
    }

    // Constructor cho đăng nhập (chỉ cần email và password)
    public UserDto(String email, String password) {
        this.id = 0;
        this.email = email != null ? email : "";
        this.password = password != null ? password : "";
        this.username = "";
    }

    // Constructor đầy đủ
    public UserDto(int id, String email, String password, String username) {
        this.id = id;
        this.email = email != null ? email : "";
        this.password = password != null ? password : "";
        this.username = username != null ? username : "";
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email : "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password != null ? password : "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username != null ? username : "";
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}