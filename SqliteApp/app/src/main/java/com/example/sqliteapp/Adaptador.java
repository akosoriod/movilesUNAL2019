package com.example.sqliteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    ArrayList<Company> lista;
    DatabaseHelper dao;
    Company c;
    Activity a;
    int id=0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Adaptador(Activity a, ArrayList<Company> lista, DatabaseHelper dao) {
        this.lista = lista;
        this.a = a;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Company getItem(int i) {
        c=lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        c=lista.get(i);
        return c.getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v= view;
        if(v==null){
            LayoutInflater li=(LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=li.inflate(R.layout.item,null);
        }
        c=lista.get(posicion);
        TextView name=(TextView)v.findViewById(R.id.t_name);
        TextView webpage=(TextView)v.findViewById(R.id.t_webpage);
        TextView email=(TextView)v.findViewById(R.id.t_email);
        TextView phone=(TextView)v.findViewById(R.id.t_phone);
        TextView detail=(TextView)v.findViewById(R.id.t_detail);
        TextView type=(TextView)v.findViewById(R.id.t_type);
        Button editar=(Button)v.findViewById(R.id.editar);
        Button eliminar=(Button)v.findViewById(R.id.eliminar);
        name.setText(c.getName());
        webpage.setText(c.getWebpage());
        email.setText(c.getEmail());
        phone.setText(c.getPhone());
        detail.setText(c.getDetaill());
        type.setText(c.getType());
        editar.setTag(posicion);
        eliminar.setTag(posicion);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=Integer.parseInt(view.getTag().toString());
                final Dialog dialogo= new Dialog(a);
                dialogo.setTitle("Editar registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                final EditText name = (EditText)dialogo.findViewById(R.id.name);
                final EditText webpage = (EditText)dialogo.findViewById(R.id.webpage);
                final EditText email = (EditText)dialogo.findViewById(R.id.email);
                final EditText phone = (EditText)dialogo.findViewById(R.id.phone);
                final EditText detail = (EditText)dialogo.findViewById(R.id.detail);
                final EditText type = (EditText)dialogo.findViewById(R.id.type);
                Button guardar = (Button)dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Guardar");
                Button cancelar = (Button)dialogo.findViewById(R.id.d_cancelar);
                c=lista.get(pos);
                setId(c.getId());
                name.setText(c.getName());
                webpage.setText(c.getWebpage());
                email.setText(c.getEmail());
                phone.setText(c.getPhone());
                detail.setText(c.getDetaill());
                type.setText(c.getType());
                guardar.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        try{
                            c=new Company(getId(),name.getText().toString(),
                                    webpage.getText().toString(),
                                    email.getText().toString(),
                                    phone.getText().toString(),
                                    detail.getText().toString(),
                                    type.getText().toString());
                            dao.editar(c);
                            lista=dao.verTodos();
                            notifyDataSetChanged();
                            dialogo.dismiss();
                        }catch (Exception e){
                            Toast.makeText(a,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=Integer.parseInt(view.getTag().toString());
                c=lista.get(pos);
                setId(c.getId());
                AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("¿Estas seguro de borrar esta empresa?");
                del.setCancelable(false);
                del.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dao.eliminar(getId());
                        lista=dao.verTodos();
                        notifyDataSetChanged();
                    }
                });
                del.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                del.show();
            }
        });
        return v;

    }
}
