package com.example.ibrokhimmovlonov.androidapplication.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.ibrokhimmovlonov.androidapplication.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "android_database.db";
    private static final int DB_VER = 1;


    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts() {

        SQLiteDatabase database = getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        String sqlTABLE = "OrderInfo";
        String[] sqlSELECT={"ProductName","ProductId", "Quantity", "Price", "Discount"};

        sqLiteQueryBuilder.setTables(sqlTABLE);
        Cursor cursor = sqLiteQueryBuilder.query(database, sqlSELECT, null, null, null, null,null);

        final List<Order> result = new ArrayList<>();
        if (cursor.moveToFirst()) {

            do {
                result.add(new Order(cursor.getString(cursor.getColumnIndex("ProductId")),
                        cursor.getString(cursor.getColumnIndex("ProductName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("Price")),
                        cursor.getString(cursor.getColumnIndex("Discount"))
                ));

            }while (cursor.moveToNext());

        }
        return result;

    }

    public void addInfoToCart(Order order) {

        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("INSERT INTO OrderInfo(ProductId,ProductName,Quantity,Price,Discount) VALUES ('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount());
        database.execSQL(query);

    }

    public void cleanCart() {

        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("DELETE FROM OrderInfo");
        database.execSQL(query);

    }


    // Favorites
    public void addInfoToFavorites(String foodId) {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("INSERT INTO Favorites(FoodId) VALUES('%s');",foodId);
        database.execSQL(query);
    }

    public void removeInfoFromFavorites(String foodId) {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("DELETE FROM Favorites WHERE FoodId='%s';",foodId);
        database.execSQL(query);
    }

    public boolean isFavorite(String foodId) {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("SELECT* FROM Favorites WHERE FoodId='%s';",foodId);

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
