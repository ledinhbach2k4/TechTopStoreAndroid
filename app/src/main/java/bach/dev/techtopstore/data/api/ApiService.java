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

    // Users
    @POST("auth/login")
    Call<UserDto> login(@Body UserDto userDto);

    // Cart and Orders
    @POST("cart/add-to-cart")
    Call<OrderItemDto> addToCart(@Body OrderItemDto orderItemDto);

    @GET("orders/get-by-status")
    Call<OrderDto> getOrderByStatus(
            @Query("userId") int userId,
            @Query("status") String status
    );

    // Thêm các phương thức cho giỏ hàng
    @GET("cart/items")
    Call<List<OrderItemDto>> getCartItems();

    @FormUrlEncoded
    @PUT("cart/items/{itemId}")
    Call<OrderItemDto> updateCartItem(
            @Path("itemId") int itemId,
            @Field("quantity") int quantity
    );

    @DELETE("cart/items/{itemId}")
    Call<Void> removeCartItem(
            @Path("itemId") int itemId
    );

    @POST("cart/checkout")
    Call<Void> checkout();
}