package tokodigitalpbo;

public class Pelanggan extends Aktor {

    public Pelanggan(String id, String nama) {
        super(id, nama);
    }

    @Override
    public String getPeran() {
        return "Pelanggan";
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + ", Nama: " + getNama()
                + ", Peran: " + getPeran();
    }
}