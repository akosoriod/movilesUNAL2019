package com.example.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;


public class DatabaseHelper {
    SQLiteDatabase cx;
    ArrayList<Company> lista= new ArrayList<Company>();
    Company c;
    Context ct;
    String DBname = "Company.db";
    String tabla = "create table if not exists company(id integer primary key autoincrement, name text, webpage text, phone integer, email text, detail text,type text)";

    public DatabaseHelper(Context c){
        this.ct =c;
        cx = c.openOrCreateDatabase(DBname, c.MODE_PRIVATE,null);
        cx.execSQL(tabla);
    }

    public boolean insertar(Company c){
        ContentValues contenedor=new ContentValues();
        contenedor.put("name",c.getName());
        contenedor.put("webpage",c.getWebpage());
        contenedor.put("email",c.getEmail());
        contenedor.put("phone",c.getPhone());
        contenedor.put("detail",c.getDetaill());
        contenedor.put("type",c.getType());
        return (cx.insert("company",null,contenedor))>0;
    }
    public boolean eliminar(int id){
        return (cx.delete("company","id="+id,null))>0;
    }
    public boolean editar(Company c){
        ContentValues contenedor=new ContentValues();
        contenedor.put("name",c.getName());
        contenedor.put("webpage",c.getWebpage());
        contenedor.put("email",c.getEmail());
        contenedor.put("phone",c.getPhone());
        contenedor.put("detail",c.getDetaill());
        contenedor.put("type",c.getType());
        return (cx.update("company",contenedor,"id="+c.getId(),null ))>0;
    }
    public ArrayList<Company> verTodos(){
        lista.clear();
        Cursor cursor=cx.rawQuery("select * from company",null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                lista.add(new Company(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)));
            }while (cursor.moveToNext());
        }
        return lista;
    }
    public Company verUno(int posicion){
        Cursor cursor=cx.rawQuery("select * from company",null);
        cursor.moveToPosition(posicion);
        c=new Company(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
        return c;
    }
}
