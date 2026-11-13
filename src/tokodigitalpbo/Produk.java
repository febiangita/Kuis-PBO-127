package tokodigitalpbo;

import java.io.Serializable;

public class Produk implements Serializable {

    private String kode;
    private String nama;
    private int stok;

    public Produk(String kode, String nama, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.stok = stok;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public int getStok() {
        return stok;
    }

    public void kurangiStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException(
                "Jumlah pembelian harus lebih dari 0."
            );
        }

        if (jumlah > stok) {
            throw new IllegalArgumentException(
                "Stok produk tidak mencukupi."
            );
        }

        stok -= jumlah;
    }

    @Override
    public String toString() {
        return kode + " - " + nama
                + " (Stok: " + stok + ")";
    }
}
