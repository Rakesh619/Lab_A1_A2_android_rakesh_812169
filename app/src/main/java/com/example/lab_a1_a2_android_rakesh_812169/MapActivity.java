package com.example.lab_a1_a2_android_rakesh_812169;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lab_a1_a2_android_rakesh_812169.database.AppDatabase;
import com.example.lab_a1_a2_android_rakesh_812169.database.DatabaseClient;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Provider;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mMap;
    private AppDatabase db;
    private List<Provider> providers;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.provider_mapview_map);
        mapFragment.getMapAsync(this);

        db = DatabaseClient.getInstance(getApplicationContext()).getAppDb();


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Provider MapView");

    }

    private void addMarker(int id, LatLng latLng, @Nullable String title, @Nullable String email) {
        if (latLng != null) {
            Marker m1 = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .snippet(email)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.twotone_room_black_36))
                    .anchor(0f, 0.5f)
                    .visible(true)
                    .draggable(false));
            m1.setTag(id);
//            m1.showInfoWindow();
        }
    }

    private void populateMarkers() {
        mMap.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        List<LatLng> latLngs = new ArrayList<>();

        for (Provider provider : providers) {
            LatLng latLng = new LatLng(provider.getProvider_lat(), provider.getProvider_lng());
            latLngs.add(latLng);
            builder.include(latLng);
            addMarker(provider.getProvider_id(), latLng, provider.getProvider_name(), provider.getProvider_email());
        }
        if (latLngs.size() > 0) {
            LatLngBounds bounds = builder.build();
            int padding = 50; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        providers = db.providerDao().getAllProviders();
        if (providers != null && providers.size() > 0) {
            populateMarkers();
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.getTag() != null) {
            Intent intent = new Intent(this, ProviderCreateUpdate.class);
            intent.putExtra("provider_id", (Integer) marker.getTag());
            startActivity(intent);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}