package tokodigitalpbo;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SistemToko implements Serializable {

    private static final long serialVersionUID = 1L;

    private String namaToko;

    private HashMap<String, Pelanggan> daftarPelanggan;
    private HashMap<String, Produk> daftarProduk;
    private ArrayList<Transaksi> daftarTransaksi;

    public SistemToko(String namaToko) {
        this.namaToko = namaToko;

        daftarPelanggan = new HashMap<>();
        daftarProduk = new HashMap<>();
        daftarTransaksi = new ArrayList<>();
    }

    public void tambahPelanggan(String id, String nama) {
        daftarPelanggan.put(id, new Pelanggan(id, nama));
    }

    public void tambahProduk(String kode, String nama, int stok) {
        daftarProduk.put(kode, new Produk(kode, nama, stok));
    }

    private void prosesTransaksi(String data)
            throws TransaksiException {

        try {
            String[] bagian = data.split(",");

            if (bagian.length != 3) {
                throw new TransaksiException(
                    "Format transaksi tidak valid."
                );
            }

            String id = bagian[0].trim().replace("ID=", "");
            String kode = bagian[1].trim().replace("KD=", "");
            int jumlah = Integer.parseInt(
                bagian[2].trim().replace("JML=", "")
            );

            Pelanggan pelanggan = daftarPelanggan.get(id);
            Produk produk = daftarProduk.get(kode);

            if (pelanggan == null) {
                throw new TransaksiException(
                    "Pelanggan " + id + " tidak terdaftar."
                );
            }

            if (produk == null) {
                throw new TransaksiException(
                    "Produk " + kode + " tidak ditemukan."
                );
            }

            if (jumlah <= 0) {
                throw new TransaksiException(
                    "Jumlah harus lebih dari 0."
                );
            }

            if (jumlah > produk.getStok()) {
                throw new TransaksiException(
                    "Stok produk tidak mencukupi."
                );
            }

            produk.kurangiStok(jumlah);

            Transaksi transaksi = new Transaksi(
                pelanggan, produk, jumlah
            );

            daftarTransaksi.add(transaksi);

            System.out.println(
                "Transaksi berhasil: " + transaksi
            );

        } catch (NumberFormatException e) {
            throw new TransaksiException(
                "Jumlah transaksi harus berupa angka."
            );
        }
    }

    public void jalankanTransaksiDariFile(String namaFile)
            throws IOException {

        try (BufferedReader reader = new BufferedReader(
                new FileReader(namaFile))) {

            String baris;

            while ((baris = reader.readLine()) != null) {

                baris = baris.trim();

                if (baris.isEmpty()) {
                    continue;
                }

                String[] daftarData =
                    baris.split("\\s+(?=ID=)");

                for (String data : daftarData) {

                    try {
                        prosesTransaksi(data.trim());

                    } catch (TransaksiException e) {
                        System.out.println(
                            "Transaksi gagal: " + e.getMessage()
                        );
                    }
                }
            }
        }
    }

    public void simpanLaporanAkhir() {

        try (ObjectOutputStream output =
                new ObjectOutputStream(
                    new FileOutputStream("sistem_state.ser"))) {

            output.writeObject(this);

            System.out.println(
                "Serialization berhasil disimpan."
            );

        } catch (IOException e) {
            System.out.println(
                "Gagal menyimpan Serialization: "
                + e.getMessage()
            );
        }

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("laporan.txt"))) {

            writer.write("DAFTAR AKTOR AKTIF");
            writer.newLine();

            writer.write("Nama Toko: " + namaToko);
            writer.newLine();

            writer.write("========================");
            writer.newLine();

            for (Pelanggan pelanggan :
                    daftarPelanggan.values()) {

                writer.write(
                    "ID: " + pelanggan.getId()
                    + " | Nama: " + pelanggan.getNama()
                );

                writer.newLine();
            }

            System.out.println(
                "Laporan berhasil dibuat."
            );

        } catch (IOException e) {
            System.out.println(
                "Gagal membuat laporan: " + e.getMessage()
            );
        }
    }
}