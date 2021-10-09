package com.example.lab_a1_a2_android_rakesh_812169.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_a1_a2_android_rakesh_812169.ProviderCreateUpdate;
import com.example.lab_a1_a2_android_rakesh_812169.R;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Provider;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersAdapter.ViewHolder> {
    private List<Provider> localDataSet;
    private Context context;

    public ProvidersAdapter(Context context, List data) {
        this.localDataSet = data;
        this.context = context;
    }

    public ProvidersAdapter() {

    }

    public ProvidersAdapter(Context context) {
        this.context = context;
    }

    public void setData(List data) {
        localDataSet = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProvidersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.providers_list, viewGroup, false);
        return new ProvidersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProvidersAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.getPname().setText(localDataSet.get(position).getProvider_name());
        viewHolder.getPemail().setText(localDataSet.get(position).getProvider_email());
        viewHolder.getPcount().setText(localDataSet.get(position).getProvider_phone());
    }

    @Override
    public int getItemCount() {
        return localDataSet.toArray().length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView pname;
        private final TextView pemail;
        private final TextView pcount;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            pname = (TextView) view.findViewById(R.id.provider_name);
            pemail = (TextView) view.findViewById(R.id.provider_email);
            pcount = (TextView) view.findViewById(R.id.provider_productCount);
            view.setOnClickListener(this);
        }

        public TextView getPname() {
            return pname;
        }

        public TextView getPemail() {
            return pemail;
        }

        public TextView getPcount() {
            return pcount;
        }

        @Override
        public void onClick(View v) {
            Provider provider = localDataSet.get(getAdapterPosition());
            if (context != null) {
                Intent intent = new Intent(context, ProviderCreateUpdate.class);
                intent.putExtra("provider_id", provider.getProvider_id());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }
}
