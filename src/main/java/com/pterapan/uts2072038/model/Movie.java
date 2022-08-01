package com.pterapan.uts2072038.model;

public class Movie {
    private int id;
    private String title;
    private String genre;
    private int durasi;

    @Override
    public String toString() {
        return title;
    }

    public Movie(int id, String title, String genre, int durasi) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.durasi = durasi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }
}
