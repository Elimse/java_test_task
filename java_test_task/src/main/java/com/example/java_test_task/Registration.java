package com.example.java_test_task;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Registration {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button B_registration; //кнопка зарегистрироваться

    @FXML
    private TextField F_login; //поле логин

    @FXML
    private PasswordField F_password; //поле пароль

    @FXML
    private PasswordField F_passwordSecond; //повторный пароль

    @FXML
    void initialize() throws IOException {

        String CurrentUserHomePath = System.getProperty("user.home"); //получаем папку пользователя
        File DB = new File(CurrentUserHomePath + "/.test" + "/UserDB.txt"); //БД пользователей (.txt файл)

        B_registration.setOnAction(actionEvent -> {

            if((F_login.getText() != null) && (F_password.getText() != null) && F_password.getText().equals(F_passwordSecond.getText())){ //если имя пользователя и пароли не пусты, и пароли сопадают

                FileWriter writer = null; //метод записи в файл
                try {
                    writer = new FileWriter(DB, true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String name = F_login.getText(); //имя
                String pass = F_password.getText(); //пароль
                Date date = new Date(); //дата регистрации
                String user = name + ";" + pass + ";" + date + ";" + "\n"; //скомпонованные имя пароль и дата + переход на новую строку

                String regDate = String.valueOf(date);//записываем дату регистрации для передачи другому окну

                try { //записываем имя и пароль в файл и переходим на другую страницу
                    writer.write(user);
                    writer.close();

                    newStage NS = new newStage();
                    B_registration.getScene().getWindow().hide();
                    NS.newStage("registration.fxml","user_work_space.fxml", name, regDate); //вызываем метод откртия новой страницы из класса Auth_controller (метод - newStage)
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                //если данные введены неверно выводим сообщение
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Неверные данные");
                alert.setHeaderText(null);
                alert.setContentText("Пароли не совпадают");

                alert.showAndWait();
            }
        });
    }
}

