package lk.ijse.LA_CMS.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.LA_CMS.BO.BOFactory;
import lk.ijse.LA_CMS.BO.custom.FoodItemsBO;
import lk.ijse.LA_CMS.DAO.custom.FoodItemsDAO;
import lk.ijse.LA_CMS.DTO.FoodItemsDTO;
import lk.ijse.LA_CMS.Entity.FoodItems;
import lk.ijse.LA_CMS.DAO.custom.impl.FoodItemsDAOImpl;
import lk.ijse.LA_CMS.util.Regex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FoodItemFromController {

    @FXML
    private JFXButton addIngredians;

    @FXML
    private JFXButton addMenu;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton clear;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TextField fooditemSearch;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private JFXButton deleteMenu;

    @FXML
    private TextField fAmount;

    @FXML
    private TextField fDescription;

    @FXML
    private Label fID;

    @FXML
    private AnchorPane rootNode1;

    @FXML
    private TableView<FoodItems> tblMenu;

    @FXML
    private JFXButton updateMenu;

    FoodItemsBO foodItemsBO= (FoodItemsBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.FOODITEMS);
    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadFoodTable();
        loadNextOrderId();

        fDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                fAmount.requestFocus();
            }
        });

        fAmount.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                fQty.requestFocus();
            }
        });
        tblMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fAmount.setText(String.valueOf(newSelection.getUnitPrice()));
                fDescription.setText(newSelection.getDescription());
                fQty.setText(String.valueOf(newSelection.getQtyOnHand()));

            }
        });
    }

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TextField fQty;

    private void loadNextOrderId() throws ClassNotFoundException {
        try {
            String currentId = foodItemsBO.currentId();
            String nextId = nextId(currentId);

            fID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("F");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return "F" + String.format("%03d", idNum);
        }
        return "F001";
    }

    private void loadFoodTable() throws ClassNotFoundException {
        ObservableList<FoodItems> obList = FXCollections.observableArrayList();

        try {
            List<FoodItemsDTO> foodItemsList = foodItemsBO.getAll();
            for (FoodItemsDTO foodItems : foodItemsList) {
                FoodItems tm = new FoodItems(
                        foodItems.getId(),
                        foodItems.getDescription(),
                        foodItems.getUnitPrice(),
                        foodItems.getQtyOnHand()
                );

                obList.add(tm);
            }

            tblMenu.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }



    @FXML
    void IdSearchOnAction(ActionEvent event) throws ClassNotFoundException {
        String description = fooditemSearch.getText();

        try {
            FoodItemsDTO foodItems = foodItemsBO.searchByDescription(description);

            if (foodItems != null) {
                fID.setText(foodItems.getId());
                fDescription.setText(foodItems.getDescription());
                fAmount.setText(String.valueOf(foodItems.getUnitPrice()));
                fQty.setText(String.valueOf(foodItems.getQtyOnHand()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws ClassNotFoundException {
        String idText = fID.getText();
        String descriptionText = fDescription.getText();
        double amountText = Double.parseDouble(fAmount.getText());
        int qtyText = Integer.parseInt(fQty.getText());

        FoodItemsDTO foodItems = new FoodItemsDTO(idText,descriptionText,amountText,qtyText);

        try {
            boolean isSaved = foodItemsBO.save(foodItems);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Menu Item is Saved!").show();
                clearFields();
                loadNextOrderId();
                loadFoodTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAnchorpaneChnageOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/LA_CMS/Removed/addIngredians.fxml"));
        Parent rootNode = loader.load();
        rootNode1.getChildren().clear();
        rootNode1.getChildren().add(rootNode);

    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws ClassNotFoundException {
        clearFields();
    }

    private void clearFields() throws ClassNotFoundException {
        fDescription.setText("");
        fAmount.setText("");
        fQty.setText("");
        loadNextOrderId();
        fooditemSearch.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = fID.getText();

        try {
            boolean isDeleted = foodItemsBO.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Menu Item is Deleted!").show();
                loadFoodTable();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String idText = fID.getText();
        String descriptionText = fDescription.getText();
        double amountText = Double.parseDouble(fAmount.getText());
        int qtyText = Integer.parseInt(fQty.getText());

        FoodItemsDTO foodItems = new FoodItemsDTO(idText,descriptionText,amountText,qtyText);

        try {
            boolean isUpdated = foodItemsBO.update(foodItems);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Menu Item is Updated!").show();
                loadFoodTable();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.amount,fAmount)) return false;

        return true;
    }

    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        isValied();
        //Regex.setTextColor(lk.ijse.buddiescafe.util.TextField.amount,fAmount);
    }

}