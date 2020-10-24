package com.kholiqul.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView menu;
    Button transaksi;
    View view;
    ViewGroup parent;
    LayoutInflater inf;
    AlertDialog.Builder build;
    int pos;
    int[] gambarHewan;
    String[] namaHewan, harga;
    DatabaseHelper db = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.menu);
        transaksi = findViewById(R.id.transaksi);

        namaHewan = new String[]{"Kucing", "Anjing", "Lebah", "Ikan", "Kura-kura"};
        gambarHewan = new int[]{R.mipmap.cat, R.mipmap.dog, R.mipmap.bee,
                R.mipmap.fish, R.mipmap.turtle};
        harga = new String[]{"45000", "55000", "65000", "75000", "60000"};

        BarangAdapter customAdapter = new BarangAdapter(getApplicationContext(), namaHewan, gambarHewan, harga);
        menu.setAdapter(customAdapter);

        transaksi();

        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                pos = position;
                order();
            }
        });

    }



    public void transaksi(){

        transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(MainActivity.this, keranjang.class);
                startActivity(d);
            }
        });


    }

    public void order(){
        inf = getLayoutInflater();
        build = new AlertDialog.Builder(MainActivity.this);
        view = inf.inflate(R.layout.order, parent,false);
        TextView nama = view.findViewById(R.id.hewan);
        final TextView har = view.findViewById(R.id.harga);
        final EditText jumlah = view.findViewById(R.id.jumlah);
        final EditText pembeli = view.findViewById(R.id.atasnama);
        final int barang = pos;
//        final int total = (Integer.parseInt(jumlah.getText().toString()))*(Integer.parseInt(harga[pos]));

        nama.setText(String.format("Nama Hewan : %s", namaHewan[pos]));
        har.setText(String.format("Harga Hewan : %s", harga[pos]));
        build.setTitle("Tambah Transaksi");
        build.setCancelable(true);
        build.setView(view);
        build.setIcon(gambarHewan[pos]);

        build.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        build.setPositiveButton("simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(validasi(pembeli.getText().toString(), String.valueOf(jumlah.getText()))){
                db.insert(pembeli.getText().toString(), String.valueOf(barang), harga[pos], String.valueOf(jumlah.getText()),String.valueOf((Integer.parseInt(jumlah.getText().toString()))*(Integer.parseInt(harga[pos]))));
                Toast.makeText(MainActivity.this, "Transaksi Disimpan", Toast.LENGTH_SHORT).show();}
                else {
                    Toast.makeText(MainActivity.this, "Harap Lengkapi Bidang!", Toast.LENGTH_SHORT).show(); order();
                }
            }
        });
        build.show();

    }

    private boolean validasi(String pembeli, String jumlah) {
        if (pembeli != null && pembeli.length() > 4 && jumlah != null && jumlah.length() >= 1) {
            return true;
        }
        return false;
    }

}
