package com.example.voicebilling;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<Product> product_list = new ArrayList<>();
    Context context;
    myDbAdapter myhelper;
    public ProductAdapter(myDbAdapter context, ArrayList<Product> product_list) {
        this.product_list = product_list;
        myhelper = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = product_list.get(position);
        holder.product_name.setText(product.name);

        holder.u_unit.setText(String.valueOf(product.u_unit));
        holder.product_price.setText(String.valueOf(product.Price));
        holder.product_unit.setText(" / "+product.unit);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myhelper.delete(product.name);
                product_list.remove(position);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,product_price,product_unit,u_unit;
        ImageButton delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.textView);
            product_price = itemView.findViewById(R.id.textView2);
            product_unit = itemView.findViewById(R.id.textView3);
            delete = itemView.findViewById(R.id.delete);
            u_unit=itemView.findViewById(R.id.u_unit);

        }
    }
}
