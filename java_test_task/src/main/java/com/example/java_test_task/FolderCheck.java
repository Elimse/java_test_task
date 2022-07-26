package com.example.java_test_task;

import java.io.File;
import java.io.IOException;

public class FolderCheck {

    public void folderCheck(String FolderName, String FileName){//метод проверки существования папки и файла
        String CurrentUserHomePath = System.getProperty("user.home"); //получаем папку пользователя
        File Dir = new File(CurrentUserHomePath + FolderName); //Директория которую либо создаем, либо используем
        File DB = new File(Dir + FileName); //БД пользователей (.txt файл)

        if (!Dir.exists()) { //проверка существования директории
            System.out.println("Создание директории: " + Dir.getName());
            boolean result = false;

            try{
                Dir.mkdir();
                result = true;
            }
            catch(SecurityException se){
                //handle it
            }
            if(result) {
                System.out.println("Директория создана");
            }
        }
        else {
            System.out.println("Директория существует");
        }

        if (!DB.exists()) { //проверка существования файла
            System.out.println("Создание файла: " + DB.getName());
            boolean result = false;

            try{
                File f = new File(DB.getAbsolutePath());
                f.createNewFile();

                result = true;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(result) {
                System.out.println("Файл создан");
            }
        }
        else {
            System.out.println("Файл существует");
        }
    }
}
