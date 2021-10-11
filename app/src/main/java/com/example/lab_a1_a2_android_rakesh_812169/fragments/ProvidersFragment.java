package com.example.lab_a1_a2_android_rakesh_812169.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab_a1_a2_android_rakesh_812169.MapActivity;
import com.example.lab_a1_a2_android_rakesh_812169.ProductCreateUpdate;
import com.example.lab_a1_a2_android_rakesh_812169.ProviderCreateUpdate;
import com.example.lab_a1_a2_android_rakesh_812169.R;
import com.example.lab_a1_a2_android_rakesh_812169.adapters.ProvidersAdapter;
import com.example.lab_a1_a2_android_rakesh_812169.database.AppDatabase;
import com.example.lab_a1_a2_android_rakesh_812169.database.DatabaseClient;
import com.example.lab_a1_a2_android_rakesh_812169.database.dao.ProviderDao;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Provider;
import com.example.lab_a1_a2_android_rakesh_812169.helper.SwipeHelper;

import java.util.ArrayList;
import java.util.List;

public class ProvidersFragment extends Fragment {
    private RecyclerView recyclerView;
    private AppDatabase db;
    private ProviderDao providerDao;
    private List<Provider> providers;
    private ProvidersAdapter adapter;
    private AppCompatEditText etSearch;
    private AppCompatButton btnMap;
    private AppCompatButton btnAdd;


    public ProvidersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.providers_fragment, container, false);
        recyclerView = view.findViewById(R.id.provider_recyclerView);
        etSearch = view.findViewById(R.id.provider_search_et);
        btnMap = view.findViewById(R.id.btn_mapview);
        btnAdd = view.findViewById(R.id.btn_add_provider);

        db = DatabaseClient.getInstance(getActivity()).getAppDb();
        providers = new ArrayList<>();
        adapter = new ProvidersAdapter(getContext());
        providerDao = db.providerDao();

        ItemTouchHelper.Callback callback = new SwipeHelper(adapter, getContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        adapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        setMapBtn();
        textChange();
        refreshFragment("");
        return view;
    }

    private void setMapBtn() {
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProviderCreateUpdate.class);
                intent.putExtra("update_mode", false);
                startActivity(intent);
            }
        });
    }

    private void refreshFragment(@NonNull String text) {
        if (text.equals("")) providers = new ArrayList<>(providerDao.getAllProviderProducts());
        else providers = new ArrayList<>(providerDao.findByNameAndEmail2("%" + text + "%"));
        adapter.setData(providers);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFragment("");
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

}