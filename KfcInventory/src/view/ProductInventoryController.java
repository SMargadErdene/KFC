package view;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import application.Category;
import application.Item;
import application.ProductCategory;
import application.ProductInventoryClass;
import javafx.collections.FXCollections;
//import application.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProductInventoryController {

	private ProductInventoryClass inventoryClass;
	private ProductController productCont;
	private Stage inventoryStage;
	private Connection connection;
	private ObservableList<Category> categoryData = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Item> itemViewTable;
    @FXML
    private TableColumn<Item, Number> IdColumn;
    @FXML
    private TableColumn<Item, String> NameColumn;
    @FXML
    private Label lblName;
    @FXML
    private Label lblId;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblCategory;
    @FXML
    private Label lblPrice;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private ImageView imageView;
    
    @FXML
    void btnCreateAction(ActionEvent event) {
//    	ProductController productController = new ProductController(this);
    	try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Product.fxml"));
			AnchorPane rootLayout = (AnchorPane) loader.load();
			Scene scene = new Scene(rootLayout, 500, 500); 
			scene.getStylesheets().add(getClass().getResource("/view/Product.css").toExternalForm());
			inventoryStage.setTitle("Шинэ бүтээгдэхүүн");
			inventoryStage.setScene(scene); 
			ProductController productController = loader.getController();
			productController.setProductInventoryController(this);
			productController.setProductInventoryState(inventoryStage);
			inventoryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {
    	int id = itemViewTable.getSelectionModel().getSelectedItem().getId();
    	try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(inventoryClass.url, inventoryClass.user, inventoryClass.pass);
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select id from set_item where item_id="+id+";");
			if(rs.next()) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Устгах боломжгүй бүтээгдэхүүн байна! Уг бүтээгдэхүүн нь сэтэд бүртгэгдсэн байна!");
				alert.setTitle("Анхааруулга!");
				alert.setHeaderText(null);
				alert.showAndWait();
			}else {
				rs = st.executeQuery("select id from order_item where item_id="+id+";");
				if(rs.next()) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("Устгах боломжгүй бүтээгдэхүүн байна! Уг бүтээгдэхүүн нь захиалганд орсон байна!");
					alert.setTitle("Анхааруулга!");
					alert.setHeaderText(null);
					alert.showAndWait();
				}else {
					int res = st.executeUpdate("delete from item where id="+id+";");
					if(res != 0) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setContentText("Амжилттай устгагдлаа!");
						alert.setTitle("Амжилттай!");
						alert.setHeaderText(null);
						alert.showAndWait();
						inventoryClass.initData();
						itemViewTable.setItems(inventoryClass.getItemData());
						imageView.setImage(null);
					}else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Устгалт амжилтгүй боллоо!");
						alert.setTitle("Амжилтгүй!");
						alert.setHeaderText(null);
						alert.showAndWait();
					}
				}
			}
			rs.close();
			st.close();
			connection.close();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    }

    @FXML
    void btnEditAction(ActionEvent event) {

    }

    private void showItemDetails(Item item) {
        if (item != null) {
            // Fill the labels with info from the person object.
        	lblId.setText(String.valueOf(item.getId()));
        	lblName.setText(item.getName());
        	lblPrice.setText(String.valueOf(item.getPrice()));
        	lblStatus.setText(item.getStatus());
        	try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				String connectionUrl = "jdbc:mysql://localhost:3306/kfc";
	    		Connection connection = DriverManager.getConnection(inventoryClass.url, inventoryClass.user, inventoryClass.pass);
	    		Statement st = connection.createStatement();
	    		ResultSet rs = st.executeQuery("select name from category where id="+item.getCategory_id()+";");
				if(rs.next()) {
	        		lblCategory.setText(rs.getString("name"));
	        	}else {
	        		lblCategory.setText("");
	        	}
				rs.close();
				st.close();
				connection.close();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	InputStream inStream = getClass().getResourceAsStream("/image/"+lblName.getText()+".PNG");
        	if(inStream != null) {
        		Image imageObject = new Image(inStream);
            	imageView.setImage(imageObject);
        	}else {
        		Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("lblName.getText()+\"нэртэй зураг олдсонгүй!");
				alert.setTitle("Амжилтгүй!");
				alert.setHeaderText(null);
				alert.showAndWait();
        		imageView.setImage(null);
        	}
        } else {
            // Item is null, remove all the text.
        	lblId.setText("");
        	lblName.setText("");
        	lblPrice.setText("");
        	lblStatus.setText("");
        	lblCategory.setText("");
        }
    }
    
	public void setProductInventoryClass(ProductInventoryClass inventoryApp) {
		this.inventoryClass = inventoryApp;
		itemViewTable.setItems(inventoryApp.getItemData());
		initCategoryData();
	}

	public void setProductInventoryClassState(Stage stage) {
		this.inventoryStage = stage;
		IdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		NameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		showItemDetails(null);
		itemViewTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showItemDetails(newValue));
	}
	
	public void initCategoryData() {
		categoryData.clear();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(inventoryClass.url, inventoryClass.user, inventoryClass.pass);
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select * from category;");
			while (rs.next()) {	
				categoryData.add(new Category(rs.getInt("id"), rs.getString("name")));
			}
			rs.close();
			st.close();
			connection.close();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ObservableList<String> getCategoryData() {
		ObservableList<String> categoryList = FXCollections.observableArrayList();
		for(Category cat: categoryData)
			categoryList.add(cat.getName());
		return categoryList;
	}
	
}

