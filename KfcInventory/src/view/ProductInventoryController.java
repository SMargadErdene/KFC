package view;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

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
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProductInventoryController implements Initializable{

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
    private TextField txtId;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtStatus;

    @FXML
    private ComboBox<String> cboxCategory;

    @FXML
    private ImageView imageView;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnNotEdit;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;
    
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
    	stateEdit();
    	int temp_id = itemViewTable.getSelectionModel().getSelectedItem().getCategory_id();
    	int index = 0;
		for (Category cat : categoryData)
			if(cat.id == temp_id)
				break;
			++index;
		if(index != 0) {
			cboxCategory.getSelectionModel().select(index);
		}
    }
    
    @FXML
    void btnNotEditAction(ActionEvent event) {
    	System.out.println("A");
    	stateNotEdit();
    }
    
    @FXML
    void btnSaveAction(ActionEvent event) {
    	System.out.println("B");
    	stateNotEdit();
    }

    private void showItemDetails(Item item) {
        if (item != null) {
            // Fill the labels with info from the person object.
        	txtId.setText(String.valueOf(item.getId()));
        	lblName.setText(item.getName());
        	txtPrice.setText(String.valueOf(item.getPrice()));
        	txtStatus.setText(item.getStatus());
			int index = 0;
    		for (Category cat : categoryData)
				if(cat.id == item.getCategory_id())
					break;
    			++index;
    		if(index != 0) {
    			cboxCategory.getSelectionModel().select(index);
    		}
        	InputStream inStream = getClass().getResourceAsStream("/image/new/"+lblName.getText()+".png");
        	if(inStream != null) {
        		Image imageObject = new Image(inStream);
            	imageView.setImage(imageObject);
        	}else {
        		Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText(lblName.getText()+" нэртэй зураг олдсонгүй!");
				alert.setTitle("Амжилтгүй!");
				alert.setHeaderText(null);
				alert.showAndWait();
        		imageView.setImage(null);
        	}
        } else {
            // Item is null, remove all the text.
        	txtId.setText("");
        	lblName.setText("");
        	txtPrice.setText("");
        	txtStatus.setText("");
        	cboxCategory.setItems(null);
        }
    }
    
	public void setProductInventoryClass(ProductInventoryClass inventoryApp) {
		this.inventoryClass = inventoryApp;
		itemViewTable.setItems(inventoryApp.getItemData());
		initCategoryData();
	}

	public void setProductInventoryClassState(Stage stage) {
		this.inventoryStage = stage;
		cboxCategory.setItems(this.getCategoryData());
		IdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		NameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		if(categoryData.size() > 0) {
			itemViewTable.getSelectionModel().select(0);
			showItemDetails(itemViewTable.getSelectionModel().getSelectedItem());
		}else {
			showItemDetails(null);
		}
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stateNotEdit();
	}
	
	public void stateNotEdit() {
		btnCreate.setVisible(true);
    	btnEdit.setVisible(true);
    	btnSave.setDisable(false);
		btnNotEdit.setDisable(false);
//		btnCreate.setDisable(false);
//		btnEdit.setDisable(false);
//		btnSave.setDisable(true);
//		btnNotEdit.setDisable(true);
		btnCreate.setOpacity(100);
		btnEdit.setOpacity(100);
		btnSave.setOpacity(0);
		btnNotEdit.setOpacity(0);
		txtId.setEditable(false);
    	txtPrice.setEditable(false);
    	txtStatus.setEditable(false);
    	cboxCategory.setEditable(false);
	}
	
	public void stateEdit() {
		btnCreate.setVisible(false);
    	btnEdit.setVisible(false);
    	btnSave.setDisable(true);
		btnNotEdit.setDisable(true);
//    	btnCreate.setDisable(true);
//    	btnEdit.setDisable(true);
//		btnSave.setDisable(false);
//		btnNotEdit.setDisable(false);
		btnCreate.setOpacity(0);
		btnEdit.setOpacity(0);
		btnSave.setOpacity(100);
		btnNotEdit.setOpacity(100);
		txtId.setEditable(true);
    	txtPrice.setEditable(true);
    	txtStatus.setEditable(true);
    	cboxCategory.setEditable(true);
	}
}

