package lk.ijse.LA_CMS.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.LA_CMS.DAO.custom.KitchenWareDAO;
import lk.ijse.LA_CMS.DAO.custom.KitchenWareMaintainDAO;
import lk.ijse.LA_CMS.Entity.KitchenWare;
import lk.ijse.LA_CMS.Entity.KitchenWareMaintains;
import lk.ijse.LA_CMS.DAO.custom.impl.KitchenWareMaintainDAOImpl;
import lk.ijse.LA_CMS.DAO.custom.impl.KitchenWareDAOImpl;
import lk.ijse.LA_CMS.util.Regex;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class KitchenWareMaintainsFromController {

    @FXML
    private Label lblDate;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXButton addKitchenWareMaintains;

    @FXML
    private TextField amount;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXComboBox<String> cmbIKitchenWareId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colKitchenWareId;

    @FXML
    private TableColumn<?, ?> colKitchenWareMaintainId;

    @FXML
    private TextField date;

    @FXML
    private JFXButton deleteKitchenWareMaintains;

    @FXML
    private TextField kmDescription;

    @FXML
    private Label kmId;

    @FXML
    private Label lblNetTotal;

    @FXML
    private TableView<KitchenWareMaintains> tblOrderCart;

    @FXML
    private JFXButton updateKitchenWareMaintains;

    KitchenWareMaintainDAO kitchenWareMaintainDAO=new KitchenWareMaintainDAOImpl();
    KitchenWareDAO kitchenWareDAO=new KitchenWareDAOImpl();
    public void initialize() throws ClassNotFoundException {
        setDate();
        getKitchenWareIds();
        loadInventoryTable();
        setCellValueFactory();
        loadNextOrderId();

        kmDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                amount.requestFocus();
            }
        });
        tblOrderCart.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lblKId.setText(newSelection.getKitchenWareId());
                kmDescription.setText(newSelection.getDescription());
                amount.setText(String.valueOf(newSelection.getAmount()));
                cmbIKitchenWareId.setValue(newSelection.getKitchenWareId());
                kmId.setText(newSelection.getId());



            }
        });
    }
    private void loadNextOrderId() throws ClassNotFoundException {
        try {
            String currentId = kitchenWareDAO.currentId();
            String nextId = nextId(currentId);

            kmId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("KWM");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return "KWM" + String.format("%03d", idNum);
        }
        return "KWM001";
    }

    private void setCellValueFactory() {
        colKitchenWareMaintainId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colKitchenWareId.setCellValueFactory(new PropertyValueFactory<>("kitchenWareId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private void loadInventoryTable() throws ClassNotFoundException {
        ObservableList<KitchenWareMaintains> obList = FXCollections.observableArrayList();

        try {
            List<KitchenWareMaintains> kitchenWareMaintainsList = kitchenWareMaintainDAO.getAll();
            for (KitchenWareMaintains kitchenWareMaintains : kitchenWareMaintainsList) {
                KitchenWareMaintains tm = new KitchenWareMaintains(
                        kitchenWareMaintains.getId(),
                        kitchenWareMaintains.getKitchenWareId(),
                        kitchenWareMaintains.getDescription(),
                        kitchenWareMaintains.getDate(),
                        kitchenWareMaintains.getAmount()
                );

                obList.add(tm);
            }

            tblOrderCart.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws ClassNotFoundException {
        String idText = kmId.getText();
        String kitchenWareIdValue = lblKId.getText();
        String descriptionText = kmDescription.getText();
        String dateText = lblDate.getText();
        String amountText = amount.getText();

        KitchenWareMaintains kitchenWareMaintains = new KitchenWareMaintains(idText,kitchenWareIdValue,descriptionText,dateText,amountText);

        try {
            boolean isSaved = kitchenWareMaintainDAO.save(kitchenWareMaintains);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "KitchenWare Maintain is Saved!").show();
                loadNextOrderId();
                loadInventoryTable();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws ClassNotFoundException {
        clearFields();
    }

    private void clearFields() throws ClassNotFoundException {
        kmDescription.setText("");
        amount.setText("");
        cmbIKitchenWareId.setValue("");
        loadNextOrderId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = kmId.getText();

        try {
            boolean isDeleted = kitchenWareMaintainDAO.delete(id);
            if (isDeleted) {
                KitchenWareMaintains kitchenWareMaintains = (KitchenWareMaintains) tblOrderCart.getSelectionModel().getSelectedItem();
                if (kitchenWareMaintains != null) {
                    tblOrderCart.getItems().remove(kitchenWareMaintains);
                    new Alert(Alert.AlertType.CONFIRMATION, "kitchenWareMaintains Deleted!").show();
                    clearFields();
                    loadInventoryTable();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete kitchenWareMaintains!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();        }

    }

    @FXML
    private Label lblKId;

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String idText = kmId.getText();
        String kitchenWareIdValue = lblKId.getText();
        String descriptionText = kmDescription.getText();
        String dateText = lblDate.getText();
        String amountText = amount.getText();

        KitchenWareMaintains kitchenWareMaintains = new KitchenWareMaintains(idText,kitchenWareIdValue,descriptionText,dateText,amountText);

        try {
            boolean isUpdated = kitchenWareMaintainDAO.update(kitchenWareMaintains);
            if (isUpdated) {
                KitchenWareMaintains selectedItem = (KitchenWareMaintains) tblOrderCart.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    int selectedIndex = tblOrderCart.getItems().indexOf(selectedItem);
                    tblOrderCart.getItems().set(selectedIndex, selectedItem);
                    new Alert(Alert.AlertType.CONFIRMATION, "kitchenWareMaintains updated successfully!").show();
                    clearFields();
                    loadInventoryTable();

                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update kitchenWareMaintains!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while updating kitchenWareMaintains: " + e.getMessage()).show();
        }
    }

    private void getKitchenWareIds() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = kitchenWareDAO.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbIKitchenWareId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws ClassNotFoundException {
        String kid = cmbIKitchenWareId.getValue();
        try {
            KitchenWare kitchenWare = kitchenWareDAO.searchByCode(kid);
            if (kitchenWare != null) {
                lblKId.setText(kitchenWare.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //    public boolean isValied(){
//        if (!) return false;
//        if (!) return false;
//        if (!) return false;
//        if (!) return false;
//
//        return true;
//    }
    @FXML
    void txtAmountOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.amount,amount);
    }

}
