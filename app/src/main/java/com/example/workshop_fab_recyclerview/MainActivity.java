package com.example.workshop_fab_recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder dialog;
    LayoutInflater inflater;

    ArrayList<Siswa> siswas = new ArrayList<>();

    EditText etNama, etInstansi; //definisi widget

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tarik layout kedalam dialog
                dialog = new AlertDialog.Builder(MainActivity.this);

                //tarik!
                inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.form_input, null);

                //definisi widget
                etNama = (EditText) view.findViewById(R.id.et_nama);
                etInstansi = (EditText) view.findViewById(R.id.et_instansi);

                //pengaturan dialog
                dialog.setView(view);
                dialog.setCancelable(true);

                //atur tombol
                dialog.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //tangkap data dari widget
                        String nama, instansi;

                        nama = etNama.getText().toString();
                        instansi = etInstansi.getText().toString();

                        //simpan data kedalaman arraylist
                        siswas.add(new Siswa(nama, instansi));

                        Toast.makeText(getBaseContext(),
                                "Data " + nama + " berhasil disimpan",
                                Toast.LENGTH_SHORT).show();

                        saveArrayList(getApplicationContext(), siswas, "list_siswa");
                        showRecyclerView();
                    }
                });

                dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.show();

            }
        });

        showRecyclerView();
    }

    void showRecyclerView(){
        //definisi
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_container);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        //masukkan data kedalam adapter
        RvAdapter rvAdapter = new RvAdapter(this);
        rvAdapter.setSiswas(siswas);

        //set recyclerview dengan adapter
        rv.setAdapter(rvAdapter);
    }

    //-------------- URUSAN SHARED PREFERENCES
    //alt + ins --> overide methods
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //simpan share preferences
        saveArrayList(getApplicationContext(), siswas, "list_siswa");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //restore data ke array list
        if(siswas.size() == 0){
            siswas = getArrayList("list_siswa");
        }

        showRecyclerView();
    }

    //konversi array list ke string dan menyimpan pada Shared Preferneces
    static void saveArrayList(Context context, ArrayList<Siswa> list, String key){
        //definisi shared preferences
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();

        //definisi GSON
        Gson gson = new Gson();
        String s = gson.toJson(list);//konversi list ke string

        //simpan kedalam shared preferences
        ed.putString(key, s);
        ed.apply();
    }

    //mengembalikan nilai shared preferences dan meletakkan kembali pd ArrayList
    ArrayList<Siswa> getArrayList(String key){
        //definisi shared preferences
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        //definisi GSON
        Gson gson = new Gson();
        String s = sp.getString(key, null);//konversi list ke string

        //restore String ke tipe ArrayList
        Type type = new TypeToken<ArrayList<Siswa>>(){}.getType();
        return gson.fromJson(s, type);
    }
}
