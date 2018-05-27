package Tests;

import Stock.Item;

import org.junit.jupiter.api.Test;

class ItemTest {
	
	private final Item testItem = new Item("Coke", 3.0, 4.0, 100, 200, 4, 90);
	
	@Test
	//Ensure item name is correct
	public void testItemName() {
		assert testItem.getName() == "Coke";		
	}
	
	@Test
	//Ensure item cost is correct
	public void testItemCost() {
		assert testItem.getCost() == 3.0;		
	}
	
	@Test
	//Ensure item sellPrice is correct
	public void testItemSellPrice() {
		assert testItem.getSellPrice() == 4.0;		
	}

	@Test
	//Ensure item reorderPoint is correct
	public void testItemReorderPoint() {
		assert testItem.getReorderPoint() == 100;		
	}
	
	@Test
	//Ensure item reorderAmount is correct
	public void testItemReorderAmount() {
		assert testItem.getReorderAmount() == 200;		
	}
	
	@Test
	//Ensure item temperature is correct
	public void testItemTemp() {
		assert testItem.getTemp() == 4;		
	}
	
	@Test
	//Ensure item quantity is correct
	public void testItemQuantity() {
		assert testItem.getQuantity() == 90;		
	}
	
	@Test
	//Ensure item addition of quantity is correct
	public void testAddQuantity() {
		testItem.addQuantity(1);
		assert testItem.getQuantity() == 91;
	}
	
	@Test
	//Ensure item subtraction of quantity is correct
	public void testSubractQuantity() {
		testItem.subtractQuantity(1);
		assert testItem.getQuantity() == 89;
	}
}
