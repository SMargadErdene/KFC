package application;

public class Category {
	public int id;
    private String name;
    
    public Category(int id, String name){
    	this.id = id;
    	this.setName(name);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
