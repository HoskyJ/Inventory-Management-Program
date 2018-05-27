package Delivery;

public class OrdinaryTruck extends Truck{
	
	public OrdinaryTruck() {
		super(Type.Ordinary);
	}
	
	//Overrides Truck getCost method to include temperature in the calculation.
	@Override
	public double getCost() {
		int quantity = 1000 - capacity;
		double truckCost = 750 + (0.25*quantity);
		return truckCost;
	}

	@Override
	public int getTruckTemp() {
		return 20; //20 is the value we assigned to dry good or ordinary trucks
	}
	
	//Used for test case
	public int getCapacity() {
		return capacity;
	}
	
}
