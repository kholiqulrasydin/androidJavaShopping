package com.kholiqul.shopping;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class keranjang extends AppCompatActivity {

    ListView cart;
    Button clear;
    TextView TotalTransaksi;
    EditText cari;

    int[] gambarHewan;
    String[] namaHewan, harga;
    int estimasi = 0;

    AlertDialog.Builder dialog;

    TransaksiAdapter adapter;
    List<TransaksiModel> itemList = new ArrayList<>();
    DatabaseHelper db = new DatabaseHelper(this);

    public static final String COLUMN_1 = "id";
    public static final String COLUMN_2 = "pembeli";
    public static final String COLUMN_3 = "barang";
    public static final String COLUMN_4 = "harga";
    public static final String COLUMN_5 = "jumlah";
    public static final String COLUMN_6 = "total";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        cart = findViewById(R.id.keranjang);
        clear = findViewById(R.id.clear);
        TotalTransaksi = findViewById(R.id.total);
        cari = findViewById(R.id.cari);

        namaHewan = new String[]{"Kucing", "Anjing", "Lebah", "Ikan", "Kura-kura"};
        gambarHewan = new int[]{R.mipmap.cat, R.mipmap.dog, R.mipmap.bee,
                R.mipmap.fish, R.mipmap.turtle};
        harga = new String[]{"45000", "55000", "65000", "75000", "60000"};

        adapter= new TransaksiAdapter(keranjang.this, itemList, gambarHewan, namaHewan);
        cart.setAdapter(adapter);

        clear();
        search();

        cart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] dialogitem = {"Apakah anda Ingin Menghapus data ini?", "Iya Lanjutkan!"};
                dialog = new AlertDialog.Builder(keranjang.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1) {
                            db.deleteOne(Integer.parseInt(itemList.get(position).getId()));
                            Toast.makeText(keranjang.this, "Transaksi Telah dihapus!", Toast.LENGTH_SHORT).show();
                            showData();
                        }
                    }
                }).show();
            }
        });
    }

    public void clear(){
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete();
                Toast.makeText(keranjang.this, "Data Transaksi Dihapus!", Toast.LENGTH_SHORT).show();
                showData();
                estimasi = 0;
            }
        });
    }


    public void showData(){
        itemList.clear();
        cart.setAdapter(null);
        adapter= new TransaksiAdapter(keranjang.this, itemList ,gambarHewan, namaHewan);
        cart.setAdapter(adapter);

        ArrayList<HashMap<String, String>> row = db.getAllData();

        for (int i = 0; i < row.size(); i++) {
            String id = row.get(i).get(COLUMN_1);
            String pembeli= row.get(i).get(COLUMN_2);
            String barang = row.get(i).get(COLUMN_3);
            String harga = row.get(i).get(COLUMN_4);
            String jumlah = row.get(i).get(COLUMN_5);
            String total = row.get(i).get(COLUMN_6);

            estimasi += Integer.parseInt(Objects.requireNonNull(total));

            TransaksiModel data = new TransaksiModel();

            data.setId(id);
            data.setPembeli(pembeli);
            data.setBarang(barang);
            data.setHarga(harga);
            data.setJumlah(jumlah);
            data.setTotal(total);

            itemList.add(data);
        }

        TotalTransaksi.setText(String.format("Total Biaya Transaksi sebesar Rp. %s", String.valueOf(estimasi)));
    }

    public void getDataFiltered(String text){
        itemList.clear();
        cart.setAdapter(null);
        adapter= new TransaksiAdapter(keranjang.this, itemList ,gambarHewan, namaHewan);
        cart.setAdapter(adapter);

        ArrayList<HashMap<String, String>> row = db.getAllDataFiltered(text);

        for (int i = 0; i < row.size(); i++) {
            String id = row.get(i).get(COLUMN_1);
            String pembeli= row.get(i).get(COLUMN_2);
            String barang = row.get(i).get(COLUMN_3);
            String harga = row.get(i).get(COLUMN_4);
            String jumlah = row.get(i).get(COLUMN_5);
            String total = row.get(i).get(COLUMN_6);

            TransaksiModel data = new TransaksiModel();
            estimasi += Integer.parseInt(Objects.requireNonNull(total));

            data.setId(id);
            data.setPembeli(pembeli);
            data.setBarang(barang);
            data.setHarga(harga);
            data.setJumlah(jumlah);
            data.setTotal(total);

            itemList.add(data);
        }
    }

    public void search(){
        cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                showData();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                estimasi = 0; getDataFiltered(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                estimasi = 0; getDataFiltered(String.valueOf(s));
            }
        });
    }

}
