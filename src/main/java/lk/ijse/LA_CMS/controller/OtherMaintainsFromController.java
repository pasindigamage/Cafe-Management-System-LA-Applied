package lk.ijse.LA_CMS.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.LA_CMS.Entity.OtherMaintains;
import lk.ijse.LA_CMS.DAO.custom.impl.OtherMaintainDAOImpl;
import lk.ijse.LA_CMS.util.Regex;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OtherMaintainsFromController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lbldate;

    @FXML
    private JFXButton addOtherMaintains;

    @FXML
    private TextField amount;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton clear;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colOtherMaintainId;

    @FXML
    private TextField date;

    @FXML
    private JFXButton deleteOtherMaintains;

    @FXML
    private Label lblNetTotal;

    @FXML
    private TextField omDescription;

    @FXML
    private Label omId;

    @FXML
    private TableView<OtherMaintains> tblOrderCart;

    @FXML
    private JFXButton updateOtherMaintains;

    public void initialize() {
        setCellValueFactory();
        loadCustomerTable();
        setDate();
        loadNextOrderId();

        omDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                amount.requestFocus();
            }
        });
        tblOrderCart.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                omDescription.setText(newSelection.getDescription());
                amount.setText(String.valueOf(newSelection.getAmount()));



            }
        });
    }

    private void loadNextOrderId() {
        try {
            String currentId = OtherMaintainDAOImpl.currentId();
            String nextId = nextId(currentId);

            omId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("OM");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return "OM" + String.format("%03d", idNum);
        }
        return "OM001";
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lbldate.setText(String.valueOf(now));
    }

    @FXML
    void txtAmountOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.amount,amount);
    }

    private void loadCustomerTable() {
        ObservableList<OtherMaintains> obList = FXCollections.observableArrayList();

        try {
            List<OtherMaintains> otherMaintainsList = OtherMaintainDAOImpl.getAll();
            for (OtherMaintains otherMaintains : otherMaintainsList) {
                OtherMaintains tm = new OtherMaintains(
                        otherMaintains.getId(),
                        otherMaintains.getDescription(),
                        otherMaintains.getDate(),
                        otherMaintains.getAmount()
                );

                obList.add(tm);
            }

            tblOrderCart.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colOtherMaintainId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String idText = omId.getText();
        String descriptionText = omDescription.getText();
        Date date = Date.valueOf(LocalDate.now());
        String amountText = amount.getText();

        OtherMaintains otherMaintains = new OtherMaintains(idText,descriptionText,date,amountText);

        try {
            boolean isSaved = OtherMaintainDAOImpl.save(otherMaintains);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Maintain is Saved!").show();
                loadNextOrderId();
                loadCustomerTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/maintains.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        omDescription.setText("");
        date.setText("");
        amount.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = omId.getText();

        try {
            boolean isDeleted = OtherMaintainDAOImpl.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Maintain is Removed!").show();
                loadCustomerTable();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String idText = omId.getText();
        String descriptionText = omDescription.getText();
        Date date = Date.valueOf(LocalDate.now());
        String amountText = amount.getText();

        OtherMaintains otherMaintains = new OtherMaintains(idText,descriptionText,date,amountText);

        try {
            boolean isUpdated = OtherMaintainDAOImpl.update(otherMaintains);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Maintain is Updated!").show();
                loadCustomerTable();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

}
