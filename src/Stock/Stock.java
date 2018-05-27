package Stock;

import java.util.ArrayList;

import java.util.List;

import Stock.Item;

public class Stock {

	private List<Item> inventory = new ArrayList<Item>();

	public Stock() {
	}
	
	//Overload for manifest
	public Stock(List<Item> items) {
		this.inventory = items;
	}

	// Loads item properties, constructs Item objects and adds them to inventory.
	public List<Item> initializeStock(String[][] CSVInput) {
		for (int i = 0; i < CSVInput.length; i++) {
			// Grabs item details for each row in array
			// obtaining appropriate properties from each cell and adding them to a new
			// item.
			String name = CSVInput[i][0];
			int cost = Integer.parseInt(CSVInput[i][1]);
			int sellPrice = Integer.parseInt(CSVInput[i][2]);
			int reorderPoint = Integer.parseInt(CSVInput[i][3]);
			int reorderAmount = Integer.parseInt(CSVInput[i][4]);
			
			String tempString = (CSVInput[i][5]);
			
			if (tempString == null) {
				tempString = "20";
			}
			int temperature = Integer.parseInt(tempString);
			// need to sort this
			int quantity = 0;
			// Create new inventory item.
			inventory.add(new Item(name, cost, sellPrice, reorderPoint, reorderAmount, temperature, quantity));
		}
		return inventory;
	}

	// gets inventory
	public List<Item> getItems() {
		return this.inventory;
	}

	public int getSize() {
		return this.inventory.size();
	}

	public Item getItem(int index) {
		return this.inventory.get(index);
	}

}
