package com.example.voicebilling;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {


    myDbAdapter myhelper;
    ImageButton add_btn;
    RecyclerView rview;
    ProductAdapter productAdapter;
    EditText name,price;
    ImageButton CreateBill;
    TextView total;
    float totalBill = 0;
    ArrayList<Product> product_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateBill = findViewById(R.id.createbill);
        rview = findViewById(R.id.rview);
        total = findViewById(R.id.total);
//        name = findViewById(R.id.get_product_name);
//        price = findViewById(R.id.get_product_price);
        add_btn =  findViewById(R.id.add_item);

        myhelper = new myDbAdapter(this);
        //Create object of adapter
        productAdapter = new ProductAdapter(myhelper,product_list);
        //Set that adapter object to recycler view
        rview.setAdapter(productAdapter);
        //Set Layout to recycler view horizontal or vertical
        rview.setLayoutManager(new LinearLayoutManager(this));


        //Button to add new item
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add data
                    Intent intent = new Intent(MainActivity.this,AddItem.class);
                    startActivity(intent);
                    totalBill = 0;
//                addUser();
//                myhelper.getItemData(String.valueOf(name.getText()));
            }
        });

        //Create bill from item list
        CreateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> hashMap = new HashMap<>();
                for (Product p:product_list){
                    hashMap.put(p.name, String.valueOf(p.Price));
                }
                Intent intent = new Intent(MainActivity.this,GenerateBill.class);
                intent.putExtra("map",hashMap);
                startActivity(intent);


            }
        });

    }
    // for add item
    public void addUser()
    {
        String t1 = name.getText().toString();
        String t2 = price.getText().toString();
        if(t1.isEmpty() || t2.isEmpty())
        {
            Log.d("Error","There are input filleds are empty");
        }
        else
        {

            long id = myhelper.insertData(t1,t2);
            if(id<=0)
            {
                Log.d("Error","Insertion unsuccessfull");
                name.setText("");
                price.setText("");
            } else
            {
                Log.d("Error","Insertion successfull");
                name.setText("");
                price.setText("");
                viewdata();
            }
        }
    }

    public void viewdata(){
        //Fetch data from database
//        String data = myhelper.getData();
//        Log.d("Get Data Price",data);
    }

    //To access mic
    public void speak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
        startActivityForResult(intent, 100);
    }



    public  void seperateData(String data){
        String unit = "kg";
        String[] product_arr = null;
      if (data.contains("kg")){
          product_arr = data.split("kg");
          unit = "kg";

      }else if(data.contains("litre")){
          product_arr = data.split("litre");
          unit = "litre";

      }else if(data.contains("packet")){
          product_arr = data.split("packet");
          unit = "packet";

      }
      //किलो
      else if(data.contains("किलो")){
          product_arr = data.split("किलो");
          unit = "किलो";
         
      }
        //लीटर
      else if(data.contains("लीटर")){
          product_arr = data.split("लीटर");
          unit = "लीटर";

      }
      //पैकेट
      else if(data.contains("पैकेट")){
          product_arr = data.split("पैकेट");
          unit = "पैकेट";

      }
      //नग
      else if(data.contains("नग")){
          product_arr = data.split("नग");
          unit = "नग";

      }
        float price;
        for(String i : product_arr){
            String[] part = i.split("(?<=\\D)(?=\\d)");
            try {
                price = Float.parseFloat(myhelper.getItemData(part[0].substring(0,part[0].length()-1)));
            }catch (NullPointerException e){
                return;
            }
            Float t = Float.parseFloat(part[1])*price;
            totalBill+=t;
            Product product = new Product(part[0],t,unit,part[1]);
            product_list.add(product);
            productAdapter.notifyDataSetChanged();

        }
        total.setText("Total:"+String.valueOf(totalBill));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK){
            String speech_data = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            Log.d("Input",speech_data);
            seperateData(speech_data);


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        totalBill = 0;
    }
}