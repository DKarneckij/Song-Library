// Darius Karneckij dk910
// Mingchao Huo mh1306

package library.songlibrary;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SongLib extends javafx.application.Application {
    private static Object names;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SongLib.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 875, 481);

        Controller listController =
                fxmlLoader.getController();
        listController.start();

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String args[]){
        launch(args);
    }
}