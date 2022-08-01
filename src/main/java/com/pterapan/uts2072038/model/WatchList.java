package com.pterapan.uts2072038.model;

public class WatchList {
    private int id;
    private int last;
    private boolean favorite;
    private Movie movie;
    private User user;

    public WatchList(int id, int last, boolean favorite, Movie movie, User user) {
        this.id = id;
        this.last = last;
        this.favorite = favorite;
        this.movie = movie;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
