package com.pterapan.uts2072038.controller;

import com.pterapan.uts2072038.HelloApplication;
import com.pterapan.uts2072038.dao.MovieDao;
import com.pterapan.uts2072038.dao.UserDao;
import com.pterapan.uts2072038.dao.WatchlistDao;
import com.pterapan.uts2072038.model.Movie;
import com.pterapan.uts2072038.model.User;
import com.pterapan.uts2072038.model.WatchList;
import com.pterapan.uts2072038.util.MyConnection;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UtsController1 implements Initializable {
    public ComboBox<String> cmbGenre;
    public ListView<User> lvUser;
    public TableView<Movie> table1;
    public TableView<WatchList> table2;
    public TableColumn<Movie, String> colTitle1;
    public TableColumn<Movie, String> colGenre;
    public TableColumn<Movie, Integer> colDurasi;
    public TableColumn<WatchList, Movie> colTitle2;
    public TableColumn<WatchList, Integer> colLast;
    public TableColumn<WatchList, Boolean> ColFavorite;

    private ObservableList<Movie> mlist;
    private ObservableList<User> ulist;
    private ObservableList<WatchList> wlist;
    private MovieDao movieDao;
    private UserDao userDao;
    private WatchlistDao watchlistDao;

    private FilteredList<Movie> filteredMovie;
    private FilteredList<WatchList> filteredUser;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        movieDao = new MovieDao();
        userDao = new UserDao();
        watchlistDao = new WatchlistDao();
        mlist = FXCollections.observableArrayList();
        ulist = FXCollections.observableArrayList();
        wlist = FXCollections.observableArrayList();

        mlist.addAll(movieDao.getData());
        ulist.addAll(userDao.getData());
        wlist.addAll(watchlistDao.getData());

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "All",
                        "Action",
                        "Musical",
                        "Comedy",
                        "Animated",
                        "Fantasy",
                        "Drama",
                        "Mistery",
                        "Thriller",
                        "Horror"
                );

        cmbGenre.setItems(options);
        cmbGenre.getSelectionModel().select(0);
        lvUser.setItems(ulist);
        table1.setItems(mlist);
        table2.setItems(wlist);
        colTitle1.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTitle()));
        colGenre.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getGenre()));
        colDurasi.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDurasi()));
        colTitle2.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getMovie()));
        colLast.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getLast()));
        ColFavorite.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().isFavorite()));
    }

    public void refreshData() {
        UserDao dao = new UserDao();
        ulist = dao.getData();
        lvUser.setItems(ulist);
        table1.setItems(mlist);
    }

    public void changeCombo(ActionEvent actionEvent) {
        String genre = cmbGenre.getValue();
        if (genre != "All") {
            filteredMovie = mlist.filtered(m -> (m.getGenre().contains(genre)));
            table1.setItems(filteredMovie);
        } else {
            refreshData();
        }
    }

    public void AddUserAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader;
        loader = new FXMLLoader(HelloApplication.class.getResource("UTSSecondPage.fxml"));
        Scene scene = new Scene(loader.load(), 333, 168);
        UtsController2 uts2Controller = loader.getController();
        uts2Controller.setUtsController1(this);
        stage.setTitle("Add Data");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void DelUserAction(ActionEvent actionEvent) {
        User selectedItems;
        selectedItems = lvUser.getSelectionModel().getSelectedItem();

        UserDao dao = new UserDao();

        Alert alertbox = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.OK, ButtonType.CANCEL);
        alertbox.showAndWait();
        if (alertbox.getResult() == ButtonType.OK) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Data berhasil dihapus", ButtonType.OK);
            alert.showAndWait();
            dao.delData(selectedItems);
        }
        refreshData();
    }

    public void printReport(ActionEvent actionEvent) {
        JasperPrint jp;
        Connection conn = MyConnection.getConnection();

        Map param = new HashMap();
        try {
            jp = JasperFillManager.fillReport("report/UTS.jasper", param, conn);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("laporan movies");
            viewer.setVisible(true);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void filterUser(MouseEvent mouseEvent) {
        int user = lvUser.getSelectionModel().getSelectedItem().getId();
        if (user > 0) {
            filteredUser = wlist.filtered(w -> (w.getId() == user));
            table2.setItems(filteredUser);
        } else {
            refreshData();
        }
    }
}
