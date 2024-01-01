package com.example.voicebilling.Adapters;

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

import com.example.voicebilling.Product;
import com.example.voicebilling.R;
import com.example.voicebilling.myDbAdapter;

import java.util.ArrayList;

public class ItemsAdpater extends RecyclerView.Adapter<ItemsAdpater.ViewHolder> {

    ArrayList<String> items;
    Context con;
    myDbAdapter dbAdapter;
    public ItemsAdpater(Context con){

        this.con = con;
        dbAdapter = new myDbAdapter(con);
        items = dbAdapter.getData();
        notifyDataSetChanged();
        Log.d("size", String.valueOf(items.size()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singleitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String row = items.get(position);

        String[] arr = row.split(",");


        Log.d("Checkdata", String.valueOf(arr[0]));
        holder.ProductName.setText(arr[1]);
        holder.ProductPrice.setText(arr[2]+".00 / unit");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapter.delete(arr[1]);
                items.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ProductName,ProductPrice;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.itemName);
            ProductPrice = itemView.findViewById(R.id.itemPrice);
            delete = itemView.findViewById(R.id.delete);

        }
    }
}
