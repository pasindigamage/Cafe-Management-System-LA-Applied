package lk.ijse.LA_CMS.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.LA_CMS.BO.BOFactory;
import lk.ijse.LA_CMS.BO.custom.FoodItemsBO;
import lk.ijse.LA_CMS.BO.custom.PlaceOrderBO;
import lk.ijse.LA_CMS.DAO.custom.OrdersDAO;
import lk.ijse.LA_CMS.DAO.custom.PlaceOrderDAO;
import lk.ijse.LA_CMS.DAO.custom.impl.FoodItemsDAOImpl;
import lk.ijse.LA_CMS.DAO.custom.impl.OrdersDAOImpl;
import lk.ijse.LA_CMS.DAO.custom.impl.PlaceOrderDAOImpl;
import lk.ijse.LA_CMS.DTO.FoodItemsDTO;
import lk.ijse.LA_CMS.db.DbConnection;
import lk.ijse.LA_CMS.Entity.*;
import lk.ijse.LA_CMS.view.tdm.CartTM;
import lk.ijse.LA_CMS.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lk.ijse.LA_CMS.controller.LoginFromController.getSignPersonID;
import static lk.ijse.LA_CMS.controller.LoginFromController.signPerson;

public class OrderFromController {


    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblUserName;

    @FXML
    private JFXButton placeButton;

    @FXML
    private TableView<CartTM> tblOrderCart;

    @FXML
    private TextField txtQty;

    @FXML
    private AnchorPane rootNode;

    private double netTotal;

    FoodItemsBO foodItemsBO= (FoodItemsBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.FOODITEMS);
    PlaceOrderBO placeOrderBO= (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.PLACE_ORDER);
    public void initialize() throws ClassNotFoundException {
        setDate();
        getFoodItems();
        loadNextOrderId();
        setCashier();
        setCellValueFactory();
    }

    private void loadNextOrderId() throws ClassNotFoundException {
        try {
            String currentId = placeOrderBO.currentId();
            String nextId = nextId(currentId);

            lblOrderId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.qty,txtQty);
    }

    private String nextId(String currentId) {
        if (currentId != null && currentId.matches("ORD\\d{3}")) {
            String[] split = currentId.split("ORD");
            int idNum = Integer.parseInt(split[1]);
            idNum++;
            return "ORD" + String.format("%03d", idNum);
        }
        return "ORD001"; // default to "O001" if currentId is null or doesn't match the expected format
    }

    private void setCashier(){
        String un = signPerson;
        lblUserName.setText(un);
    }



    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }

    private ObservableList<CartTM> cartList = FXCollections.observableArrayList();

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String ItemCode = lblDescription.getText();
        String description = cmbItemCode.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                if (selectedIndex != -1) {
                    cartList.remove(selectedIndex);
                    tblOrderCart.refresh();
                    calculateNetTotal();
                } else {
                    new Alert(Alert.AlertType.WARNING, "No item selected to remove!").show();
                }
            }
        });

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            if (ItemCode.equals(cartList.get(i).getCode())) {
                qty += cartList.get(i).getQty();
                total = unitPrice * qty;

                cartList.get(i).setQty(qty);
                cartList.get(i).setTotal(total);

                tblOrderCart.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }

        CartTM cartTm = new CartTM(ItemCode, description, qty, unitPrice, total, btnRemove);
        System.out.println(ItemCode);
        System.out.println(description);

        System.out.println(qty);
        System.out.println(unitPrice);

        System.out.println(total);
        System.out.println(btnRemove);


        cartList.add(cartTm);

        tblOrderCart.setItems(cartList);
        txtQty.setText("");
        calculateNetTotal();
    }

    private void calculateNetTotal() {
        netTotal = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }


    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException, JRException, ClassNotFoundException {
        String orderId = lblOrderId.getText();
        String userId = getSignPersonID;
        Date date = Date.valueOf(LocalDate.now());
        double amount = Double.parseDouble(lblNetTotal.getText());

        var order = new Order(orderId, userId, date, amount);
        System.out.println(orderId);
        System.out.println(userId);

        System.out.println(date);
        System.out.println(amount);



        List<OrderDetail> odList = new ArrayList<>();
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTM tm = cartList.get(i);

            OrderDetail od = new OrderDetail(
                    orderId,
                    tm.getCode(),
                    tm.getQty()
            );
            odList.add(od);
        }
        PlaceOrder po = new PlaceOrder(order, odList);
        boolean isPlaced = placeOrderBO.placeOrder(po); //  OrdersRepo.save(order);

        if(isPlaced) {
            printBill();
            loadNextOrderId();
            cartList.clear();
            tblOrderCart.setItems(cartList);
            cmbItemCode.getSelectionModel().clearSelection();
            lblDescription.setText("");
            lblNetTotal.setText("");
            lblUnitPrice.setText("");
            txtQty.clear();



            new Alert(Alert.AlertType.CONFIRMATION, "Order placed successfully!").show();
            loadNextOrderId();
        } else {
            new Alert(Alert.AlertType.WARNING,"Failed to place order!").show();
        }
    }

    private void getFoodItems() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = foodItemsBO.getIds();

            for (String id : idList) {
                obList.add(id);
            }
            cmbItemCode.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws ClassNotFoundException {
        String itemCodeValue = cmbItemCode.getValue();
        try {
            FoodItemsDTO foodItems = foodItemsBO.searchByCode(itemCodeValue);
            if (foodItems != null) {
                lblDescription.setText(foodItems.getId());
                lblUnitPrice.setText(String.valueOf(foodItems.getUnitPrice()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.qty,txtQty)) return false;

        return true;
    }
    @FXML
    void txtQtyOnAction(ActionEvent event) {
        Regex.setTextColor(lk.ijse.LA_CMS.util.TextField.qty,txtQty);
    }

    public void userIdSearchOnAction(MouseEvent mouseEvent) {

    }
    public void printBill() throws JRException, SQLException, ClassNotFoundException {

        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/Cafe_Report.jrxml");
        JRDesignQuery jrDesignQuery = new JRDesignQuery();
        jrDesignQuery.setText("SELECT Orders.id AS OrderID,\n" +
                "       Orders.userId AS UserID,\n" +
                "       Orders.date AS OrderDate,\n" +
                "       Orders.Amount AS OrderAmount,\n" +
                "       FoodItems.id AS FoodItemID,\n" +
                "       FoodItems.description AS Description,\n" +
                "       FoodItems.unitPrice AS UnitPrice,\n" +
                "       orderDetails.qty AS QuantityOrdered\n" +
                "FROM Orders\n" +
                "INNER JOIN orderDetails ON Orders.id = orderDetails.orderId\n" +
                "INNER JOIN FoodItems ON orderDetails.foodItemId = FoodItems.id\n" +
                "WHERE Orders.id = (SELECT MAX(id) FROM Orders);");
        jasperDesign.setQuery(jrDesignQuery);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
}
