package application;

import java.io.IOException;
import java.sql.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.ProductInventoryController;

public class ProductInventoryClass extends Application {
	private AnchorPane rootLayout;
	private ProductInventoryController controller;
	public DatabaseConnection connection;

	public String url = "jdbc:mysql://localhost/kfc";
	public String user = "root";
	public String pass = "mysql2017";

	private ObservableList<Item> itemData = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {

		try {
			initData();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/ProductInventory.fxml"));
			rootLayout = (AnchorPane) loader.load();
			Scene scene = new Scene(rootLayout, 600, 450);
			scene.getStylesheets().add(getClass().getResource("/view/ProductInventory.css").toExternalForm());
			primaryStage.setScene(scene);
			// pass inventory stage
			controller = loader.getController();
			controller.setProductInventoryClass(this);
			controller.setProductInventoryClassState(primaryStage);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// imageIcon= new
	// ImageIcon(getClass().getResource("/image/"+rs.getString("name")+".PNG"));
	public void initData() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		itemData.clear();
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connection = DriverManager.getConnection(url, user, pass);
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("select * from item;");
		while (rs.next()) {
			itemData.add(new Item(rs.getInt("id"), rs.getString("name"), rs.getInt("price"), rs.getString("status"),
					rs.getInt("category_id")));
		}
		rs.close();
		st.close();
		connection.close();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public ObservableList<Item> getItemData() {
		return itemData;
	}
}
