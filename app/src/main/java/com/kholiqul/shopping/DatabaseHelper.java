package com.kholiqul.shopping;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "shopping";
    public static final String TABLE = "transaksi";
    public static final String COLUMN_1 = "id";
    public static final String COLUMN_2 = "pembeli";
    public static final String COLUMN_3 = "barang";
    public static final String COLUMN_4 = "harga";
    public static final String COLUMN_5 = "jumlah";
    public static final String COLUMN_6 = "total";

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE+"("+COLUMN_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_2+" TEXT, "+COLUMN_3+" TEXT, "+COLUMN_4+" TEXT, "+COLUMN_5+" TEXT, "+COLUMN_6+" TEXT);";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public void insert(String pembeli, String barang, String harga, String jumlah, String total){
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("INSERT INTO "+TABLE+"(id, pembeli, barang, harga, jumlah, total)VALUES(NULL, '"+pembeli+"', '"+barang+"', '"+harga+"', '"+jumlah+"', '"+total+"')");
             }
        }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
//        String selectQuery = "SELECT *, SUM(total) as totalS FROM " + TABLE+" GROUP BY pembeli";
        String selectQuery = "SELECT * FROM " + TABLE;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_1, cursor.getString(0));
                map.put(COLUMN_2, cursor.getString(1));
                map.put(COLUMN_3, cursor.getString(2));
                map.put(COLUMN_4, cursor.getString(3));
                map.put(COLUMN_5, cursor.getString(4));
                map.put(COLUMN_6, cursor.getString(5));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        Log.e("select sqlite ", "" + wordList);

        database.close();
        return wordList;
    }

    public void delete(){
        try(SQLiteDatabase db = this.getWritableDatabase()){
            db.delete("transaksi", null,null);
        }
    }

    public ArrayList<HashMap<String, String>> getAllDataFiltered(String pembeli) {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + TABLE+" WHERE pembeli LIKE '"+pembeli+"%'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_1, cursor.getString(0));
                map.put(COLUMN_2, cursor.getString(1));
                map.put(COLUMN_3, cursor.getString(2));
                map.put(COLUMN_4, cursor.getString(3));
                map.put(COLUMN_5, cursor.getString(4));
                map.put(COLUMN_6, cursor.getString(5));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        Log.e("select sqlite ", "" + wordList);

        database.close();
        return wordList;
    }

    public void deleteOne(int id){
        try(SQLiteDatabase db = this.getWritableDatabase()){
            db.delete("transaksi", "id="+id,null);
        }
    }
}

