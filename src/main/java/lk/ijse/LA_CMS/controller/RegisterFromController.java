package lk.ijse.LA_CMS.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.LA_CMS.BO.custom.CredintialBO;
import lk.ijse.LA_CMS.BO.custom.Impl.CredintialBOImpl;
import lk.ijse.LA_CMS.DAO.custom.RegisterDAO;
import lk.ijse.LA_CMS.DAO.custom.impl.RegisterDAOImpl;
import lk.ijse.LA_CMS.Entity.User;
import lk.ijse.LA_CMS.util.Regex;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterFromController {

    @FXML
    private JFXButton cancel;

    @FXML
    private TextField employeeID;

    @FXML
    private TextField password;

    @FXML
    private TextField userId;

    @FXML
    private TextField userName;

    CredintialBO credintialBO=new CredintialBOImpl();
    public void initialize(){
        employeeID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                userId.requestFocus();
            }
        });
        userId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                userName.requestFocus();
            }
        });

        userName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                password.requestFocus();
            }
        });
    }

    @FXML
    void cancelOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void registerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String eid = employeeID.getText();
        String uid = userId.getText();
        String uname = userName.getText();
        String pw = password.getText();

        if(isValied()){
            credintialBO.saveUser(new User(eid,uid,uname,pw));
            new Alert(Alert.AlertType.CONFIRMATION, "Registration successfully!").show();
            userName.clear();
            password.clear();
            userId.clear();
            employeeID.clear();
        }else{
            new Alert(Alert.AlertType.ERROR, "Something Happened!").show();
        }

    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.password,password)) return false;
        if (!Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.name,userName)) return false;

        return true;
    }
    @FXML
    void txtEmployeeIdOnKeyReleased(KeyEvent event) {
        // Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.name,eName);
    }

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.password,password);
    }

    @FXML
    void txtUserIdOnKeyReleased(KeyEvent event) {
        //    Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.name,eName);
    }

    @FXML
    void txtUserNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.name,userName);
    }
}