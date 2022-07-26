package com.example.java_test_task;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class User_work_space {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button B_Add_line; //кнопка добавить строку

    @FXML
    private Button B_Delete_line; //кнопка удалить строку

    @FXML
    private Button B_Save; //кнопка сохранить таблицу

    @FXML
    private Label F_label_name; //имя пользователя вверху рабочей области

    public void displayName(String Uname){//метод для отображения имени пользователя
        F_label_name.setText("Пользователь " + Uname);
    }
    @FXML
    private TableView<TableData> T_User_table; //таблица

    @FXML
    private TableColumn<TableData, String> T_c1; //колонка 1 в таблице

    @FXML
    private TableColumn<TableData, String> T_c2; //колонка 2 в таблице

    private ObservableList<TableData> CurrentData = FXCollections.observableArrayList();
    public void add(TableData tableData){CurrentData.add(tableData);}

    public ObservableList<TableData> getCurrentData() {
        return CurrentData;
    }

    File userFile;

    @FXML
    void initialize() {

        T_c1.setCellValueFactory(new PropertyValueFactory<TableData, String>("c1"));
        T_c2.setCellValueFactory(new PropertyValueFactory<TableData, String>("c2"));
        T_User_table.setItems(getCurrentData());

        newStage NS = new newStage();

        String CurrentUserHomePath = System.getProperty("user.home"); //получаем папку пользователя
        String currentUser = NS.userName; //имя пользователя
        String regDate = NS.userDate.replace(':', '-'); //заменяем символы : на -, так как файл с символом : нельзя создать (дата регистрации)
        String ufile = "/" + currentUser + "_" + regDate + ".txt";

        userFile = new File(CurrentUserHomePath + "/.test"+"/user_data" + "/" + ufile);

        FolderCheck FC = new FolderCheck();
        FC.folderCheck("/.test"+"/user_data", ufile); //вызываем метод создания либо проверки папки /user_data и файла текущего пользователя

        BufferedReader reader = null; //буфер для считывания файла
        try { //получаем количество строк в файле (количество строк в таблице)
            reader = new BufferedReader(new FileReader(userFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        fileRider FR = new fileRider();
        int lines = FR.fileReader(userFile); //метод счета строк в файле из класса fileReader

        if(lines > 0) {
            String[][] strings = new String[lines][2]; //создаем двумерный массив записей (под два столбца)

            try {//заносим строки в массив
                reader = new BufferedReader(new FileReader(userFile));
                String line;
                int i = 0;
                while (((line = reader.readLine()) != "\n") && i < lines) {
                    String str = line;
                    String delimetr = ";"; //разделитель между столбцами
                    String[] substring = new String[2]; //вся строка делится на 2 части
                    for (int j = 0; j < 2; j++) {
                        substring = str.split(delimetr); //разбиваем данные
                        strings[i][j] = substring[j];
                    }
                    i++;
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < lines; i++) {//заполняем таблицу записями
                int j = 0;
                if(strings[i][j].equals("null")){
                    strings[i][j] = null;
                }
                if(strings[i][j+1].equals("null")){
                    strings[i][j+1] = null;
                }
                CurrentData.add(new TableData(strings[i][j], strings[i][j + 1])); //добавляем строку в данные таблицы
            }
        }
        T_User_table.setItems(CurrentData);

        T_c1.setCellFactory(TextFieldTableCell.<TableData>forTableColumn());//добавляем редактирование для 1го столбца таблицы
        T_c1.setOnEditCommit((TableColumn.CellEditEvent<TableData, String> t) ->{
            ((TableData) t.getTableView().getItems().get(t.getTablePosition().getRow())).setC1(t.getNewValue());
        });

        T_c2.setCellFactory(TextFieldTableCell.<TableData>forTableColumn());//добавляем редактирование для 2го столбца таблицы
        T_c2.setOnEditCommit((TableColumn.CellEditEvent<TableData, String> t) ->{
            ((TableData) t.getTableView().getItems().get(t.getTablePosition().getRow())).setC2(t.getNewValue());
        });

        B_Add_line.setOnAction(actionEvent -> { //нажатие на кнопку "добавить строку"
            ObservableList<TableData> data = T_User_table.getItems(); //получаем текуще данные таблицы
            data.add(new TableData(null, null)); //добавляем строку в таблицу
        });

        B_Delete_line.setOnAction(actionEvent -> {
            ObservableList<TableData> data = T_User_table.getItems();
            TableData selectedItem = T_User_table.getSelectionModel().getSelectedItem();//получаем выделенную строку
            data.remove(selectedItem); //удаляем выделенную строку
        });

        B_Save.setOnAction(actionEvent -> {//нажатие на кнопку сохранить


            FileWriter writer = null;
            try {
                writer = new FileWriter(userFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ObservableList<TableData> data = T_User_table.getItems();
            String row;
            int num = data.size();

            for(int i = 0; i < num; i++){//записываем данные в txt файл пользователя построчно

                row = T_User_table.getItems().get(i).getC1() + ";" + T_User_table.getItems().get(i).getC2() + "\n";

                try {
                    writer.write(row);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("user_work_space.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Закрытие окна");
            }
        });
    }
}

