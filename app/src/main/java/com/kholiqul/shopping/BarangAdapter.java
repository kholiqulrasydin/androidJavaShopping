package com.kholiqul.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class BarangAdapter extends BaseAdapter {
    private String[] Hewan;
    private int[] gambar;
    private String[] Harga;
    private LayoutInflater inf;

    BarangAdapter(Context context, String[] Hewan, int[] gambar, String[] Harga){
        this.Hewan = Hewan;
        this.gambar = gambar;
        this.Harga = Harga;
        inf= (LayoutInflater.from(context)) ;

    }


    @Override
    public int getCount() {
        return Hewan.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = inf.inflate(R.layout.hewan, parent, false);
            ImageView icon = convertView.findViewById(R.id.icon);
            TextView namabarang = convertView.findViewById(R.id.namabarang);
            TextView ha = convertView.findViewById(R.id.harga);

            icon.setImageResource(gambar[position]);
            String nama = Hewan[position];
            String h = Harga[position];
            namabarang.setText(String.format("Nama Hewan : %s", nama));
            ha.setText(String.format("Harga Hewan : %s", h));
        }
        return convertView;
    }
}
