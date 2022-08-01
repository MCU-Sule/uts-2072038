package com.pterapan.uts2072038.dao;

import com.pterapan.uts2072038.model.Movie;
import com.pterapan.uts2072038.model.User;
import com.pterapan.uts2072038.model.WatchList;
import com.pterapan.uts2072038.util.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WatchlistDao implements DaoInterface<WatchList> {
    @Override
    public ObservableList<WatchList> getData() {
        ObservableList<WatchList> wlist;
        wlist = FXCollections.observableArrayList();

        Connection conn = MyConnection.getConnection();
        String kalimat_sql = "SELECT w.idWatchList, w.LastWatch, w.Favorite, w.Movie_idMovie AS movie_id, w.User_idUser AS user_id, m.Title, m.Genre, m.Durasi, u.UserName, u.UserPassword FROM WatchList w JOIN Movie m ON w.Movie_idMovie = m.idMovie JOIN User u ON w.User_idUser = u.idUser";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(kalimat_sql);
            ResultSet hasil = ps.executeQuery();
            while (hasil.next()) {
                int idMovie = hasil.getInt("movie_id");
                String title = hasil.getString("Title");
                String genre = hasil.getString("Genre");
                int durasi = hasil.getInt("Durasi");
                Movie movie = new Movie(idMovie, title, genre, durasi);

                int idUser = hasil.getInt("user_id");
                String username = hasil.getString("UserName");
                String password = hasil.getString("UserPassword");
                User user = new User(idUser, username, password);

                int idWatchList = hasil.getInt("idWatchList");
                int lastWatch = hasil.getInt("LastWatch");
                boolean favorite = hasil.getBoolean("Favorite");
                WatchList w = new WatchList(idWatchList, lastWatch, favorite, movie, user);
                wlist.add(w);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wlist;
    }

    @Override
    public int addData(WatchList data) {
        Connection conn = MyConnection.getConnection();
        String kalimat_sql = "INSERT INTO WatchList(idWatchList, LastWatch, Favorite, Movie_idMovie, User_idUser) VALUES(?,?,?,?,?)";
        int hasil = 0;
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(kalimat_sql);
            ps.setInt(1, data.getId());
            ps.setInt(2, data.getLast());
            ps.setBoolean(3, data.isFavorite());
            ps.setInt(4, data.getMovie().getId());
            ps.setInt(5, data.getUser().getId());
            hasil = ps.executeUpdate();

            if (hasil > 0) {
                System.out.println("berhasil masukin nilai");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hasil;
    }

    @Override
    public int delData(WatchList data) {
        Connection conn;
        conn = MyConnection.getConnection();

        String query = "DELETE FROM WatchList WHERE idWatchList=?";
        int hasil = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, data.getId());
            hasil = ps.executeUpdate();
            if (hasil > 0) {
                System.out.println("berhasil hapus data");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hasil;
    }

    @Override
    public int updateData(WatchList data) {
        Connection conn;
        conn = MyConnection.getConnection();

        String query = "UPDATE WatchList SET LastWatch=?, Favorite=?, Movie_idMovie=?, User_idUser=? WHERE idWatchList=?";
        int hasil = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, data.getLast());
            ps.setBoolean(2, data.isFavorite());
            ps.setInt(3, data.getMovie().getId());
            ps.setInt(4, data.getUser().getId());
            ps.setInt(5, data.getId());
            hasil = ps.executeUpdate();
            if (hasil > 0) {
                System.out.println("berhasil update data");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hasil;
    }
}
