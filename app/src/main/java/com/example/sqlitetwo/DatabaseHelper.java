package com.example.sqlitetwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE__NAME = "students.db";
    public static final String TABLE_NAME = "students_table";
    public static  final String COL_1 = "ID";
    public static  final String COL_2 = "name";
    public static  final String COL_3 = "surname";
    public static  final String COL_4 = "marks";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE__NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME TEXT , SURNAME TEXT , MARKS INTEGER)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     //db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
     onCreate(db);
    }

    public boolean insertData(String name, String surname, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4,marks);
        long result =  db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;


    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_NAME, null);
        return res;
    }
    public boolean updateData(String id,String name, String surname, String marks){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME,contentValues,"ID = ?",new String[]{id});
        return true;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{id});
    }

}
