package bach.dev.techtopstore.data.api;

import java.util.List;

import bach.dev.techtopstore.data.dto.CategoryDto;
import bach.dev.techtopstore.data.dto.OrderDto;
import bach.dev.techtopstore.data.dto.OrderItemDto;
import bach.dev.techtopstore.data.dto.ProductDto;
import bach.dev.techtopstore.data.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    // Categories
    @GET("categories")
    Call<List<CategoryDto>> getCategories();

    @GET("categories/{id}")
    Call<CategoryDto> getCategory(@Path("id") int id);

    @POST("categories")
    Call<CategoryDto> createCategory(@Body CategoryDto categoryDto);

    @PUT("categories/{id}")
    Call<CategoryDto> updateCategory(@Path("id") int id, @Body CategoryDto categoryDto);

    @DELETE("categories/{id}")
    Call<Void> deleteCategory(@Path("id") int id);

    // Products
    @GET("products")
    Call<List<ProductDto>> getProducts();

    @GET("products/search")
    Call<List<ProductDto>> searchProducts(@Query("keyword") String keyword);

    @GET("products/category/{id}")
    Call<List<ProductDto>> getProductsByCategory(@Path("id") int categoryId);

    @GET("products/{id}")
    Call<ProductDto> getProduct(@Path("id") int id);

    @GET("products/filter")
    Call<List<ProductDto>> getProductsByProperty(
            @Query("property") String property,
            @Query("order") String order
    );

    @POST("products")
    Call<ProductDto> createProduct(@Body ProductDto productDto);

    @PUT("products/{id}")
    Call<ProductDto> updateProduct(@Path("id") int id, @Body ProductDto productDto);

    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") int id);

    // Users
    @FormUrlEncoded
    @POST("auth/login")
    Call<UserDto> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("auth/register")
    Call<UserDto> register(@Body UserDto userDto);

    @GET("users")
    Call<List<UserDto>> getUsers();

    @GET("users/{id}")
    Call<UserDto> getUser(@Path("id") int id);

    @POST("users")
    Call<UserDto> createUser(@Body UserDto userDto);

    @PUT("users/{id}")
    Call<UserDto> updateUser(@Path("id") int id, @Body UserDto userDto);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    // Cart and Orders
    @POST("cart/add-to-cart")
    Call<OrderItemDto> addToCart(
            @Query("product_id") int productId,
            @Query("order_id") int orderId,
            @Query("quantity") int quantity,
            @Query("price") double price
    );

    @GET("orders/get-by-status")
    Call<OrderDto> getOrderByStatus(
            @Query("userId") int userId,
            @Query("status") String status
    );

    @GET("cart/items/{orderId}")
    Call<List<OrderItemDto>> getCartItems(@Path("orderId") int orderId);

    @PUT("order-items/{id}")
    Call<OrderItemDto> updateCartItem(
            @Path("id") int itemId,
            @Body OrderItemDto orderItemDto
    );

    @DELETE("order-items/{id}")
    Call<Void> removeCartItem(@Path("id") int itemId);

    @PUT("orders/{id}")
    Call<OrderDto> checkout(@Path("id") int orderId, @Body OrderDto orderDto);

    // Order Management
    @GET("orders")
    Call<List<OrderDto>> getOrders();

    @GET("orders/{id}")
    Call<OrderDto> getOrder(@Path("id") int id);

    @POST("orders")
    Call<OrderDto> createOrder(@Body OrderDto orderDto);

    @PUT("orders/{id}")
    Call<OrderDto> updateOrder(@Path("id") int id, @Body OrderDto orderDto);

    @DELETE("orders/{id}")
    Call<Void> deleteOrder(@Path("id") int id);

    // Order Items
    @GET("order-items")
    Call<List<OrderItemDto>> getOrderItems();

    @GET("order-items/{id}")
    Call<OrderItemDto> getOrderItem(@Path("id") int id);

    @POST("order-items")
    Call<OrderItemDto> createOrderItem(@Body OrderItemDto orderItemDto);

    @PUT("order-items/{id}")
    Call<OrderItemDto> updateOrderItem(@Path("id") int id, @Body OrderItemDto orderItemDto);

    @DELETE("order-items/{id}")
    Call<Void> deleteOrderItem(@Path("id") int id);
}