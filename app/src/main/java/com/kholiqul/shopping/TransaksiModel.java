package com.kholiqul.shopping;

public class TransaksiModel {

    private String id;
    private String pembeli;
    private String barang;
    private String harga;
    private String jumlah;
    private String total;
    private String totalS;

    public TransaksiModel(){}

    public TransaksiModel(String id, String pembeli, String barang, String harga, String jumlah, String total, String totalS){
        this.id = id;
        this.pembeli = pembeli;
        this.barang = barang;
        this.harga = harga;
        this.jumlah = jumlah;
        this.total = total;
        this.totalS = totalS;
    }

    public void setId(String id){this.id = id;}
    public void setPembeli(String pembeli){this.pembeli = pembeli;}
    public void setBarang(String barang){this.barang = barang;}
    public void setHarga(String harga){this.harga = harga;}
    public void setJumlah(String jumlah){this.jumlah = jumlah;}
    public void setTotal(String total){this.total = total;}
    public void setTotalS(String totalS){this.total = totalS;}

    public String getId() {
        return id;
    }

    public String getPembeli() {
        return pembeli;
    }

    public String getBarang() {
        return barang;
    }

    public String getHarga() {
        return harga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getTotal() {
        return total;
    }
    public String getTotalS() {
        return totalS;
    }
}
