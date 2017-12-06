package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import view.ProductController;
import view.ProductInventoryController;

public class ProductCategory {
	private Stage stageCategory;

	public String url = "jdbc:mysql://localhost/kfc";
	public String user = "root";
	public String pass = "mysql2017";

	private TextField txtName;
	private Button btnCreate, btnClose;
	private String CategoryName = "";
	private Label lblName;

	public ProductCategory(ProductController controller) {
		stageCategory = new Stage();

		GridPane grid = new GridPane();
		grid.setPrefWidth(250);
		grid.setPrefHeight(150);
		grid.setPadding(new Insets(30, 10, 20, 10));

		lblName = new Label("Нэр: ");
		txtName = new TextField();
		btnCreate = new Button("Үүсгэх");
		btnClose = new Button("Хаах");

		lblName.setPrefWidth(35);
		txtName.setPrefWidth(185);

		HBox hbox = new HBox();
		hbox.setHgrow(btnCreate, Priority.ALWAYS);
		hbox.setHgrow(btnClose, Priority.ALWAYS);
		btnCreate.setPrefWidth(80);
		btnClose.setPrefWidth(60);
		hbox.getChildren().addAll(btnCreate, btnClose);
		hbox.setMargin(btnCreate, new Insets(10, 0, 0, 30));
		hbox.setMargin(btnClose, new Insets(10, 0, 0, 20));
		hbox.setPadding(new Insets(20, 0, 0, 0));

		grid.add(lblName, 0, 0);
		grid.add(txtName, 1, 0);
		grid.add(hbox, 1, 1);

		Scene scene = new Scene(grid, 250, 150);
		stageCategory.setTitle("Шинэ ангилал");
		stageCategory.setScene(scene);
		stageCategory.show();
		btnCreate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// setCategoryName(txtName.getText());
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection connection = DriverManager.getConnection(url, user, pass);
					Statement st = connection.createStatement();
					int rs = st.executeUpdate("INSERT INTO category(name) VALUES('" + txtName.getText() + "');");
					if (rs == 1) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setContentText(txtName.getText() + " шинэ ангилал амжилттай үүсгэгдлээ!");
						alert.setTitle("Амжиллттай!");
						alert.setHeaderText(null);
						alert.showAndWait();
						controller.inventoryController.initCategoryData();
						controller.computeCategory();
					}
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					stageCategory.close();
				}
			}

		});
		btnClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				stageCategory.close();
			}

		});
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}
}