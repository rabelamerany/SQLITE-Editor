package com.example.lenovo.androidmyadmin.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.lenovo.androidmyadmin.model.User;

import java.util.ArrayList;

public class UserSQLiteHelper extends SQLiteOpenHelper {

    public UserSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void queryData(String sql )
    {
        SQLiteDatabase database =getWritableDatabase();
        database.execSQL(sql);
    }
    //insertData
    public void insertData(String email,String password,String type)
    {
        SQLiteDatabase database =getWritableDatabase();
        //query to insert record in database  table
        String sql = "INSERT INTO user VALUES(NULL,?,?,?)";//where"RECORD" is table name in db we will create in classes
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,email);
        statement.bindString(2,password);
        statement.bindString(3,type);
        statement.executeInsert();

    }
    //updateData
    public void updateData(String email,String password,int id,String type){
        SQLiteDatabase database = getWritableDatabase();
        //query to update
        String sql="UPDATE user SET email=?, password=? ,type=? WHERE id=?";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,email);
        statement.bindString(2,password);
        statement.bindDouble(3,(double)id);
        statement.bindString(4,type);
        statement.execute();
        database.close();

    }
    public void deleteD(String email)
    {
        SQLiteDatabase database =getWritableDatabase();
        //query to delete record using id
        String sql="DELETE FROM user WHERE email=? ;";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.execute();
        database.close();;
    }
    //deleteData
    public void deleteData(int id)
    {
       SQLiteDatabase database =getWritableDatabase();
       //query to delete record using id
       String sql="DELETE FROM user WHERE id=?";
       SQLiteStatement statement=database.compileStatement(sql);
       statement.bindDouble(1,(double)id);
       statement.execute();
       database.close();;
    }
    public ArrayList<User> getData(String sql)
    {
        SQLiteDatabase database=getReadableDatabase();

          Cursor c =database.rawQuery(sql,null);
          ArrayList<User> a = new ArrayList<User>();
        int count = 0;

        if (c.moveToFirst())
        {
            while(!c.isAfterLast())
            {
                //Model m = new Model(c.getInt(0), c.getString(1),c.getString(2), c.getBlob(3), c.getString(4));
                //a.add(m);
                count++;
                c.moveToNext();
            }
            /*for(int i = 0; i < 5 ; i++)
            {
                a.add(c.getString(i));
            }*/
        }
        System.err.println("count is : " + count);
        return a;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
