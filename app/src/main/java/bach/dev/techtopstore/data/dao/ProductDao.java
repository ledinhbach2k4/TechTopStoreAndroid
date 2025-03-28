package bach.dev.techtopstore.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import bach.dev.techtopstore.data.model.ProductModel;

@Dao
public interface ProductDao {
    @Insert
    void insert(ProductModel product);

    @Query("SELECT * FROM products WHERE id=:id LIMIT 1")
    ProductModel findById(int id);

    @Query("SELECT * FROM products")
    List<ProductModel> getAll();

    @Update
    void update(ProductModel product);

    @Delete
    void delete(ProductModel product);
}
