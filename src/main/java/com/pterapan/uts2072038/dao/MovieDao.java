package com.pterapan.uts2072038.dao;

import com.pterapan.uts2072038.model.Movie;
import com.pterapan.uts2072038.util.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDao implements DaoInterface<Movie> {
    @Override
    public ObservableList<Movie> getData() {
        ObservableList<Movie> mlist;
        mlist = FXCollections.observableArrayList();

        Connection conn = MyConnection.getConnection();
        String kalimat_sql = "SELECT idMovie, Title, Genre, Durasi FROM movie";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(kalimat_sql);
            ResultSet hasil = ps.executeQuery();
            while (hasil.next()) {
                int id = hasil.getInt("idMovie");
                String title = hasil.getString("Title");
                String genre = hasil.getString("Genre");
                int durasi = hasil.getInt("Durasi");
                Movie m = new Movie(id, title, genre, durasi);
                mlist.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mlist;
    }

    @Override
    public int addData(Movie data) {
        Connection conn = MyConnection.getConnection();
        String kalimat_sql = "INSERT INTO movie(idMovie, Title, Genre, Durasi) VALUES(?,?,?,?)";
        int hasil = 0;
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(kalimat_sql);
            ps.setInt(1, data.getId());
            ps.setString(2, data.getTitle());
            ps.setString(3, data.getGenre());
            ps.setInt(4, data.getDurasi());
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
    public int delData(Movie data) {
        Connection conn;
        conn = MyConnection.getConnection();

        String query = "DELETE FROM movie WHERE idMovie=?";
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
    public int updateData(Movie data) {
        Connection conn;
        conn = MyConnection.getConnection();

        String query = "UPDATE movie SET Title=?, Genre=?, Durasi=? WHERE idMovie=?";
        int hasil = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, data.getTitle());
            ps.setString(2, data.getGenre());
            ps.setInt(3, data.getDurasi());
            ps.setInt(4, data.getId());
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
