package com.pterapan.uts2072038.dao;

import com.pterapan.uts2072038.model.User;
import com.pterapan.uts2072038.util.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements DaoInterface<User> {
    @Override
    public ObservableList<User> getData() {
        ObservableList<User> ulist;
        ulist = FXCollections.observableArrayList();

        Connection conn = MyConnection.getConnection();
        String kalimat_sql = "SELECT idUser, UserName, UserPassword FROM user";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(kalimat_sql);
            ResultSet hasil = ps.executeQuery();
            while (hasil.next()) {
                int id = hasil.getInt("idUser");
                String username = hasil.getString("UserName");
                String password = hasil.getString("UserPassword");
                User u = new User(id, username, password);
                ulist.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ulist;
    }

    @Override
    public int addData(User data) {
        Connection conn = MyConnection.getConnection();
        String kalimat_sql = "INSERT INTO user(idUser, UserName, UserPassword) VALUES(?,?,?)";
        int hasil = 0;
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(kalimat_sql);
            ps.setInt(1, data.getId());
            ps.setString(2, data.getUsername());
            ps.setString(3, data.getPassword());
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
    public int delData(User data) {
        Connection conn;
        conn = MyConnection.getConnection();

        String query = "DELETE FROM user WHERE idUser=?";
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
    public int updateData(User data) {
        Connection conn;
        conn = MyConnection.getConnection();

        String query = "UPDATE user SET UserName=?, UserPassword=? WHERE idUser=?";
        int hasil = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, data.getUsername());
            ps.setString(2, data.getPassword());
            ps.setInt(3, data.getId());
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
