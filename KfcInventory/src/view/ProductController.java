package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.ProductCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProductController {

	public String url = "jdbc:mysql://localhost/kfc";
	public String user = "root";
	public String pass = "mysql2017";

	private Stage inventoryStage;
	public ProductInventoryController inventoryController;

	private Stage stageProductController;

	@FXML
	private Button handle_picture;
	@FXML
	private Button btnNewCategory;
	@FXML
	private Label lblItemNumber;
	@FXML
	private TextField txtUnitPrice;
	@FXML
	private TextArea txtDescription;
	@FXML
	private TextField txtPicture;
	@FXML
	private Button btnCreate;
	@FXML
	private Button btnClose;
	@FXML
	private TextField txtItemName;
	@FXML
	public ComboBox cboxCategory;
	@FXML
	private ImageView imageView;

	@FXML
	void actionCloseProduct(ActionEvent event) {
		inventoryStage.close();
	}

	@FXML
	void actionCreateProduct(ActionEvent event) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(url, user, pass);
			Statement st = connection.createStatement();

			String name = (String) cboxCategory.getSelectionModel().getSelectedItem();

			ResultSet res = st.executeQuery("select id from category where name='" + name + "';");
			int cat_id = 0;
			if (res.next()) {
				cat_id = res.getInt("id");
			}
			System.out.println("INSERT INTO item(name, price, status, category_id) VALUES('" + txtItemName.getText()
					+ "', " + Integer.parseInt(txtUnitPrice.getText()) + ",'" + txtDescription.getText() + "'," + cat_id
					+ ");");
			int rs = st
					.executeUpdate("INSERT INTO item(name, price, status, category_id) VALUES('" + txtItemName.getText()
							+ "', " + txtUnitPrice.getText() + ",'" + txtDescription.getText() + "'," + cat_id + ");");
			if (rs == 1) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText(txtItemName.getText() + " шинэ бүтээгдэхүүн амжилттай үүсгэгдлээ!");
				alert.setTitle("Амжилттай!");
				alert.setHeaderText(null);
				alert.showAndWait();
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void actionCreateCategory(ActionEvent event) {
		ProductCategory category = new ProductCategory(this);
	}

	@FXML
	void insertpicture(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Зураг сонгох");
		url = "file:" + fileChooser.showOpenDialog(null).toString();
		txtPicture.setText(url);
		imageView.setImage(new Image(url));
	}

	public void setProductInventoryController(ProductInventoryController inventoryCont) {
		this.inventoryController = inventoryCont;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(url, user, pass);
			Statement st = connection.createStatement();
			int cat_id = cboxCategory.getSelectionModel().getSelectedIndex();
			ResultSet rs = st.executeQuery("select max(id)as id from item");
			if (rs.next()) {
				lblItemNumber.setText(String.valueOf(rs.getInt("id")));
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		computeCategory();
	}

	public void computeCategory() {
		cboxCategory.setItems(inventoryController.getCategoryData());
		cboxCategory.getSelectionModel().select(0);
	}

	public void setProductInventoryState(Stage stage) {
		this.inventoryStage = stage;
	}

}
