package umn.ac.id.tugasakhir;

public class GuideList {
    public String nama, nomortelepon, deskripsi, destination, email, imgurl;
    public Integer harga;

    public GuideList() {

    }

    public GuideList(String nama, String email, String nomortelepon, String deskripsi, String destination, String imgurl, Integer harga) {
        this.nama = nama;
        this.nomortelepon = nomortelepon;
        this.deskripsi = deskripsi;
        this.email = email;
        this.destination = destination;
        this.imgurl = imgurl;
        this.harga = harga;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNomortelepon() {
        return nomortelepon;
    }

    public void setNomortelepon(String nomortelepon) {
        this.nomortelepon = nomortelepon;
    }


}
