package com.example.lab_a1_a2_android_rakesh_812169;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lab_a1_a2_android_rakesh_812169.adapters.ProductsAdapter;
import com.example.lab_a1_a2_android_rakesh_812169.database.AppDatabase;
import com.example.lab_a1_a2_android_rakesh_812169.database.DatabaseClient;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Product;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Provider;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ProviderCreateUpdate extends AppCompatActivity {
    private AppCompatEditText etPrName;
    private AppCompatEditText etPrEmail;
    private AppCompatEditText etPrPhone;
    private AppCompatTextView etPrDisp;
    private RecyclerView recyclerView;
    private AppDatabase db;
    private Provider provider;
    private List<Product> products;
    private ProductsAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();
    }

    private static final String TAG = "ProviderCreateUpdate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_create_update);
        db = DatabaseClient.getInstance(getApplicationContext()).getAppDb();
        adapter = new ProductsAdapter(getBaseContext());

        etPrName = findViewById(R.id.provider_name_et);
        etPrEmail = findViewById(R.id.provider_email_et);
        etPrPhone = findViewById(R.id.provider_phone_et);
        etPrDisp = findViewById(R.id.products_disp_heading);
        recyclerView = findViewById(R.id.provider_products_recyclerView);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            int id = intent.getIntExtra("provider_id", 0);
            provider = db.providerDao().getProviderById(id);
            products = db.productDao().getAllByProviderIds(new int[]{id});

            adapter.setData(products);
            recyclerView.setAdapter(adapter);
            setTextViews(provider);

            getSupportActionBar().setTitle(provider.getProvider_name());
        }
    }

    private void setTextViews(Provider provider) {
        etPrName.setText("Name: " + provider.getProvider_name());
        etPrEmail.setText("Email: " + provider.getProvider_email());
        etPrPhone.setText("Phone: " + provider.getProvider_phone());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}