package com.example.lab_a1_a2_android_rakesh_812169.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab_a1_a2_android_rakesh_812169.R;
import com.example.lab_a1_a2_android_rakesh_812169.adapters.ProductsAdapter;
import com.example.lab_a1_a2_android_rakesh_812169.adapters.ProvidersAdapter;
import com.example.lab_a1_a2_android_rakesh_812169.database.AppDatabase;
import com.example.lab_a1_a2_android_rakesh_812169.database.DatabaseClient;
import com.example.lab_a1_a2_android_rakesh_812169.database.dao.ProviderDao;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Provider;

import java.util.ArrayList;
import java.util.List;

public class ProvidersFragment extends Fragment {
    private RecyclerView recyclerView;
    private AppDatabase db;
    private ProviderDao providerDao;
    private List<Provider> providers;
    private ProvidersAdapter adapter;
    private AppCompatEditText etSearch;


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

        db = DatabaseClient.getInstance(getActivity()).getAppDb();
        providers = new ArrayList<>();
        adapter = new ProvidersAdapter(getContext());
        providerDao = db.providerDao();

        textChange();
        refreshFragment("");
        return view;
    }

    private void refreshFragment(@NonNull String text) {
        if (text.equals("")) providers = providerDao.getAllProviders();
        else providers= providerDao.findByNameAndEmail("%" + text + "%");
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