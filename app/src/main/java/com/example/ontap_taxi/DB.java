package com.example.ontap_taxi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class DB extends SQLiteOpenHelper {
    private static final  String TableName = "Taxi";
    private static final  String MA = "Ma";
    private static final  String SoXe = "SoXe";
    private static final  String QuanDuong = "QuangDuong";
    private static final  String DonGia = "DongGia";
    private static final  String KhuyenMai = "khuyenMai";

    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SqlCreate = "Create table if not exists " + TableName + "(" +MA + " INTEGER PRIMARY KEY, "+ SoXe + " TEXT, " + QuanDuong + " FLOAT, " + DonGia + " FLOAT, " + KhuyenMai + " INTEGER)";
        db.execSQL(SqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TableName);
        onCreate(db);
    }
    public void AddTaxi(Taxi taxi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(MA,taxi.getMa());
        String sql = "INSERT INTO "+TableName + "("+SoXe+","+QuanDuong+"," + DonGia +","+KhuyenMai+") VALUES ("+"'"+taxi.getSoXe()+"'," + taxi.getQuangDuong()+","+ taxi.getDonGia()+","+ taxi.getKhuyenMai()+")";
//        values.put(SoXe,taxi.getSoXe());
//        values.put(QuanDuong,taxi.getQuangDuong());
//        values.put(DonGia,taxi.getDonGia());
//        values.put(KhuyenMai,taxi.getKhuyenMai());
//        db.insert(TableName,null,values);
        db.execSQL(sql);
        db.close();

    }
    public void UpdateTaxi(Taxi updateTaxi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value  = new ContentValues();
        value.put(SoXe,updateTaxi.getSoXe());
        value.put(QuanDuong,updateTaxi.getQuangDuong());
        value.put(DonGia,updateTaxi.getDonGia());
        value.put(KhuyenMai,updateTaxi.getKhuyenMai());
        db.update(TableName,value,"Ma=?",new String[]{String.valueOf(updateTaxi.getMa())});
        db.close();
    }
    public ArrayList<Taxi> getAllTaxi(){
        ArrayList<Taxi> list = new ArrayList<>();
        String SQL = "Select * from " + TableName;
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cusor = db.rawQuery(SQL,null);
        if(cusor != null){
            while (cusor.moveToNext()){
                Taxi taxi = new Taxi(cusor.getInt(0),cusor.getString(1),cusor.getFloat(2),cusor.getFloat(3),cusor.getInt(4));
                list.add(taxi);
            }
        }
        return list;

    }
    public void deleteTaxi(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TableName,MA+"=?",new String[]{String.valueOf(id)});
    }
}
