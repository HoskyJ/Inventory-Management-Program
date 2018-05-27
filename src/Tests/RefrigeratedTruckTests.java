package Tests;
import Stock.Item;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class RefrigeratedTruckTests {
	int temperature = 5;
	
	List<Item> cargo = new ArrayList<Item>();
	Delivery.RefrigeratedTruck truck = new Delivery.RefrigeratedTruck(temperature);
	
	@Test
	//Check if temperature of truck is assigned correctly
	public void checkTemperature() {
		assertEquals(5, truck.getTruckTemp());
	}
	@Test
	//Check if temperature of truck is assigned correctly
	public void getCost() {
		double cost = Math.round((900 + 200 * Math.pow(0.7, temperature/5)) * 100.0) / 100.0;
		assertEquals(cost, truck.GetCost());
	}
	
	
	
	//Is capacity update when added
	//Is it has the correct type
	//Correct costing value?
	//Correct capacity?
	//Stores cargo
	//Is temperature correct
	/**
	 * Item cargoItem1 = new Item("Coke", 15); //Create relevant item
		Item cargoItem2 = new Item("Soup", 150); //Create relevant item
		Item cargoItem3 = new Item("Frogs", 5); //Create relevant item
		cargo.add(cargoItem1);
		cargo.add(cargoItem2);
		cargo.add(cargoItem3);
		
		Stock cargoLoad = new Stock(cargo);
		truck.cargo = cargoLoad;
		truck.capacity -= itemQuantity;
	 */
	
	

}
