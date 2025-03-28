package bach.dev.techtopstore.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ProductModel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "thumbnail")
    public String thumbnail;
    @ColumnInfo(name = "thumbnail_extra")  // Mới
    public String thumbnailExtra;
    @ColumnInfo(name = "price")
    public double price;
    @ColumnInfo(name = "quantity")
    public int quantity;
    @ColumnInfo(name = "category_id")
    public int categoryId;
    @ColumnInfo(name = "rating")  // Mới
    public double rating;
    @ColumnInfo(name = "sold_count")  // Mới
    public int soldCount;

    @Ignore
    public ProductModel(String name, String description, String thumbnail, String thumbnailExtra, double price, int quantity, int categoryId, double rating, int soldCount) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.thumbnailExtra = thumbnailExtra;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.rating = rating;
        this.soldCount = soldCount;
    }


    public ProductModel(String name, String description, String thumbnail, double price, int quantity, int categoryId) {
    }
}
