package com.example.lab_a1_a2_android_rakesh_812169.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_a1_a2_android_rakesh_812169.ProductCreateUpdate;
import com.example.lab_a1_a2_android_rakesh_812169.R;
import com.example.lab_a1_a2_android_rakesh_812169.database.model.Product;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private List<Product> localDataSet;
    private Context context;
    private View.OnClickListener onClickListener;

    public ProductsAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ProductsAdapter(Context context, List data) {
        this.localDataSet = data;
        this.context = context;
    }

    public ProductsAdapter() {
    }

    public void setData(List data) {
        localDataSet = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.products_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        vh.getPname().setText(localDataSet.get(position).getProduct_name());
        vh.getPdesc().setText(localDataSet.get(position).getProduct_description());
        vh.getPprice().setText(localDataSet.get(position).getProduct_price() + " $CAD");
    }

    @Override
    public int getItemCount() {
        return localDataSet.toArray().length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView pname;
        private final TextView pdesc;
        private final TextView pprice;

        public ViewHolder(View view) {
            super(view);
            pname = (TextView) view.findViewById(R.id.product_name);
            pdesc = (TextView) view.findViewById(R.id.product_description);
            pprice = (TextView) view.findViewById(R.id.product_price);
            view.setOnClickListener(this);
        }

        public TextView getPname() {
            return pname;
        }

        public TextView getPdesc() {
            return pdesc;
        }

        public TextView getPprice() {
            return pprice;
        }

        @Override
        public void onClick(View v) {
            Product product = localDataSet.get(getAdapterPosition());
            if (context != null) {
                Intent intent = new Intent(context, ProductCreateUpdate.class);
                intent.putExtra("product_id", product.getProduct_id());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        }
    }


}

