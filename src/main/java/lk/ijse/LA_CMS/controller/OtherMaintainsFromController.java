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
import lk.ijse.LA_CMS.BO.BOFactory;
import lk.ijse.LA_CMS.BO.custom.OtherMaintainBO;
import lk.ijse.LA_CMS.DAO.custom.OtherMaintainDAO;
import lk.ijse.LA_CMS.DTO.OtherMaintainsDTO;
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
    private TableView<OtherMaintainsDTO> tblOrderCart;

    @FXML
    private JFXButton updateOtherMaintains;

    OtherMaintainBO otherMaintainBO= (OtherMaintainBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.OTHER_MAINTAINS);
    public void initialize() throws ClassNotFoundException {
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

    private void loadNextOrderId() throws ClassNotFoundException {
        try {
            String currentId = otherMaintainBO.currentId();
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

    private void loadCustomerTable() throws ClassNotFoundException {
        ObservableList<OtherMaintainsDTO> obList = FXCollections.observableArrayList();

        try {
            List<OtherMaintainsDTO> otherMaintainsList = otherMaintainBO.getAll();
            for (OtherMaintainsDTO otherMaintains : otherMaintainsList) {
                OtherMaintainsDTO tm = new OtherMaintainsDTO(
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
    void btnAddOnAction(ActionEvent event) throws ClassNotFoundException {
        String idText = omId.getText();
        String descriptionText = omDescription.getText();
        Date date = Date.valueOf(LocalDate.now());
        String amountText = amount.getText();

        OtherMaintainsDTO otherMaintains = new OtherMaintainsDTO(idText,descriptionText,date,amountText);

        try {
            boolean isSaved = otherMaintainBO.save(otherMaintains);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Maintain is Saved!").show();
                loadNextOrderId();
                loadCustomerTable();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        omDescription.clear();
        amount.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        ObservableList<OtherMaintainsDTO> selectedDTOS = tblOrderCart.getSelectionModel().getSelectedItems();
        if (selectedDTOS.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select location(s) to delete!").show();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected Maintains?");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.showAndWait();

        if (confirmationAlert.getResult() == ButtonType.OK) {
            try {
                for (OtherMaintainsDTO otherMaintainsDTO : selectedDTOS) {
                    boolean isDeleted = otherMaintainBO.delete(otherMaintainsDTO.getId());
                    if (isDeleted) {
                        tblOrderCart.getItems().remove(otherMaintainsDTO);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete : " + otherMaintainsDTO.getId()).show();
                    }
                }
                new Alert(Alert.AlertType.CONFIRMATION, " deleted successfully!").show();
                clearFields();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting : " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String idText = omId.getText();
        String descriptionText = omDescription.getText();
        Date date = Date.valueOf(LocalDate.now());
        String amountText = amount.getText();

        OtherMaintainsDTO otherMaintains = new OtherMaintainsDTO(idText,descriptionText,date,amountText);

        try {
            boolean isUpdated = otherMaintainBO.update(otherMaintains);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Maintain is Updated!").show();
                loadCustomerTable();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
}
