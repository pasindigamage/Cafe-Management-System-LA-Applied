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
import lk.ijse.LA_CMS.BO.custom.EmployeeBO;
import lk.ijse.LA_CMS.BO.custom.Impl.EmployeeBOImpl;
import lk.ijse.LA_CMS.DTO.EmployeeDTO;
import lk.ijse.LA_CMS.Entity.Employee;
import lk.ijse.LA_CMS.util.Regex;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFromController {

    @FXML
    private JFXButton addEmployee;

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
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private JFXButton deleteEmployee;

    @FXML
    private TextField eAddress;

    @FXML
    private TextField eContact;

    @FXML
    private TextField eEmail;

    @FXML
    private Label eID;

    @FXML
    private TextField eName;

    @FXML
    private TextField ePossition;

    @FXML
    private TableView<Employee> tblEmployee;

    @FXML
    private TextField eIDSearch;

    @FXML
    private JFXButton updateEmployee;

    @FXML
    private AnchorPane rootNode;

    private List<Employee> employeeList = new ArrayList<>();

    EmployeeBO employeeBO= (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.EMPLOYEE);
    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadEmployeeTable();
        loadNextOrderId();

        ePossition.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                eName.requestFocus();
            }
        });
        eName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                eAddress.requestFocus();
            }
        });

        eAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                eContact.requestFocus();
            }
        });

        eContact.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                eEmail.requestFocus();
            }
        });

        eEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addEmployee.requestFocus();
            }
        });

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                eName.setText(newSelection.getName());
                eAddress.setText(newSelection.getAddress());
                ePossition.setText(newSelection.getPosition());
                eContact.setText(newSelection.getContact());
                eEmail.setText(newSelection.getEmail());
            }
        });
    }

    private void loadNextOrderId() throws ClassNotFoundException {
        try {
            String currentId = employeeBO.currentId();
            String nextId = generateNextEmployeeId(currentId);

            eID.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextEmployeeId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("E");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return "E" + String.format("%03d", idNum);
        }
        return "E001";
    }

    private void loadEmployeeTable() throws ClassNotFoundException {
        ObservableList<Employee> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDTO> employeeList = employeeBO.getAll();
            for (EmployeeDTO employee : employeeList) {
                Employee tm = new Employee(
                        employee.getId(),
                        employee.getName(),
                        employee.getPosition(),
                        employee.getAddress(),
                        employee.getEmail(),
                        employee.getContact()
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
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private List<EmployeeDTO> getAllEmployee() throws ClassNotFoundException {
        List<EmployeeDTO> employeeList = null;
        try {
            employeeList = employeeBO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    @FXML
    void IdSearchOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = eIDSearch.getText();

        try {
            Employee employee = employeeBO.searchByCode(id);

            if (employee != null) {
                eID.setText(id);
                ePossition.setText(employee.getPosition());
                eName.setText(employee.getName());
                eAddress.setText(employee.getAddress());
                eEmail.setText(employee.getEmail());
                eContact.setText(employee.getContact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = eID.getText();
        String name = eName.getText();
        String address = eAddress.getText();
        String contact = eContact.getText();
        String position =ePossition.getText();
        String email = eEmail.getText();

        EmployeeDTO employee = new EmployeeDTO(id,name,position,address,email,contact);

        try {
            if(isValied()){boolean isSaved = employeeBO.save(employee);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Saved!").show();
                    clearFields();
                    loadNextOrderId();
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
        eAddress.setText("");
        eContact.setText("");
        eEmail.setText("");
        ePossition.setText("");
        eName.setText("");
        eIDSearch.setText("");
        loadNextOrderId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = eID.getText();

        try {
            boolean isDeleted = employeeBO.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted!").show();
                clearFields();
                loadEmployeeTable();
                loadNextOrderId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String idText = eID.getText();
        String nameText= eName.getText();
        String possitionText = ePossition.getText();
        String addressText = eAddress.getText();
        String emailText = eEmail.getText();
        String contactText = eContact.getText();

        EmployeeDTO employee = new EmployeeDTO(idText,nameText,possitionText,addressText,emailText,contactText);

        try {
            boolean isUpdated = employeeBO.update(employee);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated!").show();
                clearFields();
                loadEmployeeTable();
                loadNextOrderId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public boolean isValied(){
        if (! Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.address,eAddress)) return false;
        if (! Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.contact,eContact)) return false;
        if (! Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.email,eEmail)) return false;
        if (!Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.name,eName)) return false;

        return true;
    }
    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.address,eAddress);
    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.contact,eContact);
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.email,eEmail);
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.name,eName);
    }
}