package com.example.java_test_task;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Auth_controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button B_enter; //Кнопка входа

    @FXML
    private Button B_registration; //кнопка регистрации

    @FXML
    private TextField F_login; //Поле логина

    @FXML
    private PasswordField F_password; //Поле пароля

    @FXML
    void initialize() {

        String CurrentUserHomePath = System.getProperty("user.home"); //получаем папку пользователя
        File Dir = new File(CurrentUserHomePath + "/.test"); //Директория которую либо создаем, либо используем
        File DB = new File(Dir + "/UserDB.txt"); //БД пользователей (.txt файл)

        newStage NS = new newStage();//класс с методом открытия новых окон

        FolderCheck FC = new FolderCheck();
        FC.folderCheck("/.test", "/UserDB.txt"); //вызываем метод проверки существования папки .test и файла UserDB.txt из класса FolderCheck (метод - folderCheck)
        //если папка и файл не существуют, то метод их создает

        B_registration.setOnAction(actionEvent -> { //переход на окно регистрации
            B_registration.getScene().getWindow().hide();

            NS.newStage("hello-view.fxml","registration.fxml", F_login.getText(), null);//вызываем метод откртия нового окна из класса newStage (метод - newStage)
        });

        B_enter.setOnAction(actionEvent -> {

            fileRider FR = new fileRider();
            int lines = FR.fileReader(DB); //метод счета строк в файле из класса fileReader

            BufferedReader reader = null; //буфер для считывания файла
            try { //получаем количество строк в файле (оно же количество зарегистрированных пользователей)
                reader = new BufferedReader(new FileReader(DB));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            String[] users = new String[lines]; //создаем массив пользователей

            try {//заносим пользователей построчно в массив
                reader = new BufferedReader(new FileReader(DB));
                String line;
                int i = 0;
                    while(((line = reader.readLine()) != "\n") && i < lines){
                        users[i] = line;
                        System.out.println(line);
                        i++;
                    }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(F_login.getText() != "" && F_password.getText() != "") {
                String name = F_login.getText(); //имя
                String pass = F_password.getText(); //пароль
                String user = name + ";" + pass;

                for (int i = 0; i < users.length; i++) {//проходим через весь массив пользователей и проверяем есть ли совпадения имени и пароля
                    if (users[i].contains(user)) {

                        String delimetr = ";"; //разделитель между именем фамилией и датой регистрации пользователя
                        String[] substring = new String[3]; //вся строка делится на 3 части
                        substring = users[i].split(delimetr); //разбиваем данные совпавшего пользователя и забиваем в массив, затем вытягиваем из данных время регистрации
                        String regDate = substring[2];//время регистрации

                        if(name.equals(substring[0]) && pass.equals(substring[1])) {
                            B_enter.getScene().getWindow().hide();
                            NS.newStage("hello-view.fxml", "user_work_space.fxml", F_login.getText(), regDate);//вызываем метод откртия нового окна из класса newStage (метод - newStage)
                            return;
                        }
                    }
                }
                //если данные введены неверно выводим сообщение
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Неверные данные");
                alert.setHeaderText(null);
                alert.setContentText("Ошибка в имени пользователя или пароле");

                alert.showAndWait();
                return;
            }

            //если данные не введены
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Неверные данные");
            alert.setHeaderText(null);
            alert.setContentText("Введите данные пользователя");

            alert.showAndWait();
        });
    }
}

