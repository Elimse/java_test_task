package com.example.java_test_task;

import java.io.*;

public class fileRider {

    public int fileReader(File name){

        BufferedReader reader = null; //буфер для считывания файла
        try { //получаем количество строк в файле (оно же количество зарегистрированных пользователей)
            reader = new BufferedReader(new FileReader(name));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int lines = 0;
        while (true) {
            try {
                if (!(reader.readLine() != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            lines++;
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return (lines);
    }
}
