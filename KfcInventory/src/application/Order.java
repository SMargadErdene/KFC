package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {
	private IntegerProperty id;
	private StringProperty date;
	private IntegerProperty total_price;
	private StringProperty status;
	
	public Order(Integer id, String date, Integer price, String status) {
		this.id = new SimpleIntegerProperty(id);
		this.date = new SimpleStringProperty(date);
		this.total_price = new SimpleIntegerProperty(price);
		this.status = new SimpleStringProperty(status);
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getDate() {
		return date.get();
	}

	public void setDate(String date) {
		this.date.set(date);
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	public int getTotal_price() {
		return total_price.get();
	}

	public void setTotal_price(int total_price) {
		this.total_price.set(total_price);
	}
	
	public IntegerProperty idProperty() {
	    return id;
	}
	
	public StringProperty dateProperty() {
	    return date;
	}
	
	public IntegerProperty priceProperty() {
	    return total_price;
	}
	
	public StringProperty statusProperty() {
	    return status;
	}
}
