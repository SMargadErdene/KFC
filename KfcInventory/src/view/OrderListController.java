package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import application.NewOrderClass;
import application.Order;
import application.OrderListClass;
import application.PrintReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class OrderListController {
	private OrderListClass orderClass;
	// private ProductController productCont;
	
	@FXML
    private ImageView imageViewKFC;
	
	private Stage orderStage;

	@FXML
	private Button btnCreate;

	@FXML
	private Button btnEdit;

	@FXML
	private Button btnDelete;
	
	@FXML
	private Button btnPrint;

	@FXML
	private TableView<Order> orderTableView;

	@FXML
	private TableColumn<Order, Number> idColumn;

	@FXML
	private TableColumn<Order, String> dateColumn;

	@FXML
	private TableColumn<Order, Number> priceColumn;

	@FXML
	private TableColumn<Order, String> statusColumn;

	@FXML
	void actionCreate(ActionEvent event) {
		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int w = (int)screenSize.getWidth();
			int h = (int)screenSize.getHeight();
			NewOrderClass orderClass = new NewOrderClass(w, h);
			orderClass.setSize(w, h);
			orderClass.setLocationRelativeTo(null);
			orderClass.setTitle("Шинэ захиалга");
			orderClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			orderClass.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void actionDelete(ActionEvent event) {
		Order deleteOrder = orderTableView.getSelectionModel().getSelectedItem();
		int id = deleteOrder.getId();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(orderClass.url, orderClass.user, orderClass.pass);
			Statement st = connection.createStatement();
			int res = st.executeUpdate("delete from order_item where order_id="+id);
			res = st.executeUpdate("delete from iorder where id="+id);
			Alert alert;
			if(res == 1) {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Амжилттай!");
				alert.setContentText("Амжилттай устгагдлаа!");
			}else {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Амжилтгүй!");
				alert.setContentText("Устгалт амжилтгүй боллоо!");
			}
			
			alert.setHeaderText(null);
			alert.showAndWait();
			orderClass.initData();
			orderTableView.setItems(orderClass.getItemData());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void actionEdit(ActionEvent event) {
//		try {
//			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//			int w = (int)screenSize.getWidth();
//			int h = (int)screenSize.getHeight();
//			NewOrderClass orderClass = new NewOrderClass(w, h);
//			orderClass.setSize(w, h);
//			orderClass.setLocationRelativeTo(null);
//			orderClass.setTitle("Шинэ захиалга");
//			orderClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			orderClass.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	@FXML
    void actionPrint(ActionEvent event) {
		int id = orderTableView.getSelectionModel().getSelectedItem().getId();
		PrintReport report = new PrintReport(id);
    }

	public void setOrderListClass(OrderListClass orderApp) {
		this.orderClass = orderApp;
//		imageViewKFC.setImage(new Image(OrderListClass.class.getResourceAsStream("/image/kfclogo.png")));
		orderTableView.setItems(orderApp.getItemData());
		if(orderApp.getItemData().size() > 0) {
			orderTableView.getSelectionModel().select(0);
		}
		// initCategoryData();
	}

	public void setOrderListClassState(Stage stage) {
		this.orderStage = stage;
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		// showItemDetails(null);
		// orderTableView.getSelectionModel().selectedItemProperty().addListener((observable,
		// oldValue, newValue) -> showItemDetails(newValue));
	}

}
