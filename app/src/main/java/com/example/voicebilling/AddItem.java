package com.example.voicebilling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.voicebilling.Adapters.ItemsAdpater;

import java.util.ArrayList;

public class AddItem extends AppCompatActivity {
    EditText ProductName, ProductPrice;
    Button AddItem;
    myDbAdapter dbAdapter;
    ArrayList<String> items = new ArrayList<>();
    ItemsAdpater itemsAdpater;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ProductName  = findViewById(R.id.ProductName);
        ProductPrice = findViewById(R.id.ProductPrice);
        AddItem = findViewById(R.id.AddItem);

        recyclerView = findViewById(R.id.itemView);
        dbAdapter = new myDbAdapter(this);
        getItems();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsAdpater = new ItemsAdpater(this);
        recyclerView.setAdapter(itemsAdpater);


        AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
                itemsAdpater = new ItemsAdpater(AddItem.this);
                recyclerView.setAdapter(itemsAdpater);
                itemsAdpater.notifyDataSetChanged();
            }
        });

    }
    public void addUser()
    {
        String t1 = ProductName.getText().toString();
        String t2 = ProductPrice.getText().toString();
        if(t1.isEmpty() || t2.isEmpty())
        {
            Log.d("Error","There are input filleds are empty");
        }
        else
        {
            long id = dbAdapter.insertData(t1,t2);
            if(id<=0)
            {
                Log.d("Error","Insertion unsuccessfull");
                ProductName.setText("");
                ProductPrice.setText("");
            } else
            {
                Log.d("Error","Insertion successfull");
                ProductName.setText("");
                ProductPrice.setText("");
//                viewdata();
            }
        }
    }

    public void getItems(){

        ArrayList<String> data = dbAdapter.getData();
        items = data;
        Log.d("AllData", String.valueOf(data.size()));


    }


}