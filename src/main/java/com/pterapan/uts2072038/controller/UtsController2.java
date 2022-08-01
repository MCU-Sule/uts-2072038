package com.pterapan.uts2072038.controller;

import com.pterapan.uts2072038.dao.UserDao;
import com.pterapan.uts2072038.model.User;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class UtsController2 {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Label labelUsername;
    public Label labelPassword;

    private UtsController1 utsController1;

    public void submit(ActionEvent actionEvent) {
        UserDao dao = new UserDao();
        int id = 0;
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        int hasil = dao.addData(new User(id, username, password));
        utsController1.lvUser.setItems(dao.getData());
        if (hasil > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Berhasil Add Data", ButtonType.OK);
            alert.showAndWait();
            labelUsername.getScene().getWindow().hide();
        }
    }

    public void setUtsController1(UtsController1 utsController1) {
        this.utsController1 = utsController1;
    }
}
