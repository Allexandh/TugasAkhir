package umn.ac.id.tugasakhir;

public class Review {
    public String deskripsi, id, title;
    public Integer rating;
    //public Integer umur;

    public Review() {

    }

    public Review(String deskripsi, String id, String title, Integer rating) {
        this.deskripsi = deskripsi;
        this.id = id;
        this.title = title;
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title= title;
    }


    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi= deskripsi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id= id;
    }


}
