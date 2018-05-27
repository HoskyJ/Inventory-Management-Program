package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Test;

import Delivery.Manifest;
import Delivery.Truck;
import Stock.Item;

class ManifestTest {

	Stock.Store store = new Stock.Store("Coles", 150000);
	
	@Test
	//Check if the getting of item details is correct
	public void itemDetailsTest() {
		//Set up store
		List<Item> inventory = new ArrayList<Item>();
		Item cargoItem1 = new Item("Coke", 3.0, 5.0, 200, 250, 5, 100); //Create relevant item
		Item cargoItem2 = new Item("Frogs", 6.0, 15.0, 50, 30, 20, 45); //Create relevant item
		inventory.add(cargoItem1);
		inventory.add(cargoItem2);
		
		Stock.Stock cargoLoad = new Stock.Stock(inventory);
		store.UpdateInventory(cargoLoad);
		
		Manifest.GetItemDetails(inventory);
		assertEquals(inventory.get(0).getName(), Manifest.cooledItems.get(0).get(0));
		assertEquals(inventory.get(1).getName(), Manifest.ordinaryItems.get(0).get(0));
	}
	
	@Test
	//Check that sorting cooled items is correct
	public void tempSortTest() {
		ArrayList<ArrayList<Object>> cooledItems = new ArrayList<ArrayList<Object>>();
		
		cooledItems.add(new ArrayList<Object>());
		int listSize = cooledItems.size();
		cooledItems.get(listSize-1).add("Coke");
		cooledItems.get(listSize-1).add("230");
		cooledItems.get(listSize-1).add("-5");
		
		cooledItems.add(new ArrayList<Object>());
		listSize = cooledItems.size();
		cooledItems.get(listSize-1).add("Frogs");
		cooledItems.get(listSize-1).add("50");
		cooledItems.get(listSize-1).add("20");
		
		cooledItems.add(new ArrayList<Object>());
		listSize = cooledItems.size();
		cooledItems.get(listSize-1).add("Cheese");
		cooledItems.get(listSize-1).add("150");
		cooledItems.get(listSize-1).add("1");
		
		cooledItems.add(new ArrayList<Object>());
		listSize = cooledItems.size();
		cooledItems.get(listSize-1).add("Rice");
		cooledItems.get(listSize-1).add("200");
		cooledItems.get(listSize-1).add("3");
		

		Manifest.cooledItems = cooledItems;
		Manifest.SortTemp();
		//Check coldest temp is correct
		assertEquals("-5", Manifest.cooledItems.get(0).get(2));
		
		//Check highest temp is correct
		assertEquals("20", Manifest.cooledItems.get(3).get(2));
	}
	
	@Test
	//Check that overflow is handled correctly
		//Only the last truck should have space if there is any
	public void checkOverFlowTest() {
		//Set up store
		List<Item> inventory = new ArrayList<Item>();
		Item cargoItem1 = new Item("Coke", 3.0, 5.0, 200, 250, 5, 100); //Create relevant item
		Item cargoItem2 = new Item("Frogs", 6.0, 15.0, 50, 30, 20, 45); //Create relevant item
		Item cargoItem3 = new Item("Cake", 2.0, 5.0, 70, 100, 20, 65); //Create relevant item
		inventory.add(cargoItem1);
		inventory.add(cargoItem2);
		inventory.add(cargoItem3);
		
		Stock.Stock inventoryItems = new Stock.Stock(inventory);
		store.UpdateInventory(inventoryItems);
		
		Manifest.GetItemDetails(inventory);
		Manifest.SortTemp();
		Manifest.CooledLogistics();
		Manifest.OrdinaryItems();
		
		for(int x = 0; x < Manifest.Manifest.size(); x++) {
			Truck truck = Manifest.Manifest.get(x);
			List<Item> cargo = truck.cargo.getItems();
			
			//On last item
			if(x != Manifest.Manifest.size() - 1) {
				assertFalse(truck.capacity != 0);
			}
			
		}
	}
	
	
	@Test
	//Check if cost return is correct
	public void checkCostTest() {
		//Set up store
		List<Item> inventory = new ArrayList<Item>();
		Item cargoItem1 = new Item("Coke", 3.0, 5.0, 200, 250, 5, 100); //Create relevant item
		Item cargoItem2 = new Item("Frogs", 6.0, 15.0, 50, 30, 20, 45); //Create relevant item
		Item cargoItem3 = new Item("Cake", 2.0, 5.0, 70, 100, 20, 65); //Create relevant item
		inventory.add(cargoItem1);
		inventory.add(cargoItem2);
		inventory.add(cargoItem3);
		
		Stock.Stock inventoryItems = new Stock.Stock(inventory);
		store.UpdateInventory(inventoryItems);
		
		Manifest.GetItemDetails(inventory);
		Manifest.SortTemp();
		Manifest.CooledLogistics();
		Manifest.OrdinaryItems();
		
		assertEquals(3.0 + 6.0 + 2.0, 3.0 + 6.0 + 2.0, Manifest.GetLogisticsCost());
	}

}
