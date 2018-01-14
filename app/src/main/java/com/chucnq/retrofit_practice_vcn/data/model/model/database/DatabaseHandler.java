package com.chucnq.retrofit_practice_vcn.data.model.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.chucnq.retrofit_practice_vcn.data.model.model.database.ProductContract.ProductEntry;
import com.chucnq.retrofit_practice_vcn.data.model.model.model.Product;

import java.util.ArrayList;

/**
 * Created by User on 1/14/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "product.db";
    public static final int DATABASE_VERSION = 4;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "create table " + ProductEntry.TABLE_NAME + "("
                + ProductEntry._ID + " integer primary key autoincrement,"
                + ProductEntry.COLUMN_NAME + " text not null,"
                + ProductEntry.COLUMN_PRICE + " real not null);";

        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertProduct(Product pro){

        SQLiteDatabase db = this.getWritableDatabase();
        boolean kq = false;

        try {
            ContentValues values = new ContentValues();
            values.put(ProductEntry.COLUMN_NAME,pro.getName());
            values.put(ProductEntry.COLUMN_PRICE,String.valueOf(pro.getPrice()));

            db.insert(ProductEntry.TABLE_NAME,null,values);
            kq = true;
        }catch (Exception e){
            e.printStackTrace();
            kq = false;
        }

        return kq;
    }

    public ArrayList<Product> getAllProduct(){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Product> products = new ArrayList<>();

        String selectQuery = "select * from " + ProductEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){

            do {
                Product pro = new Product();
                pro.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(ProductEntry._ID))));
                pro.setName(cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_NAME)));
                pro.setPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRICE))));

                products.add(pro);

            }while (cursor.moveToNext());

        }

        return products;
    }

}
