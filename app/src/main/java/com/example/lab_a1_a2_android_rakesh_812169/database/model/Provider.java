package com.example.lab_a1_a2_android_rakesh_812169.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "providers")
public class Provider implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int provider_id;

    @ColumnInfo(name = "provider_name")
    private String provider_name;

    @ColumnInfo(name = "provider_email")
    private String provider_email;

    @ColumnInfo(name = "provider_phone")
    private String provider_phone;

    @ColumnInfo(name = "provider_lat")
    private double provider_lat;

    @ColumnInfo(name = "provider_lng")
    private double provider_lng;

    @Ignore
    private List<Product> products;

    public Provider() {
    }

    public Provider(String provider_name, String provider_email, String provider_phone) {
        this.provider_name = provider_name;
        this.provider_email = provider_email;
        this.provider_phone = provider_phone;
    }

    public Provider(String provider_name, String provider_email, String provider_phone, double provider_lat, double provider_lng) {
        this.provider_name = provider_name;
        this.provider_email = provider_email;
        this.provider_phone = provider_phone;
        this.provider_lat = provider_lat;
        this.provider_lng = provider_lng;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getProvider_email() {
        return provider_email;
    }

    public void setProvider_email(String provider_email) {
        this.provider_email = provider_email;
    }

    public String getProvider_phone() {
        return provider_phone;
    }

    public void setProvider_phone(String provider_phone) {
        this.provider_phone = provider_phone;
    }

    public double getProvider_lat() {
        return provider_lat;
    }

    public void setProvider_lat(double provider_lat) {
        this.provider_lat = provider_lat;
    }

    public double getProvider_lng() {
        return provider_lng;
    }

    public void setProvider_lng(double provider_lng) {
        this.provider_lng = provider_lng;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return  provider_name;
    }

}