package bach.dev.techtopstore.data.api;

import java.util.List;

import bach.dev.techtopstore.data.dto.CategoryDto;
import bach.dev.techtopstore.data.dto.OrderDto;
import bach.dev.techtopstore.data.dto.OrderItemDto;
import bach.dev.techtopstore.data.dto.ProductDto;
import bach.dev.techtopstore.data.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("categories")
    Call<List<CategoryDto>> getCategories();
    @GET("products")
    Call<List<ProductDto>> getProducts();
    @GET("products/search")
    Call<List<ProductDto>> searchProducts(@Query("keyword") String keyword);
    @GET("products/category/{id}")
    Call<List<ProductDto>> getProductsByCategory(@Path("id") int categoryId);


    @GET("products/{id}")
    Call<ProductDto> getProduct(@Path("id") int id);

    /**
     * Api for users
     */
    @POST("auth/login")
    Call<UserDto> login(@Body UserDto userDto);

    /**
     * Api cart
     */

    @POST("cart/add-to-cart")
    Call<OrderItemDto> addToCart(
            @Body OrderItemDto orderItemDto);

    @GET("orders/get-by-status")
    Call<OrderDto> getOrderByStatus(@Query("userId") int userId, @Query("status") String status);


    @GET("products/filter")
    Call<List<ProductDto>> getProductsByProperty(@Query("property") String property,@Query("order") String order);
}
