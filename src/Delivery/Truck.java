package Delivery;
import Stock.Stock;
/**
 * 
 * @author J_Hos
 *
 */
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
	
	public abstract double getCost();
	
	public abstract int getTruckTemp();

}
