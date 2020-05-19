package umn.ac.id.tugasakhir;

public class Destination {
    public String  deskripsi,imgurl, nama, subgrup,id;
    //public Integer umur;

    public Destination() {

    }

    public Destination(String deskripsi, String imgurl, String nama, String subgrup) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.imgurl = imgurl;
        this.subgrup = subgrup;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getImgurl() {
        return imgurl;
    }
    public void setImgurl(String imgurl) {
        this.imgurl= imgurl;
    }


    public String getDeskripsi() {
        return deskripsi;
    }
    public void setDeskripsi(String deskripsi) {
        this.deskripsi= deskripsi;
    }

    public String getSubgrup() {
        return subgrup;
    }
    public void setSubgrup(String nomortelepon) {
        this.subgrup= subgrup;
    }


}
