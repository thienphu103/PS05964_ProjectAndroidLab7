package com.example.a.lab7;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by A on 7/19/2017.
 */

public class SQLite extends SQLiteOpenHelper {
    private static final String TABLE = "BOOK";
    private static final String KEY_ID = "ID";
    private static final String KEY_TILTE = "TILTE";
    private static final String KEY_AUTHOR = "AUTHOR";
    private static final String KEY_PRICE = "PRICE";
    ArrayList<ClassBook> arrayList = new ArrayList<>();

    public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE + "'");
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +// AUTOINCREMENT
                KEY_TILTE + " VARCHAR," +
                KEY_AUTHOR + " VARCHAR," +
                KEY_PRICE + " VARCHAR)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
    }

    public ArrayList<ClassBook> getDataBook(String name) {
        arrayList.clear();
      String sql= "SELECT * FROM " + TABLE + "";
        Cursor cursor = GetData(sql);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tilte = cursor.getString(1);
            String author = cursor.getString(2);
            String price = cursor.getString(3);
            arrayList.add(new ClassBook(id, tilte, author, price));
        }

        return arrayList;
    }

    public ArrayList<ClassBook> getSearchBook(String name) {
        arrayList.clear();
        String sql= "SELECT * FROM " + TABLE + " WHERE " + KEY_TILTE + " LIKE '%"+name+"%'";;
        Cursor cursor = GetData(sql);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tilte = cursor.getString(1);
            String author = cursor.getString(2);
            String price = cursor.getString(3);
            arrayList.add(new ClassBook(id, tilte, author, price));
        }

        return arrayList;
    }

//    public String getBook() {
//        String Getbook = "SELECT * FROM " + TABLE + "";
//        return Getbook;
//    }

    public String deleteBook(String id) {
        String Deletebook = "DELETE FROM " + TABLE + " WHERE " + KEY_ID + "='" + id + "'";//Detete row name & id
        return Deletebook;
    }

    public String addBook(int id, String tilte, String author, String price) {
        String AddData = "INSERT INTO " + TABLE + " VALUES(" +
                "'" + id + "'," +
                "'" + tilte + "'," +
                "'" + author + "'," +
                "'" + price + "')";
//        String AddData = "INSERT INTO " + TABLE + " VALUES('" + classBook.id + "','" + classBook.tilte + "','" + classBook.author + "','" + classBook.price + "')";
        return AddData;
    }

    public String updateBook(int id, String tilte, String author, String price, String idup) {
//        String UpdateData="UPDATE " +TABLE+"   VALUES ('"+id+"', '"+tilte+"', '"+author+"','"+price+"'), WHERE " + KEY_ID + "='" + idup + "'";
        String UpdateData = "UPDATE " + TABLE + " SET " + KEY_ID + "='" + id + "'" +
                " , " + KEY_TILTE + "='" + tilte + "'" +
                " , " + KEY_AUTHOR + "='" + author + "'" +
                " , " + KEY_PRICE + "='" + price + "'" +
                " WHERE ID='" + idup + "'";//Update
        return UpdateData;
    }


}