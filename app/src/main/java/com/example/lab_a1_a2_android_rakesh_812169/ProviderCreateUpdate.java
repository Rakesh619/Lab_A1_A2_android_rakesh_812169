package com.example.lab_a1_a2_android_rakesh_812169;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_a1_a2_android_rakesh_812169.adapters.ProductsAdapter;
import com.example.lab_a1_a2_android_rakesh_812169.database.AppDatabase;
import com.example.lab_a1_a2_android_rakesh_812169.database.DatabaseClient;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Product;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Provider;
import com.example.lab_a1_a2_android_rakesh_812169.helper.SwipeHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ProviderCreateUpdate extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private static final String TAG = "ProviderCreateUpdate";
    private AppCompatEditText etPrName;
    private AppCompatEditText etPrEmail;
    private AppCompatEditText etPrPhone;
    private AppCompatTextView etPrDisp;
    private RecyclerView recyclerView;
    private AppDatabase db;
    private Provider provider;
    private List<Product> products;
    private ProductsAdapter adapter;
    private Boolean update_mode;
    private int id = -1;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private AppCompatButton btnSave;
    private MaterialButton btnCall;
    private MaterialButton btnEmail;
    private LatLng latLng;
    private FrameLayout frameLayout;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_create_update);
        db = DatabaseClient.getInstance(getApplicationContext()).getAppDb();
        adapter = new ProductsAdapter(getBaseContext());
        recyclerView = findViewById(R.id.provider_products_recyclerView);

        ItemTouchHelper.Callback callback = new SwipeHelper(adapter, getApplicationContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        adapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        etPrName = findViewById(R.id.provider_name_et);
        etPrEmail = findViewById(R.id.provider_email_et);
        etPrPhone = findViewById(R.id.provider_phone_et);
        etPrDisp = findViewById(R.id.products_disp_heading);
        btnSave = findViewById(R.id.btn_create_update);
        btnCall = findViewById(R.id.provider_call_btn);
        btnEmail = findViewById(R.id.provider_email_btn);
        frameLayout = findViewById(R.id.provider_com);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.provider_mapview);
        mapFragment.getMapAsync(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            id = intent.getIntExtra("provider_id", -1);
            setupSaveBtn();
            update_mode = intent.getBooleanExtra("update_mode", false);
            if (id != -1) {

                provider = db.providerDao().getProviderById(id);
                products = db.productDao().getAllByProviderIds(new int[]{id});

                isEditMode(update_mode);
                adapter.setData(products);
                recyclerView.setAdapter(adapter);
                setTextViews(provider);

                getSupportActionBar().setTitle(provider.getProvider_name());
            } else {
                etPrDisp.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);

                getSupportActionBar().setTitle("Create Provider");
                btnSave.setText("Create");
            }
        }
    }

    private void isEditMode(boolean isEditMode) {
        etPrName.setEnabled(isEditMode);
        etPrEmail.setEnabled(isEditMode);
        etPrPhone.setEnabled(isEditMode);

        if (isEditMode) {
            btnSave.setText("Update");
            etPrDisp.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            if (provider.getProvider_lat() != 0 && provider.getProvider_lng() != 0) {
                latLng = new LatLng(provider.getProvider_lat(), provider.getProvider_lng());
            }
            frameLayout.setVisibility(View.GONE);
        } else {
            findViewById(R.id.provider_mapview).setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
        }
    }

    private void setTextViews(Provider provider) {
        etPrName.setText(provider.getProvider_name());
        etPrEmail.setText(provider.getProvider_email());
        etPrPhone.setText(provider.getProvider_phone());
    }

    private void setupSaveBtn() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callAction = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + etPrPhone.getText().toString().trim()));
                startActivity(callAction);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriText = "mailto:" + provider.getProvider_email() + "" +
                        "?subject=" + Uri.encode(provider.getProvider_name()) +
                        "&body=" + Uri.encode(provider.getProvider_phone());
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse(uriText));

//                if (intent.resolveActivity(getPackageManager()) != null)
                startActivity(Intent.createChooser(intent, "Send Email"));

            }
        });

    }

    private void onClickSave() {
        String name = etPrName.getText().toString().trim();
        String phone = etPrPhone.getText().toString().trim();
        String email = etPrEmail.getText().toString().trim();

        if (name == "") {
            etPrName.setError("Enter Provider Name");
            return;
        }
        if (phone == "") {
            etPrPhone.setError("Enter Provider Phone");
            return;
        }
        if (email == "") {
            etPrEmail.setError("Enter Provider Email");
            return;
        }
        if (latLng == null) {
            Toast.makeText(this, "Select a Location on the Map", Toast.LENGTH_LONG).show();
            return;
        }
        if (provider != null) {
            provider.setProvider_name(name);
            provider.setProvider_email(email);
            provider.setProvider_phone(phone);
            provider.setProvider_lat(latLng.latitude);
            provider.setProvider_lng(latLng.longitude);
            db.providerDao().updateProvider(provider);
            Toast.makeText(this, "Updated " + name + " Successfully", Toast.LENGTH_LONG).show();
        } else {
            List<Provider> list = new ArrayList<>();
            list.add(new Provider(name, email, phone, latLng.latitude, latLng.longitude));
            db.providerDao().insertAllProviders(list);
            Toast.makeText(this, "Created " + name + " Successfully", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);

        if (provider != null) {
            mMap.clear();
            addMarker(provider.getProvider_name(), provider.getProvider_email());
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        this.latLng = latLng;
        mMap.clear();
        addMarker(null, null);
    }

    private void addMarker(@Nullable String title, @Nullable String desc) {
        if (latLng != null) {
            Marker m1 = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(title == null ? "Select this location" : title)
                    .snippet(desc)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.twotone_room_black_36))
                    .anchor(0f, 0.5f)
                    .visible(true)
                    .draggable(false));
            m1.showInfoWindow();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(m1.getPosition(), 12);
            mMap.animateCamera(cameraUpdate);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null && !update_mode && id != -1) {
            products = db.productDao().getAllByProviderIds(new int[]{id});
            adapter.setData(products);
        }
    }
}