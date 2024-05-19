package com.example.ontap_taxi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter implements Filterable {
    private Activity activity;
    private ArrayList<Taxi> data;
    private LayoutInflater inflater;
    private ArrayList<Taxi> databackup;

    public Adapter(Activity activity,ArrayList<Taxi> data){
        this.activity = activity;
        this.data = data;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getMa();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = inflater.inflate(R.layout.list_taxi_item,null);
        }
        TextView soxe = v.findViewById(R.id.txtSoxe);
        soxe.setText(data.get(position).getSoXe());
        TextView  quangduong =v.findViewById(R.id.txtQuangDuong);
        quangduong.setText("Quang Duong " + String.valueOf(data.get(position).getQuangDuong()));
        TextView  tongtien =v.findViewById(R.id.txtTongTien);
        float tien = data.get(position).getDonGia() * data.get(position).getQuangDuong() * (100 - data.get(position).getKhuyenMai())/100;
        tongtien.setText(String.valueOf(tien));
        return v;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults fres = new FilterResults();
                if(databackup == null){
                    databackup = new ArrayList<>(data);

                }
                if(constraint == null || constraint.length() == 0){
                    fres.count = databackup.size();
                    fres.values = databackup;

                }else{
                    ArrayList<Taxi> newdata = new ArrayList<>();
                    for(Taxi t : databackup){
                        if(Float.parseFloat(constraint.toString()) < t.getTongTien()){
                            newdata.add(t);
                        }
                    }
                    fres.values = newdata;
                    fres.count = newdata.size();
                }
                return fres;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = new ArrayList<Taxi>();
                ArrayList<Taxi> tmp = (ArrayList<Taxi>)  results.values;
                data.addAll(tmp);
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
