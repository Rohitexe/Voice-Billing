package com.example.voicebilling;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class myDbAdapter {
    myDbHelper myhelper;

    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String name, String pass)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.PRODUCT_NAME, name.toLowerCase());
        contentValues.put(myDbHelper.PRODUCT_PRICE, pass.toLowerCase());
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getItemData(String name){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String query = "SELECT price FROM myTable WHERE Name = '" +name.toLowerCase()+"'";
        Log.d("getdataprice",query);
        Cursor cursor = db.rawQuery(query,null);
        try{

            if (cursor != null) {
                cursor.moveToFirst();
                Log.d("Getdataprice", String.valueOf(cursor));
                String s1 = cursor.getString(0);
                return s1;
            }
        }catch (IndexOutOfBoundsException e){
            Log.d("Error","Item does not exist");
            return null;

        }
        return  null;
    }

    public ArrayList<String> getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.PRODUCT_ID,myDbHelper.PRODUCT_NAME,myDbHelper.PRODUCT_PRICE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        ArrayList<String> buffer = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.PRODUCT_ID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.PRODUCT_NAME));
            String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.PRODUCT_PRICE));
            buffer.add(cid+"," + name + "," + password );
        }
        Log.d("chekitems",buffer.toString());
        return buffer;
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname.toLowerCase()};


        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.PRODUCT_NAME+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.PRODUCT_NAME,newName);
        String[] whereArgs= {oldName};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.PRODUCT_NAME+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;  // Database Version
        private static final String PRODUCT_ID="id";     // Column I (Primary Key)
        private static final String PRODUCT_NAME = "Name";    //Column II
        private static final String PRODUCT_PRICE= "Price";    // Column III
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+PRODUCT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+PRODUCT_NAME+" VARCHAR(255) ,"+ PRODUCT_PRICE+" VARCHAR(225));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Log.d("Error", String.valueOf(e));
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                 Log.d("Error", String.valueOf("Upgrading"));

                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Log.d("Error", String.valueOf(e));
            }
        }
    }
}
