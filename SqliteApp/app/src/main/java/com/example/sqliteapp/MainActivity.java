package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dao;
    Adaptador adapter;
    ArrayList<Company> lista;
    Company c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new DatabaseHelper(MainActivity.this);
        lista=dao.verTodos();
        adapter=new Adaptador(this,lista,dao);
        ListView list=(ListView)findViewById(R.id.lista);
        Button agregar=(Button)findViewById(R.id.agregar);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //vista.xml
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialogo de agregar dialogo.xml
                final Dialog dialogo= new Dialog(MainActivity.this);
                dialogo.setTitle("Nuevo registro");
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
                guardar.setText("Agregar");
                Button cancelar = (Button)dialogo.findViewById(R.id.d_cancelar);
                guardar.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        try{
                              c=new Company(name.getText().toString(),
                                    webpage.getText().toString(),
                                    email.getText().toString(),
                                    phone.getText().toString(),
                                    detail.getText().toString(),
                                    type.getText().toString());
                            dao.insertar(c);
                            lista=dao.verTodos();
                            adapter.notifyDataSetChanged();
                            dialogo.dismiss();
                        }catch (Exception e){
                            Toast.makeText(getApplication(),"Error",Toast.LENGTH_SHORT).show();
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
    }
}
