package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
	private IntegerProperty id;
	private StringProperty name;
	private IntegerProperty price;
	private StringProperty status;
	private IntegerProperty category_id;
	
	public Item(Integer id, String name, Integer price, String status, Integer cat_id) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.price = new SimpleIntegerProperty(price);
		this.status = new SimpleStringProperty(status);
		this.category_id = new SimpleIntegerProperty(cat_id);
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public Integer getPrice() {
		return price.get();
	}

	public void setPrice(Integer price) {
		this.price.set(price);
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	public Integer getCategory_id() {
		return category_id.get();
	}

	public void setCategory_id(Integer category_id) {
		this.category_id.set(category_id);
	}
	
	public IntegerProperty idProperty() {
	    return id;
	}
	
	public StringProperty nameProperty() {
	    return name;
	}
}
