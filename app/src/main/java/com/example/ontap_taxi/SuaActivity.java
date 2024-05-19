package com.example.ontap_taxi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class SuaActivity extends AppCompatActivity {
    private EditText txtSoxe;
    private EditText txtQuang;
    private EditText txtDonGia;
    private EditText txtKhuyeMai;
    private Button btnSua;
    private Button btnExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.addactivity);
        super.onCreate(savedInstanceState);
        txtSoxe = findViewById(R.id.SoXe);
        txtQuang = findViewById(R.id.QuangDuong);
        txtDonGia = findViewById(R.id.DonGia);
        txtKhuyeMai = findViewById(R.id.KhuyenMai);
        btnExit = findViewById(R.id.btnExit);
        btnSua = findViewById(R.id.btnSua);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b != null){
            txtSoxe.setText(b.getString("soxe"));
            txtQuang.setText(String.valueOf(b.getFloat("quangduong")));
            txtDonGia.setText(String.valueOf(b.getFloat("dongia")));
            txtKhuyeMai.setText(String.valueOf(b.getInt("khuyenmai")));
        }
        btnSua.setOnClickListener( v ->{
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("esoxe",txtSoxe.getText().toString());
            bundle.putFloat("equangduong",Float.parseFloat(txtQuang.getText().toString()));
            bundle.putFloat("edongia",Float.parseFloat(txtDonGia.getText().toString()));
            bundle.putInt("ekhuyenmai",Integer.parseInt(txtKhuyeMai.getText().toString()));
            bundle.putInt("ema",b.getInt("ma"));
            intent.putExtras(bundle);
            setResult(200,intent);
            finish();
        });
        btnExit.setOnClickListener(v ->{
            finish();
        });
    }
}