package lk.ijse.LA_CMS.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.LA_CMS.BO.BOFactory;
import lk.ijse.LA_CMS.BO.custom.SupplierBO;
import lk.ijse.LA_CMS.DAO.custom.SupplierDAO;
import lk.ijse.LA_CMS.DTO.SupplierDTO;
import lk.ijse.LA_CMS.Entity.Supplier;
import lk.ijse.LA_CMS.DAO.custom.impl.SupplierDAOImpl;
import lk.ijse.LA_CMS.util.Regex;

import java.sql.SQLException;
import java.util.List;

public class SupplierFromController {

    @FXML
    private JFXButton addSupplier;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton clear;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMail;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private JFXButton deleteSupplier;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField sAddress;

    @FXML
    private TextField sContact;

    @FXML
    private TextField sEmail;

    @FXML
    private Label sID;

    @FXML
    private TextField sNIC;

    @FXML
    private TextField sName;

    @FXML
    private TableView<Supplier> tblEmployee;

    @FXML
    private JFXButton updateSuppler;

    SupplierBO supplierBO= (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.SUPPLIER);

    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadEmployeeTable();
        loadNextOrderId();

        sNIC.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sName.requestFocus();
            }
        });
        sName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sAddress.requestFocus();
            }
        });

        sAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sContact.requestFocus();
            }
        });

        sContact.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sEmail.requestFocus();
            }
        });
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                sNIC.setText(String.valueOf(newSelection.getNic()));
                sName.setText(newSelection.getName());
                sAddress.setText(String.valueOf(newSelection.getCompanyAddress()));
                sContact.setText(String.valueOf(newSelection.getContact()));
                sEmail.setText(String.valueOf(newSelection.getEmail()));
            }
        });
    }

    private void loadNextOrderId() throws ClassNotFoundException {
        try {
            // Check if sID label is properly initialized
            if (sID != null) {
                String currentId = supplierBO.currentId();
                String nextId = nextId(currentId);

                // Update the text of the sID label
                sID.setText(nextId);
            } else {
                // Log an error or display a message if sID is null
                System.err.println("Error: sID label is null.");
            }
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("SU");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return "SU" + String.format("%03d", idNum);
        }
        return "SU001";
    }

    private void loadEmployeeTable() throws ClassNotFoundException {
        ObservableList<Supplier> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDTO> supplierList = supplierBO.getAll();
            for (SupplierDTO supplier : supplierList) {
                Supplier tm = new Supplier(
                        supplier.getId(),
                        supplier.getNic(),
                        supplier.getName(),
                        supplier.getCompanyAddress(),
                        supplier.getEmail(),
                        supplier.getContact()
                );

                obList.add(tm);
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("companyAddress"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    @FXML
    private TextField sIDSearch;

    @FXML
    void IdSearchOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = sIDSearch.getText();

        try {
            SupplierDTO supplier = supplierBO.searchByCode(id);

            if (supplier != null) {
                sID.setText(supplier.getId());
                sNIC.setText(supplier.getNic());
                sName.setText(supplier.getName());
                sAddress.setText(supplier.getCompanyAddress());
                sEmail.setText(supplier.getEmail());
                sContact.setText(String.valueOf(supplier.getContact()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws ClassNotFoundException {
        String idText = sID.getText();
        String nicText = sNIC.getText();
        String nameText = sName.getText();
        String addressText = sAddress.getText();
        String emailText = sEmail.getText();
        String contactText =sContact.getText();

        SupplierDTO supplier = new SupplierDTO(idText,nicText,nameText,addressText,emailText,contactText);

        try {
            if (isValied()){   boolean isSaved = supplierBO.save(supplier);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved!").show();
                    loadNextOrderId();
                    clearFields();
                    loadEmployeeTable();
                }}

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event) throws ClassNotFoundException {
        clearFields();
    }

    private void clearFields() throws ClassNotFoundException {
        sNIC.setText("");
        sName.setText("");
        sAddress.setText("");
        sEmail.setText("");
        sContact.setText("");
        sIDSearch.setText("");
        loadNextOrderId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = sID.getText();

        try {
            boolean isDeleted = supplierBO.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted!").show();
                loadNextOrderId();
                loadEmployeeTable();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String idText = sID.getText();
        String nicText = sNIC.getText();
        String nameText = sName.getText();
        String addressText = sAddress.getText();
        String emailText = sEmail.getText();
        String contactText = sContact.getText();

        SupplierDTO supplier = new SupplierDTO(idText,nicText,nameText,addressText,emailText,contactText);

        try {
            boolean isUpdated = supplierBO.update(supplier);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated!").show();
                clearFields();
                loadEmployeeTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.address,sAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.contact,sContact)) return false;
        if (!Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.email,sEmail)) return false;
        if (!Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.name,sName)) return false;
        //  if (!Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.nic,sNIC)) return false;


        return true;
    }
    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.address,sAddress);
    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.contact,sContact);
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.email,sEmail);
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.name,sName);
    }

    @FXML
    void txtNicOnKeyReleased(KeyEvent event) {
        //Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.nic,sNIC);
    }
}
