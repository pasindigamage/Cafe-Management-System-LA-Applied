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
import lk.ijse.LA_CMS.DAO.custom.SupplierDAO;
import lk.ijse.LA_CMS.Entity.KitchenWare;
import lk.ijse.LA_CMS.Entity.Supplier;
import lk.ijse.LA_CMS.DAO.custom.impl.KitchenWareDAOImpl;
import lk.ijse.LA_CMS.DAO.custom.impl.SupplierDAOImpl;
import lk.ijse.LA_CMS.util.Regex;

import java.sql.SQLException;
import java.util.List;

public class KitchenWareFromController {

    @FXML
    private TextField Description;

    @FXML
    private TextField Qty;

    @FXML
    private JFXButton addKitchenWare;

    @FXML
    private JFXButton clear;

    @FXML
    private JFXComboBox<String> cmbISupplierId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colKitchenWareId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private JFXButton deleteKitchenWare;

    @FXML
    private Label kId;

    @FXML
    private Label lblNetTotal;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<KitchenWare> tblOrderCart;

    @FXML
    private JFXButton updateKitchenWare;

    KitchenWareDAO kitchenWareDAO=new KitchenWareDAOImpl();
    SupplierDAO supplierDAO=new SupplierDAOImpl();
    public void initialize() throws ClassNotFoundException {
        getSupplierIds();
        loadInventoryTable();
        setCellValueFactory();
        loadNextOrderId();

        Description.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Qty.requestFocus();
            }
        });
        tblOrderCart.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Description.setText(newSelection.getDescription());
                Qty.setText(newSelection.getQty());
                cmbISupplierId.setValue(newSelection.getSupplierId());
                kId.setText(newSelection.getId());

            }
        });
    }

    private void loadNextOrderId() throws ClassNotFoundException {
        try {
            String currentId = kitchenWareDAO.currentId();
            String nextId = nextId(currentId);

            kId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("KW");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return "KW" + String.format("%03d", idNum);
        }
        return "KW001";

    }

    private void setCellValueFactory() {

        colKitchenWareId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void loadInventoryTable() throws ClassNotFoundException {
        ObservableList<KitchenWare> obList = FXCollections.observableArrayList();

        try {
            List<KitchenWare> kitchenWareList = kitchenWareDAO.getAll();
            for (KitchenWare kitchenWare : kitchenWareList) {
                KitchenWare tm = new KitchenWare(
                        kitchenWare.getId(),
                        kitchenWare.getSupplierId(),
                        kitchenWare.getDescription(),
                        kitchenWare.getQty()
                );

                obList.add(tm);
            }

            tblOrderCart.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getSupplierIds() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = supplierDAO.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbISupplierId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private TextField kitchenWareIdSearch;

    @FXML
    void IdSearchOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = kitchenWareIdSearch.getText();

        try {
            KitchenWare kitchenWare = kitchenWareDAO.searchByCode(id);

            if (kitchenWare != null) {
                kId.setText(kitchenWare.getId());
                Description.setText(kitchenWare.getDescription());
                Qty.setText(kitchenWare.getQty());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws ClassNotFoundException {
        String idText = kId.getText();
        String cmbISupplierIdValue = lblsId.getText();
        String descriptionText = Description.getText();
        String qtyText = Qty.getText();
        KitchenWare kitchenWare = new KitchenWare(idText,cmbISupplierIdValue,descriptionText,qtyText);

        try {
            boolean isSaved = kitchenWareDAO.save(kitchenWare);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "KitchenWare is Saved!").show();
                loadNextOrderId();
                clearFields();
                loadInventoryTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws ClassNotFoundException {
        clearFields();
    }

    private void clearFields() throws ClassNotFoundException {
        Description.setText("");
        cmbISupplierId.setValue("");
        Qty.setText("");
        //kitchenWareIdSearch.setText("");
        cmbISupplierId.setValue("");
        lblsId.setText("");
        loadNextOrderId();
        loadInventoryTable();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = kId.getText();
        try {
            boolean isDeleted = kitchenWareDAO.delete(id);
            if (isDeleted) {
                KitchenWare kitchenWare = (KitchenWare) tblOrderCart.getSelectionModel().getSelectedItem();
                if (kitchenWare != null) {
                    tblOrderCart.getItems().remove(kitchenWare);
                    new Alert(Alert.AlertType.CONFIRMATION, "KitchenWare Deleted!").show();
                    clearFields();
                    loadInventoryTable();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete kitchenWare!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String idText = kId.getText();
        String supplierIdValue = lblsId.getText();
        String descriptionText = Description.getText();
        String qtyText = Qty.getText();

        KitchenWare kitchenWare = new KitchenWare(idText,supplierIdValue,descriptionText,qtyText);

        try {
            boolean isUpdated = kitchenWareDAO.update(kitchenWare);
            if (isUpdated) {
                KitchenWare selectedItem = (KitchenWare) tblOrderCart.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    int selectedIndex = tblOrderCart.getItems().indexOf(selectedItem);
                    tblOrderCart.getItems().set(selectedIndex, selectedItem);
                    new Alert(Alert.AlertType.CONFIRMATION, "KitchenWare updated successfully!").show();
                    clearFields();
                    loadInventoryTable();

                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update KitchenWare!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while updating KitchenWare: " + e.getMessage()).show();
        }
    }

    @FXML
    private Label lblsId;

    @FXML
    void cmbItemOnAction(ActionEvent event) throws ClassNotFoundException {
        String sid = cmbISupplierId.getValue();
        try {
            Supplier supplier = supplierDAO.searchByDescription(sid);
            if (supplier != null) {
                lblsId.setText(supplier.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.qty,Qty)) return false;
        //  if (!) return false;
        //  if (!) return false;
        //  if (!) return false;

        return true;
    }
    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.qty,Qty);
    }
}
