package Stock;

public class Store {
	String name;
	double capital;
	Stock inventory;

	public Store(String name, double capital) {
		this.name = name;
		this.capital = capital;
	}

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public void UpdateInventory(Stock inventory) {
		this.inventory = inventory;
	}

	public Stock GetCurrentyInventory() {
		return inventory;
	}
}
