package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Stock.Item;

class OrdinaryTruckTest {

	int temperature = 5;
	
	List<Item> cargo = new ArrayList<Item>();
	Delivery.OrdinaryTruck truck = new Delivery.OrdinaryTruck();
	
	@Test
	//Check if temperature of truck is assigned correctly
	public void checkTemperature() {
		assertEquals(20, truck.getTruckTemp());
	}

}
