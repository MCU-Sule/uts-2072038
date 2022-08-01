package com.pterapan.uts2072038.dao;

import javafx.collections.ObservableList;

public interface DaoInterface<T> {
    ObservableList<T> getData();
    int addData(T data);
    int delData(T data);
    int updateData(T data);
}
