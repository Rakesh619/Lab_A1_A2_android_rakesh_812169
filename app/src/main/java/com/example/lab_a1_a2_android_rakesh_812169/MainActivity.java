package com.example.lab_a1_a2_android_rakesh_812169;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.lab_a1_a2_android_rakesh_812169.adapters.ViewPagerAdapter;
import com.example.lab_a1_a2_android_rakesh_812169.database.AppDatabase;
import com.example.lab_a1_a2_android_rakesh_812169.database.DatabaseClient;
import com.example.lab_a1_a2_android_rakesh_812169.database.dao.ProductDao;
import com.example.lab_a1_a2_android_rakesh_812169.database.dao.ProviderDao;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Product;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Provider;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private AppDatabase db;
    private ProductDao productDao;
    private ProviderDao providerDao;
    private Boolean isMenuVisible = false;
    private ProgressDialog progress;

    @Override
    protected void onStart() {
        super.onStart();
        db = DatabaseClient.getInstance(getApplicationContext()).getAppDb();
        productDao = db.productDao();
        providerDao = db.providerDao();
        progress = new ProgressDialog(this);

        progress.setTitle("Loading....");
        progress.show();
        List<Product> allProducts = productDao.getAllProducts();
        if (allProducts.size() > 0) {
            isMenuVisible = false;
            invalidateOptionsMenu();
        } else {
            isMenuVisible = false; // true
            invalidateOptionsMenu();
            generateDummyData();
//            Toast.makeText(getApplicationContext(), "No products in the table.\nPlease populate some dummy data.", Toast.LENGTH_SHORT).show();
        }
        progress.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void generateDummyData() {
        progress.setTitle("Loading....");
        progress.show();

        List<Provider> providers = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        providers.add(new Provider("Apple", "support@apple.com", "+16470000002",43.812068, -79.549653));
        providers.add(new Provider("Samsung", "support@samsung.com", "+16470000005",43.817407, -79.541570));
        providerDao.insertAllProviders(providers);

        providers = providerDao.getAllProviders();

        for (int i = 0; i < providers.size(); i++) {
            if (providers.get(i).getProvider_name().equals("Apple")) {
                products.add(new Product("iPhone X", "iPhone X, Apple's latest flagship smartphone, is now available in Canada. The phone packs an OLED True Tone display with Super Retina, an A11 Bionic chip processor, 3GB of RAM, 12-megapixel dual cameras and Face ID for facial recognition controls.", 1781, providers.get(i).getProvider_id()));
                products.add(new Product("iPhone 11", "Liquid Retina HD display · 6.1‑inch (diagonal) all-screen LCD Multi-Touch display with IPS technology · 1792‑by‑828‑pixel resolution at 326 ppi", 749, providers.get(i).getProvider_id()));
                products.add(new Product("iPhone 11 pro", "Apple iPhone 11 Pro Octa-core processor, iOS 13 and Hexa Core (2.49 GHz, Dual core, Vortex + 1.52 GHz, Quad core, Tempest) processor and it launched with 6GB of Storage.", 1379, providers.get(i).getProvider_id()));
                products.add(new Product("iPhone 12", "Display. 6.1-inch, Super Retina XDR, OLED True Tone display, 2532 x 1170 pixels. 5.4-inch, Super Retina XDR, OLED True Tone display, 2340 x 1080 pixels. 6.1-inch, Super Retina XDR, OLED True Tone display, 2532 x 1170 pixels, HDR 10, Dolby Vision.", 1019, providers.get(i).getProvider_id()));
                products.add(new Product("Apple MacBook Air (M1, 2020)", "Apple MacBook Air 13.3\" w/ Touch ID (Fall 2020)", 1299, providers.get(i).getProvider_id()));
            } else {
                products.add(new Product("Galaxy Z Flip3 5G", "Samsung Galaxy Z Flip3 5G (Cream, 8GB RAM, 128GB Storage)", 1329, providers.get(i).getProvider_id()));
                products.add(new Product("Samsung galaxy s21", "Galaxy's first 5nm processor packs epic power and speed. This outstanding upgrade means faster processing and more intelligence in every aspect of Galaxy S2", 1414, providers.get(i).getProvider_id()));
                products.add(new Product("Galaxy Note20", "The Galaxy Note20 Ultra takes Samsung's most iconic smartphone series and gives it the right tools to get things done.", 1539, providers.get(i).getProvider_id()));
                products.add(new Product("Galaxy Note20 Ultra", "Samsung launches its latest mobile Galaxy Note20 & Note20 Ultra with versatile S Pen, PC-level performance, pro-grade 8K video camera and more.", 1999, providers.get(i).getProvider_id()));
                products.add(new Product("Samsung Galaxy S21 Ultra 5G", "Introducing Galaxy S21 Ultra 5G. Designed with a unique contour-cut camera to create a revolution in photography — letting you capture cinematic 8K video.", 1499, providers.get(i).getProvider_id()));
            }
        }
        productDao.insertAllProducts(products);
        progress.dismiss();
        this.recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.dummy_data).setVisible(isMenuVisible);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dummy_data:
                generateDummyData();
                break;
            default:
                break;
        }
        return true;
    }

//    public static void hideSoftKeyboard(Activity activity) {
//        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        if (inputMethodManager.isAcceptingText()) {
//            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//        }
//    }

}

