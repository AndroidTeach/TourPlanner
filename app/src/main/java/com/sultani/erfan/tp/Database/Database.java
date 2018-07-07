package com.sultani.erfan.tp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.sultani.erfan.tp.Model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sultani on 10/25/2017.
 */

public class Database extends SQLiteAssetHelper  {
      private static final String DB_NAME = "tbBD.db";
      private static final int DB_VER=1;

    public Database(Context context) {
        super(context, DB_NAME, null, 1);
    }


    public List<Order> getCarts(){


        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"PackageID","PackageName","Quantity","Price","Discount","Days"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor c =qb.query(db,sqlSelect,null,null,null,null,null,null);

        final List<Order> result = new ArrayList<>();
        if(c.moveToFirst())
        {
            do {
                result.add(new Order(c.getString(c.getColumnIndex("PackageID")),
                        c.getString(c.getColumnIndex("PackageName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount")),
                        c.getString(c.getColumnIndex("Days"))
                ));
            }
            while (c.moveToNext());

        }
                return result;

    }

    public void addToCart(Order order){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(PackageID,PackageName,Quantity,Price,Discount,Days) VALUES ('%s','%s','%s','%s','%s','%s');",
        order.getPackageID(),
                order.getPackageName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount(),
                order.getDays());

        db.execSQL(query);


        System.out.println(order.getPackageID());
        System.out.println(order.getPackageName());
        System.out.println(order.getQuantity());
        System.out.println(order.getPrice());
        System.out.println(order.getDays());


    }

   public void cleanCart(){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail ");
        db.execSQL(query);

    }


}
