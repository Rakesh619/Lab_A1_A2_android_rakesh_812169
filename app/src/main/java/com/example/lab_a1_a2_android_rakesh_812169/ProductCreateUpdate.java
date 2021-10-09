package com.example.lab_a1_a2_android_rakesh_812169;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.lab_a1_a2_android_rakesh_812169.database.AppDatabase;
import com.example.lab_a1_a2_android_rakesh_812169.database.DatabaseClient;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Product;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Provider;

public class ProductCreateUpdate extends AppCompatActivity {
    private AppCompatEditText etPname;
    private AppCompatEditText etPdesc;
    private AppCompatEditText etPprice;
    private AppCompatTextView etPrName;
    private AppCompatTextView etPrEmail;
    private AppCompatTextView etPrPhone;
    private AppCompatTextView etPrDisp;
    private AppDatabase db;
    private Boolean enableEdit = false;
    private Product product;
    private Provider provider;

    @Override
    protected void onStart() {
        super.onStart();
        db = DatabaseClient.getInstance(this.getApplicationContext()).getAppDb();
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            int id = intent.getIntExtra("product_id", 0);
            product = db.productDao().getPrductById(id);
            provider = db.providerDao().getProviderById(product.getProvider_id());
            getSupportActionBar().setTitle(product.getProduct_name());
            setTextViews(product, provider);
        } else {
            etPrName.setVisibility(View.GONE);
            etPrEmail.setVisibility(View.GONE);
            etPrPhone.setVisibility(View.GONE);
            etPrDisp.setVisibility(View.GONE);
            etPname.setFocusable(true);
            etPdesc.setFocusable(true);
            etPprice.setFocusable(true);
            getSupportActionBar().setTitle("Create/Edit");

        }
    }

    private void setTextViews(Product product, Provider provider) {
        etPname.setText(product.getProduct_name());
        etPdesc.setText(product.getProduct_description());
        etPprice.setText("Price: " + product.getProduct_price() + " $CAD");
        etPrName.setText("Name: " + provider.getProvider_name());
        etPrEmail.setText("Email: " + provider.getProvider_email());
        etPrPhone.setText("Phone: " + provider.getProvider_phone());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_create_update);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        etPname = findViewById(R.id.product_name_et);
        etPdesc = findViewById(R.id.product_description_et);
        etPprice = findViewById(R.id.product_price_et);
        etPrName = findViewById(R.id.provider_name_disp);
        etPrEmail = findViewById(R.id.provider_email_disp);
        etPrPhone = findViewById(R.id.provider_phone_disp);
        etPrDisp = findViewById(R.id.provider_disp_heading);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}