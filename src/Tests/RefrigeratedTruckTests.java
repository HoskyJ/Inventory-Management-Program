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
	//Check if cost of truck is calculated correctly
	public void getCost() {
		double cost = Math.round((900 + 200 * Math.pow(0.7, temperature/5)) * 100.0) / 100.0;
		assertEquals(cost, truck.GetCost());
	}
}
