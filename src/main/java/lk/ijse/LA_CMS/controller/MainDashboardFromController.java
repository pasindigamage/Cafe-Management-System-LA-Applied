package lk.ijse.LA_CMS.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainDashboardFromController {
    @FXML
    private Label first;

    @FXML
    private Label second;

    @FXML
    private Label three11;
    @FXML
    private Label three1;
    @FXML
    private Label three;
    @FXML
    private Label date;
    @FXML
    private JFXButton home;

    @FXML
    private Label time111;

    @FXML
    private JFXButton employee;

    @FXML
    private JFXButton inventoryDetail;

    @FXML
    private JFXButton kitchenware;

    @FXML
    private JFXButton logout;

    @FXML
    private AnchorPane mainDashboard;

    @FXML
    private JFXButton maintain;

    @FXML
    private JFXButton menu;

    @FXML
    private JFXButton order;

    @FXML
    private AnchorPane subDashboard;

    @FXML
    private JFXButton supplier;


    @FXML
    private Label time;

    public void initialize() throws SQLException {
        addHoverHandlers(employee);
        addHoverHandlers(menu);
        addHoverHandlers(order);
        addHoverHandlers(maintain);
        addHoverHandlers(logout);
        addHoverHandlers(kitchenware);
        addHoverHandlers(inventoryDetail);
        addHoverHandlers(supplier);
        setDate();
        displayTopPerformingFoodItems();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> { // set current Time
            LocalTime currentTime = LocalTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatter);

            time.setText(formattedTime);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        date.setText(String.valueOf(now));

    }

    private void addHoverHandlers(Button button) {// button Animation
        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color: Black; -fx-text-fill: white; ");
        });
        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color:  transparent; -fx-text-fill: white;");
        });
    }


    private void setGreetings() {
        LocalTime currentTime = LocalTime.now();
        String greeting = (currentTime.getHour() < 12) ? "Good Morning !!!" :currentTime.getHour() < 18 ? "Good Afternoon !!!" : "Good Evening !!!";
        time.setText(greeting);
    }
    private void displayTopPerformingFoodItems() throws SQLException {
      ResultSet resultSet= SQLUtil.execute("SELECT fi.description AS foodDescription, SUM(od.qty) AS totalQuantityOrdered " +
              "FROM orderDetails od " +
              "JOIN FoodItems fi ON od.foodItemId = fi.id " +
              "GROUP BY fi.description " +
              "ORDER BY totalQuantityOrdered DESC " +
              "LIMIT 5;");

            if (resultSet.next()) {
                first.setText(resultSet.getString("foodDescription") + " - " + resultSet.getInt("totalQuantityOrdered"));
            }
            if (resultSet.next()) {
                second.setText(resultSet.getString("foodDescription") + " - " + resultSet.getInt("totalQuantityOrdered"));
            }
            if (resultSet.next()) {
                three.setText(resultSet.getString("foodDescription") + " - " + resultSet.getInt("totalQuantityOrdered"));
            }
            if (resultSet.next()) {
                three1.setText(resultSet.getString("foodDescription") + " - " + resultSet.getInt("totalQuantityOrdered"));
            }
            if (resultSet.next()) {
                three11.setText(resultSet.getString("foodDescription") + " - " + resultSet.getInt("totalQuantityOrdered"));
            }
    }
    @FXML
    void employeeOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employee.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    @FXML
    void inventoryOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/inventorySupplierDetail.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    @FXML
    void kitchenwareOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/kitchenWare.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    @FXML
    void logoutOnAction(ActionEvent event) {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
    }

    @FXML
    void maintainOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/maintains.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    @FXML
    void menuOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/foodItem.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    @FXML
    void orderOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/order.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    @FXML
    void supplierOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/suppliers.fxml"));
        Pane registerPane = fxmlLoader.load();
        subDashboard.getChildren().clear();
        subDashboard.getChildren().add(registerPane);
    }

    public void homeOnAction(ActionEvent actionEvent) throws IOException {
        mainDashboard.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/mainDashboard.fxml"));
        Stage stage = (Stage) mainDashboard.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
    }

}
