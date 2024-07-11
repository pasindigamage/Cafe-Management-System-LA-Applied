package lk.ijse.LA_CMS.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.LA_CMS.DAO.SQLUtil;
import lk.ijse.LA_CMS.db.DbConnection;
import lk.ijse.LA_CMS.util.Regex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class LoginFromController {

    @FXML
    private PasswordField password;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXButton signIn;

    @FXML
    private TextField userName;

    public void initialize(){
        userName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                password.requestFocus();
            }
        });
        password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                signIn.requestFocus();
            }
        });
        setGreetings();
    }

    @FXML
    private Label time;

    private void setGreetings() {
        LocalTime currentTime = LocalTime.now();
        String greeting = (currentTime.getHour() < 12) ? "Good Morning !!!" :currentTime.getHour() < 18 ? "Good Afternoon !!!" : "Good Evening !!!";
        time.setText(greeting);
    }

    @FXML
    void letsSignInOnAction (ActionEvent event) throws IOException, SQLException {
        String un = userName.getText();
        String pw = password.getText();
        try {
            checkCredential(un, pw);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPS! something went wrong").show();
        }

    }

    public static String signPerson;

    private void checkCredential(String un, String pw) throws SQLException, IOException {
       // ResultSet resultSet=SQLUtil.execute("SELECT userName, password FROM User WHERE userName = ?;");
        String sql = "SELECT userName, password FROM User WHERE userName = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, un);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            String dbPw = resultSet.getString(2);

            if(dbPw.equals(pw)) {
                signPerson = getUserName(un);
                navigateToTheDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "User Name is not found!").show();
        }
    }

    public static String getSignPersonID;
    private String getUserId(String un) throws SQLException{
        String sql = "SELECT id FROM User WHERE userName = ?";

        getSignPersonID = null;

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, un); // Set the parameter

            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) { // Check if there's at least one row
                    getSignPersonID = resultSet.getString(1); // Retrieve the value from the first column
                }
            }
        }
        return getSignPersonID;
    }

    private String getUserName(String un) throws SQLException {
        String sql = "SELECT Employee.name FROM User JOIN Employee ON User.employeeId = Employee.id WHERE userName = ?";

        String signPerson = null;

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, un); // Set the parameter

            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) { // Check if there's at least one row
                    signPerson = resultSet.getString(1); // Retrieve the value from the first column
                }
            }
        }
        return signPerson; // Return the retrieved value
    }

    private void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/mainDashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void linkForgotPasswordOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/changePassword.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.show();

    }

    @FXML
    void linkRegistrationOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/register.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.show();

    }
}