package com.example.lab_a1_a2_android_rakesh_812169.database.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProviderAndProducts extends Provider {
//    @Embedded public Provider provider;
    @Relation(parentColumn = "provider_id",
            entityColumn = "provider_id",
            entity = Product.class)
    private List<Product> products;
}
