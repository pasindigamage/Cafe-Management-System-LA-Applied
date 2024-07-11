package lk.ijse.LA_CMS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/Loading_Page.fxml"))));
        stage.setTitle("Cafe Management System");
        stage.centerOnScreen();
        stage.show();
    }
}