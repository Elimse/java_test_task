package com.example.java_test_task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableData { //класс для заполнения таблицы
    private String c1;
    private String c2;

    public TableData(String c1, String c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public String getC1() {
        return c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

}
