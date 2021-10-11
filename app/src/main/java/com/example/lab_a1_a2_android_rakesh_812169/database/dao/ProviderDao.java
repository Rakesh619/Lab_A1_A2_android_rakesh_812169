package com.example.lab_a1_a2_android_rakesh_812169.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.lab_a1_a2_android_rakesh_812169.database.model.ProviderAndProducts;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Provider;

import java.util.List;

@Dao
public interface ProviderDao {
    @Query("SELECT * FROM providers")
    List<Provider> getAllProviders();

    @Query("SELECT * FROM providers WHERE provider_id IN (:provider_ids)")
    List<Provider> getAllByIds(int[] provider_ids);

    @Query("SELECT * FROM providers WHERE provider_id IN (:provider_ids)")
    List<Provider> getAllByProviderIds(int[] provider_ids);

    @Query("SELECT * FROM providers WHERE provider_id = :provider_id")
    Provider getProviderById(int provider_id);

    @Query("SELECT * FROM providers WHERE provider_name LIKE :text OR " + "provider_email LIKE :text")
    List<Provider> findByNameAndEmail(String text);

    @Insert
    void insertAllProviders(List<Provider> providers);

    @Update
    void updateProvider(Provider providers);

    @Delete
    void deleteProvider(Provider provider);

    @Query("SELECT * FROM providers")
    List<ProviderAndProducts> getAllProviderProducts();

    @Query("SELECT * FROM providers WHERE provider_name LIKE :text OR " + "provider_email LIKE :text")
    List<ProviderAndProducts> findByNameAndEmail2(String text);
}
