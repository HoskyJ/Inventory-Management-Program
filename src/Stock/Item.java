package Stock;

public class Item {
	private String name;
	private double cost;
	private double price;
	private int reorderPoint;
	private int reorderAmount;
	private int temp;
	private int quantity = 600;

	//Constructs an Item object
	public Item(String name, double cost, double price, int reorderPoint, int reorderAmount, int temp, int quantity) {
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.reorderPoint = reorderPoint;
		this.reorderAmount = reorderAmount;
		this.temp = temp;
		this.quantity = quantity;
	}
	
	//Overload constructor used for creating items for use in manifest
	public Item(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}
		
	public String getName() {
		return this.name;
	}

	public double getCost() {
		return this.cost;
	}

	public double getPrice() {
		return this.price;
	}

	public int getReorderPoint() {
		return this.reorderPoint;
	}

	public int getReorderAmount() {
		return this.reorderAmount;
	}

	public int getTemp() {
		return this.temp;
	}

	public int getQuantity() {
		return this.quantity;
	}

	//Used to increase quantity of item after manifest has been loaded
	public void addQuantity(int amount) {
		quantity += amount;
	}
	
	//Used to decrease quantity of item after sales have been loaded
	public void subtractQuantity(int amount) {
		quantity -= amount;
	}

}
