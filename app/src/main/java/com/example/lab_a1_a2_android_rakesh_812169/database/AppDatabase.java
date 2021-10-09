package com.example.lab_a1_a2_android_rakesh_812169.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lab_a1_a2_android_rakesh_812169.database.dao.ProductDao;
import com.example.lab_a1_a2_android_rakesh_812169.database.dao.ProviderDao;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Product;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Provider;


@Database(entities = {Provider.class, Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProviderDao providerDao();

    public abstract ProductDao productDao();
}
