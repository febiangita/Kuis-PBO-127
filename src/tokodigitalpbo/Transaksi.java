package tokodigitalpbo;

import java.io.Serializable;

public class Transaksi implements Serializable {

    private Pelanggan pelanggan;
    private Produk produk;
    private int jumlah;

    public Transaksi(Pelanggan pelanggan, Produk produk, int jumlah) {
        this.pelanggan = pelanggan;
        this.produk = produk;
        this.jumlah = jumlah;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public Produk getProduk() {
        return produk;
    }

    public int getJumlah() {
        return jumlah;
    }

    @Override
    public String toString() {
        return "Pelanggan: " + pelanggan.getNama()
                + ", Produk: " + produk.getNama()
                + ", Jumlah: " + jumlah;
    }
}
