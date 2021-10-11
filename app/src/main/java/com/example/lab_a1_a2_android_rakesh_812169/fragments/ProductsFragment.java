package com.example.lab_a1_a2_android_rakesh_812169.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_a1_a2_android_rakesh_812169.ProductCreateUpdate;
import com.example.lab_a1_a2_android_rakesh_812169.R;
import com.example.lab_a1_a2_android_rakesh_812169.adapters.ProductsAdapter;
import com.example.lab_a1_a2_android_rakesh_812169.database.AppDatabase;
import com.example.lab_a1_a2_android_rakesh_812169.database.DatabaseClient;
import com.example.lab_a1_a2_android_rakesh_812169.database.dao.ProductDao;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Product;
import com.example.lab_a1_a2_android_rakesh_812169.helper.SwipeHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {
    private RecyclerView recyclerView;
    private AppDatabase db;
    private ProductDao productDao;
    private List<Product> products;
    private ProductsAdapter adapter;
    private AppCompatEditText etSearch;
    private AppCompatButton btnAdd;

    public ProductsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.products_fragment, container, false);
        recyclerView = view.findViewById(R.id.product_recyclerView);

        btnAdd = view.findViewById(R.id.btn_add_product);
        etSearch = view.findViewById(R.id.product_search_et);

        textChange();

        db = DatabaseClient.getInstance(getActivity()).getAppDb();
        products = new ArrayList<>();
        adapter = new ProductsAdapter(getContext());

        ItemTouchHelper.Callback callback = new SwipeHelper(adapter, getContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        adapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);

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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.providerDao().getAllProviders().size() > 0) {
                    Intent intent = new Intent(getActivity(), ProductCreateUpdate.class);
                    intent.putExtra("update_mode", false);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Please add Provider before adding Product", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), ProductCreateUpdate.class);
                    intent.putExtra("update_mode", false);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFragment("");
    }
}

