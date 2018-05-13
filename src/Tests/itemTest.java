package Tests;

import Stock.Item;

import org.junit.jupiter.api.Test;

class itemTest {
	
	String testName = "testName";	
	double testCost = 7.99;
	double testPrice = 9.99;
	int itemReorderPoint = 2;
	int itemReorderAmount = 10;
	int testTemp = 4;
	
	private final Item testItem = new Item(testName, testCost, testPrice, itemReorderPoint, itemReorderAmount, testTemp);
	
	@Test
	public void testItemName() {
		assert testItem.getName() == testName;		
	}
	
	@Test
	public void testItemSellPrice() {
		assert testItem.getSellPrice() == testPrice;		
	}

	@Test
	public void testItemReorderPoint() {
		assert testItem.getReorderPoint() == itemReorderPoint;		
	}
	
	@Test
	public void testItemReorderAmount() {
		assert testItem.getReorderAmount() == itemReorderAmount;		
	}
	
	@Test
	public void testItemTemp() {
		assert testItem.getTemp() == testTemp;
		;		
	}
	

}
