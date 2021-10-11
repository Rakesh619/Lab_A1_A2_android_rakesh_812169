package com.example.lab_a1_a2_android_rakesh_812169;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lab_a1_a2_android_rakesh_812169.database.AppDatabase;
import com.example.lab_a1_a2_android_rakesh_812169.database.DatabaseClient;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Product;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Provider;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class ProductCreateUpdate extends AppCompatActivity {
    private static final String TAG = "ProductCreateUpdate";
    List<Provider> providers;
    private AppCompatEditText etPname;
    private AppCompatEditText etPdesc;
    private AppCompatEditText etPprice;
    private AppCompatTextView etPrName;
    private AppCompatTextView etPrEmail;
    private AppCompatTextView etPrPhone;
    private AppCompatTextView etPrDisp;
    private AppCompatButton btnSave;
    private AppDatabase db;
    private Boolean enableEdit = false;
    private Product product;
    private Provider provider;
    private Boolean update_mode = false;
    private Spinner spinner;

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
        btnSave = findViewById(R.id.btn_create_update);
        spinner = findViewById(R.id.spinner);

        providers = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = DatabaseClient.getInstance(this.getApplicationContext()).getAppDb();
        Intent intent = getIntent();


        if (intent != null && intent.getExtras() != null) {
            providers = db.providerDao().getAllProviders();
            ArrayAdapter<Provider> providerArrayAdapter = new ArrayAdapter<Provider>(this, android.R.layout.simple_list_item_1, providers);
            spinner.setAdapter(providerArrayAdapter);

            int id = intent.getIntExtra("product_id", -1);
            setupSaveBtn();
            update_mode = intent.getBooleanExtra("update_mode", false);
            if (id != -1) {

                product = db.productDao().getPrductById(id);
                provider = db.providerDao().getProviderById(product.getProvider_id());

                isEditMode(update_mode);
                setTextViews(product, provider);

                getSupportActionBar().setTitle(product.getProduct_name());
            } else {
                isEditMode(true);

                getSupportActionBar().setTitle("Create Product");
                btnSave.setText("Create");
            }
        }
    }

    private void isEditMode(boolean isEditMode) {
        etPname.setEnabled(isEditMode);
        etPdesc.setEnabled(isEditMode);
        etPprice.setEnabled(isEditMode);

        if (isEditMode) {
            btnSave.setText("Update");

            etPrDisp.setVisibility(View.GONE);
            etPrName.setVisibility(View.GONE);
            etPrEmail.setVisibility(View.GONE);
            etPrPhone.setVisibility(View.GONE);
        } else {
            spinner.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
        }
    }

    private void setTextViews(Product product, Provider provider) {
        etPname.setText(product.getProduct_name());
        etPdesc.setText(product.getProduct_description());
        etPprice.setText("" + product.getProduct_price());
        etPrName.setText(provider.getProvider_name());
        etPrEmail.setText(provider.getProvider_email());
        etPrPhone.setText(provider.getProvider_phone());
        for (int i = 0; i < providers.size(); i++) {
            if (providers.get(i).getProvider_id() == provider.getProvider_id())
                spinner.setSelection(i);

        }
    }

    private void setupSaveBtn() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });
    }

    private void onClickSave() {
        String name = etPname.getText().toString().trim();
        String desc = etPdesc.getText().toString().trim();
        String price = etPprice.getText().toString().trim();
        Provider provider = (Provider) spinner.getSelectedItem();
        if (name == "") {
            etPname.setError("Enter Product Name");
            return;
        }
        if (desc == "") {
            etPdesc.setError("Enter Product Description");
            return;
        }
        if (price == "") {
            etPprice.setError("Enter Product Price");
            return;
        }
        if (provider == null) {
            Toast.makeText(this, "Select a Provider for the product", Toast.LENGTH_LONG).show();
            return;
        }
        int provider_id = provider.getProvider_id();
        if (product != null) {
            product.setProduct_name(name);
            product.setProduct_description(desc);
            product.setProduct_price(Integer.parseInt(price));
            product.setProvider_id(provider_id);
            db.productDao().updateProduct(product);

            Toast.makeText(this, "Updated " + name + " Successfully", Toast.LENGTH_LONG).show();
        } else {
            List<Product> list = new ArrayList<>();
            list.add(new Product(name, desc, Integer.parseInt(price), provider_id));
            db.productDao().insertAllProducts(list);
            Toast.makeText(this, "Created " + name + " Successfully", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}