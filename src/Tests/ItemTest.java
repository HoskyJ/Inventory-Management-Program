package Tests;

import Stock.Item;


/**
 * @author Tim Devereux
 * Tests for the Item class using JUnit 4
 *
 */

import org.junit.jupiter.api.Test;

class ItemTest {
	
	private final Item testItem = new Item("test", 0, 1, 2, 3, 4, 5);
	
	@Test
	public void testItemName() {
		assert testItem.getName() == "test";		
	}
	
	@Test
	public void testItemCost() {
		assert testItem.getCost() == 0;		
	}
	
	@Test
	public void testItemSellPrice() {
		assert testItem.getSellPrice() == 1;		
	}

	@Test
	public void testItemReorderPoint() {
		assert testItem.getReorderPoint() == 2;		
	}
	
	@Test
	public void testItemReorderAmount() {
		assert testItem.getReorderAmount() == 3;		
	}
	
	@Test
	public void testItemTemp() {
		assert testItem.getTemp() == 4;		
	}
	
	@Test
	public void testItemQuantity() {
		assert testItem.getQuantity() == 5;		
	}
	
	@Test
	public void testAddQuantity() {
		testItem.addQuantity(1);
		assert testItem.getQuantity() == 6;
	}
	
	@Test
	public void testSubractQuantity() {
		testItem.subtractQuantity(1);
		assert testItem.getQuantity() == 4;
	}
	
	

}
