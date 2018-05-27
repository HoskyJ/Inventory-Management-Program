package Delivery;
/**
 * 
 * @author J_Hos
 *
 */
public class RefrigeratedTruck extends Truck {
	
	int temperature;
	
	public RefrigeratedTruck(int temperature) {
		super(Type.Refrigerated);
		this.temperature = temperature;
	}

	public int getTruckTemp() {	
		return this.temperature;
	}
	
	//Overrides Truck getCost method to include temperature in the calculation.
	@Override
	public double getCost() {
		double truckCost = Math.round((900 + 200 * Math.pow(0.7, temperature/5)) * 100.0) / 100.0;
		return truckCost;
	}
	
}
