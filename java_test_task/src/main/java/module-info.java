module com.example.java_test_task {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.java_test_task to javafx.fxml;
    exports com.example.java_test_task;
}