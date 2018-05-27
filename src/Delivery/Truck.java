package Delivery;
import Stock.Stock;

public abstract class Truck {
	//Used to set capacity and create CSV
	public enum Type {
		Refrigerated, Ordinary
	}

	public int capacity;
	public static double cost;
	public Stock cargo;
	public Type type;

	public Truck(Type type) {
		this.type = type;
		if(type == Type.Refrigerated) {
			capacity = 800;
		}
		else {
			capacity = 1000;
		}
	}

	public void PrintCargo() {
		for(int x = 0; x < cargo.getItems().size(); x++) {
			System.out.println(cargo.getItems().get(x));
		}
		System.out.println(cargo.getItems().size());
	}
	
	public abstract double GetCost();
	
	public abstract int getTruckTemp();

}
