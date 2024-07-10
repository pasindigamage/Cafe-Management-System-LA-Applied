package lk.ijse.LA_CMS.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.LA_CMS.DAO.custom.ChangePasswordDAO;
import lk.ijse.LA_CMS.DAO.custom.impl.ChangePasswordDAOImpl;
import lk.ijse.LA_CMS.DTO.ChangePasswordDTO;
import lk.ijse.LA_CMS.util.Regex;

import java.sql.SQLException;

public class ChangePasswordFromController {

    @FXML
    private JFXButton cancel;

    @FXML
    private TextField employeeId;

    @FXML
    private TextField newPassword;

    @FXML
    private JFXButton save;

    @FXML
    private TextField userName;

    public void initialize(){
        userName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                employeeId.requestFocus();
            }
        });
        employeeId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                newPassword.requestFocus();
            }
        });

    }

    @FXML
    void cancelOnAction(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String uname = userName.getText();
        String eid = employeeId.getText();
        String newPw = newPassword.getText();

        if(isValied()) {
            ChangePasswordDAO changePasswordDAO = new ChangePasswordDAOImpl();
            changePasswordDAO.save(new ChangePasswordDTO(uname,eid,newPw));
            new Alert(Alert.AlertType.CONFIRMATION, "User saved successfully!").show();
            userName.clear();
            employeeId.clear();
            newPassword.clear();


        }else {
            new Alert(Alert.AlertType.ERROR, "Password update failed. User not found").show();
        }
    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.password,newPassword)) return false;
        if (!Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.useName,userName)) return false;

        return true;
    }

    @FXML
    void txtEidOnKeyReleased(KeyEvent event) {
        //Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.password,userName);
    }

    @FXML
    void txtNewPwOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.password,newPassword);
    }

    @FXML
    void txtUnameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.useName,userName);
    }
}