package com.example.lab_a1_a2_android_rakesh_812169.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_a1_a2_android_rakesh_812169.R;
import com.example.lab_a1_a2_android_rakesh_812169.adapters.ProductsAdapter;
import com.example.lab_a1_a2_android_rakesh_812169.database.AppDatabase;
import com.example.lab_a1_a2_android_rakesh_812169.database.DatabaseClient;
import com.example.lab_a1_a2_android_rakesh_812169.database.dao.ProductDao;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {
    private RecyclerView recyclerView;
    private AppDatabase db;
    private ProductDao productDao;
    private List<Product> products;
    private ProductsAdapter adapter;
    private AppCompatEditText etSearch;

    public ProductsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.products_fragment, container, false);
        recyclerView = view.findViewById(R.id.product_recyclerView);

        etSearch = view.findViewById(R.id.product_search_et);

        textChange();

        db = DatabaseClient.getInstance(getActivity()).getAppDb();
        products = new ArrayList<>();
        adapter = new ProductsAdapter(getContext());
        productDao = db.productDao();
        refreshFragment("");
        return view;
    }

    private void refreshFragment(@NonNull String text) {
        if (text.equals("")) products = productDao.getAllProducts();
        else products = productDao.findByNameAndDescription("%" + text + "%");
        adapter.setData(products);
        recyclerView.setAdapter(adapter);
    }

    private void textChange() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                refreshFragment(s.toString().trim());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFragment("");
    }
}

