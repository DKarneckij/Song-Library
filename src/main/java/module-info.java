module library.songlibrary {
    requires javafx.controls;
    requires javafx.fxml;


    opens library.songlibrary to javafx.fxml;
    exports library.songlibrary;
}