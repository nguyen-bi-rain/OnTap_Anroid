package com.example.ontap_taxi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Taxi> listTaxi;
    private Adapter adapter;

    private ListView listview;
    private DB db;
    private int itemSelected;
    private EditText txtSearch;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = findViewById(R.id.list_item);
        txtSearch = findViewById(R.id.txtSearch);

        db = new DB(this,"TaxiDB",null,1);

//        db.AddTaxi(new Taxi(1,"29D2-283.34",100,30,10));
//        db.AddTaxi(new Taxi(1,"30D2-283.34",100,30,10));
//        db.AddTaxi(new Taxi(1,"31D2-283.34",100,30,10));
//        db.AddTaxi(new Taxi(1,"32D2-283.34",100,30,10));
//        db.AddTaxi(new Taxi(1,"19D2-283.34",30,30,10));
        listTaxi = new ArrayList<>();
        listTaxi = db.getAllTaxi();
        Collections.sort(listTaxi, new Comparator<Taxi>() {
            @Override
            public int compare(Taxi item, Taxi t1) {
                String s1 = item.getSoXe();
                String s2 = t1.getSoXe();
                return s1.compareToIgnoreCase(s2);
            }

        });

        adapter = new Adapter(this,listTaxi);
        listview.setAdapter(adapter);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelected = position;
                return false;
            }
        });
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        registerForContextMenu(listview);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater flater = new MenuInflater(this);
        flater.inflate(R.menu.contextmenu,menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 201 && resultCode == 200){
            Bundle b = data.getExtras();
            int ma = b.getInt("ema");
            String xe = b.getString("esoxe");
            float duong = b.getFloat("equangduong");
            float gia = b.getFloat("edongia");
            int khuyen = b.getInt("ekhuyenmai");
            Taxi t = new Taxi(ma,xe,duong,gia,khuyen);
            db.UpdateTaxi(t);
            listTaxi.set(itemSelected,t);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Taxi t = listTaxi.get(itemSelected);
        if(item.getItemId()  == R.id.mnuSua){
            Intent i = new Intent(MainActivity.this,SuaActivity.class);
            Bundle b = new Bundle();
            b.putInt("ma",t.getMa());
            b.putString("soxe",t.getSoXe());
            b.putFloat("quangduong",t.getQuangDuong());
            b.putFloat("dongia",t.getDonGia());
            b.putInt("khuyenmai",t.getKhuyenMai());
            i.putExtras(b);
            startActivityForResult(i,201);
        }
        if(item.getItemId() == R.id.mnuXoa){
            int cnt = 0;
            ArrayList<Taxi> arr = new ArrayList<>();

            for(int i =0;i<listTaxi.size();i++){
                if(listTaxi.get(i).getTongTien() < listTaxi.get(itemSelected).getTongTien()){
                    cnt ++;
                    db.deleteTaxi(listTaxi.get(i).getMa());
                    arr.add(listTaxi.get(i));
                }
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Vu TheN Nguyen");
            builder.setMessage("Ban co muon xoa "+cnt+" hoa don < "+ listTaxi.get(itemSelected).getTongTien());

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listTaxi.removeAll(arr);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
        return super.onContextItemSelected(item);
    }
}