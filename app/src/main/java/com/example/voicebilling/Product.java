package com.example.voicebilling;

public class Product {
    Float Price;
    String name;
    String unit;
    String u_unit;

    Product(String name, Float Price, String unit,String u_unit){

        this.name = name;
        this.Price  = Price;
        this.unit = unit;
        this.u_unit= u_unit;
    }

}
