package com.kholiqul.shopping;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TransaksiAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private int[] gambarHewan;
    private String[] namaHewan;
    private List<TransaksiModel> items;

    TransaksiAdapter(Activity activity, List<TransaksiModel> items, int[] gambarHewan, String[] namaHewan){
        this.activity = activity;
        this.items = items;
        this.gambarHewan = gambarHewan;
        this.namaHewan = namaHewan;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null) {
            assert inflater != null;
            convertView = inflater.inflate(R.layout.transaksi, parent, false);
        }

            ImageView gambar = convertView.findViewById(R.id.gambarHewan);
            TextView nama = convertView.findViewById(R.id.pembeli);
            TextView hewan = convertView.findViewById(R.id.namaHewan);
            TextView harga = convertView.findViewById(R.id.hargahewan);
            TextView jumlahbeli = convertView.findViewById(R.id.jumlahbeli);
            TextView totalbayar = convertView.findViewById(R.id.totalbayar);

            TransaksiModel transaksiModel = items.get(position);

            gambar.setImageResource(gambarHewan[Integer.parseInt(transaksiModel.getBarang())]);
            hewan.setText(String.format("Nama Hewan : %s", namaHewan[Integer.parseInt(transaksiModel.getBarang())]));
            nama.setText(transaksiModel.getPembeli());
            harga.setText(String.format("Harga Hewan : %s", transaksiModel.getHarga()));
            jumlahbeli.setText(String.format("Jumlah Beli : %s", transaksiModel.getJumlah()));
            totalbayar.setText(String.format("Total Bayar : %s", transaksiModel.getTotal()));

        return convertView;
    }
}
