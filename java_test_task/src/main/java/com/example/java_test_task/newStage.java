package com.example.java_test_task;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class newStage {

    static String userName;
    static String userDate;

    public void newStage(String currentPage, String PageName, String username, String regDate){//метод открытия нового окна

        FXMLLoader loader = new FXMLLoader(getClass().getResource(PageName));
        if(currentPage == "hello-view.fxml"){
        loader.setLocation(Auth_controller.class.getResource(PageName));
        }
        else{
            loader.setLocation(Registration.class.getResource(PageName));
        }

        userName = username;//имя пользователя
        userDate = regDate;//дата регистрации

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(PageName == "user_work_space.fxml"){ //если переходм на страницу с таблицей
            User_work_space user_work_space = loader.getController();
            user_work_space.displayName(username); //передаем имя для label вверху окна
        }

        Parent root = loader.getRoot(); //путь к файлу который загружаем
        Stage stage = new Stage();
        stage.setScene(new Scene(root)); //указываем путь к сцене
        stage.show(); //показать окно
    }
}
