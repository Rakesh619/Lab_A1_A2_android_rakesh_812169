package com.example.lab_a1_a2_android_rakesh_812169.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lab_a1_a2_android_rakesh_812169.database.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    List<Product> getAllProducts();

    @Query("SELECT * FROM products WHERE product_id IN (:product_ids)")
    List<Product> getAllByIds(int[] product_ids);

    @Query("SELECT * FROM products WHERE provider_id IN (:provider_ids)")
    List<Product> getAllByProviderIds(int[] provider_ids);

    @Query("SELECT * FROM products WHERE product_id = :product_id")
    Product getPrductById(int product_id);

    @Query("SELECT * FROM products WHERE product_name LIKE :text OR " + "product_description LIKE :text")
    List<Product> findByNameAndDescription(String text);

    @Insert
    void insertAllProducts(List<Product> products);

    @Update
    void updateProduct(Product product);

    @Delete
    void deleteProduct(Product product);
}
