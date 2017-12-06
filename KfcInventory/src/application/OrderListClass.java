package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.OrderListController;

public class OrderListClass extends Application {
	public String url = "jdbc:mysql://localhost/kfc";
	public String user = "root";
	public String pass = "mysql2017";
	
	private AnchorPane rootLayout;
	private OrderListController orderController;
	private ObservableList<Order> orderData = FXCollections.observableArrayList();
	
	public void initData() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		orderData.clear();
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection(url, user, pass);
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("select * from iorder;");
		while (rs.next()) {
			orderData.add(new Order(rs.getInt("id"), rs.getString("date"), rs.getInt("total_price"), rs.getString("status")));
		}
		rs.close();
		st.close();
		connection.close();
	}
	
	public ObservableList<Order> getItemData() {
		return orderData;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			initData();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/OrderList.fxml"));
			rootLayout = (AnchorPane) loader.load();
			rootLayout.getStylesheets().add(getClass().getResource("/view/OrderListStyle.css").toExternalForm());
			Scene scene = new Scene(rootLayout);
			primaryStage.setTitle("Кассын гүйлгээ");
			primaryStage.setScene(scene);
			// pass inventory stage
			orderController = loader.getController();
			orderController.setOrderListClass(this);
			orderController.setOrderListClassState(primaryStage);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
