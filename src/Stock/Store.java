package Stock;

public class Store {
	String name;
	double capital;
	Stock inventory;

	//Constructor which creates store with its name and starting capital
	public Store(String name, double capital) {
		this.name = name;
		this.capital = capital;
	}

	//Return current capital
	public double getCapital() {
		return capital;
	}
	
	//Change capital to given value
	//Used for loading sales and manifest
	public void setCapital(double capital) {
		this.capital = capital;
	}
	
	//Updates the inventory of the store after loading manifest and sales
	public void UpdateInventory(Stock inventory) {
		this.inventory = inventory;
	}
	
	//Returns the store's current inventory
	public Stock GetCurrentyInventory() {
		return inventory;
	}
}
