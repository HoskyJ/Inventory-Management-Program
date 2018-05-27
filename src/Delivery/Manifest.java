package Delivery;
import Stock.Stock;
import Delivery.Truck;
import java.util.ArrayList;
import java.util.List;
import Stock.Item;
import java.util.Collections;
import java.util.Comparator;

public class Manifest {
	public static List<Truck> Manifest = new ArrayList<Truck>(); //Change to suit size of manifest not 100
	public static ArrayList<ArrayList<Object>> ordinaryItems;
	public static ArrayList<ArrayList<Object>> cooledItems;
	
	public Manifest() {
		//GetItemDetails(Main.Entry.store.GetCurrentyInventory().getItems());
		//SortTemp();
		//CooledLogistics();
		//OrdinaryItems();
		//System.out.println(GetLogisticsCost());
	}
	
	//Determine items that need re-stocking and their reorder amounts
	public static void GetItemDetails(List<Item> inventory) {
		List<Item> currentInventory = inventory;
		ordinaryItems = new ArrayList<ArrayList<Object>>();
		cooledItems = new ArrayList<ArrayList<Object>>();
		
		for (Item item : currentInventory) {
			//Get details of current item
			int quantity = item.getQuantity();
			int reorderPoint = item.getReorderPoint();
			
			//Check if item needs reordering
			if (quantity <= reorderPoint) {
				//Ordinary Items
				if(item.getTemp() == 20) {
					ordinaryItems.add(new ArrayList<Object>());
					int listSize = ordinaryItems.size();
					
					ordinaryItems.get(listSize-1).add(item.getName());
					ordinaryItems.get(listSize-1).add(Integer.toString(item.getReorderAmount()));
				}
				//Temperature Items
				else {
					cooledItems.add(new ArrayList<Object>());
					int listSize = cooledItems.size();
					
					cooledItems.get(listSize-1).add(item.getName());
					cooledItems.get(listSize-1).add(Integer.toString(item.getReorderAmount()));
					cooledItems.get(listSize-1).add(Integer.toString(item.getTemp()));
				}
			}
		}
	}
	
	//Sorts cooled items in ascending order using a comparator
	public static void SortTemp() {
		Collections.sort(cooledItems, new Comparator<List<Object>> () {
	        public int compare(List<Object> list1, List<Object> list2) {
	        return (Integer.valueOf((String) list1.get(2))).compareTo(Integer.valueOf((String)list2.get(2)));
	        }
	    });
	}

	//Distribute cooled items in trucks
	public static void CooledLogistics() {
		List<Item> cargo = new ArrayList<Item>(); //Hold items to be loaded
		int excessItemQuantity  = 0; //Stores left over item quantities to be filled
		
		//Create first truck at coldest temperature
		int temperature = Integer.parseInt((String) cooledItems.get(0).get(2));
		Delivery.RefrigeratedTruck cooledTruck = new Delivery.RefrigeratedTruck(temperature);
		
		for(int itemIndex = 0; itemIndex < cooledItems.size(); itemIndex++) {
			//Get current item details
			List<Object> item = cooledItems.get(itemIndex);
			int itemQuantity = Integer.parseInt((String) item.get(1));
			String itemName = (String) item.get(0);
			
			//Item can be filled completely into current truck
			if(cooledTruck.capacity - itemQuantity >= 0) {
				Item cargoItem = new Item(itemName, itemQuantity); //Create relevant item
				cargo.add(cargoItem); //Add item to list of items to load truck with
				cooledTruck.capacity -= itemQuantity; //Update truck capacity
			}
			//Item cannot fit entirely in current truck
			else {
				//Fill as much of the item as possible
				excessItemQuantity = (cooledTruck.capacity - itemQuantity) - (cooledTruck.capacity - itemQuantity) * 2; //Originally a negative number as its the excess. Converts to positive
				itemQuantity -= excessItemQuantity; //Updates itemQuantity to fit into left over space in current truck.
				Item cargoItem = new Item(itemName, itemQuantity); //Create relevant item
				cargo.add(cargoItem); //Add item to list of items to load truck with
				
				//Truck is now full
				//Load truck with full cargo load
				Stock cargoLoad = new Stock(cargo);
				cooledTruck.cargo = cargoLoad;
				cooledTruck.capacity -= itemQuantity;
				
				//Add to truck to the manifest
				Manifest.add(cooledTruck);
				
				//Reset cargo item list
				cargo = new ArrayList<Item>(); //Start new inventory
				
				//Create a new truck and fill excess item quantity
				//Could throw error if leftOverCargo is greater than capacity
				temperature = Integer.parseInt((String) item.get(2)); //Gets the temperature of the item which has left over to be filled
				cooledTruck = new Delivery.RefrigeratedTruck(temperature);
				Item excessCargoItem = new Item(itemName, excessItemQuantity); //Create relevant item //Makes leftOverCargo positive
				cargo.add(excessCargoItem);
				cooledTruck.capacity -= excessItemQuantity;
				excessItemQuantity = 0;
			}
			
			//If last item to be order or current truck full
			//add truck to manifest
			if(itemIndex == cooledItems.size() - 1 || cooledTruck.capacity == 0) {
				//Load truck with full cargo load
				Stock cargoLoad = new Stock(cargo);
				cooledTruck.cargo = cargoLoad;
				Manifest.add(cooledTruck);
			}
		}
		
		/**
		for(int x = 0; x < trucks.size(); x++) {
			System.out.println(trucks.get(x).getTruckTemp());
		}
		*/
	}
	
	//Distribute ordinary items in trucks
	public static void OrdinaryItems() {
		//Fill any left over space in cooled trucks
		//Get most recent truck details
		Delivery.Truck previousTruck = Manifest.get(Manifest.size()-1); //Gets the most recent truck object (the only one that will have space)
		
		List<Item> cargo = previousTruck.cargo.getItems(); //Get trucks cargo
		
		
		
		int counter = 0;
		
		while(previousTruck.capacity > 0 && counter < ordinaryItems.size()) {
			//Get current item details
			int itemQuantity = Integer.parseInt((String) ordinaryItems.get(counter).get(1));
			String itemName = (String) ordinaryItems.get(counter).get(0);
			
			//Item can be filled completely into truck
			if(previousTruck.capacity - itemQuantity >= 0) {
				Item cargoItem = new Item(itemName, itemQuantity); //Create relevant item
				cargo.add(cargoItem); //Add item to list of items to load truck with
				previousTruck.capacity -= itemQuantity;
				ordinaryItems.remove(counter); //All of this item has been filled so remove it
			}
			//Item cannot be filled completely into truck
			else {
				int excessItemQuantity = (previousTruck.capacity - itemQuantity) - (previousTruck.capacity - itemQuantity) * 2; //Originally a negative number as its the excess. Converts to positive
				itemQuantity -= excessItemQuantity; //Updates itemQuantity to fit into left over space in current truck.
				Item cargoItem = new Item(itemName, itemQuantity); //Create relevant item
				cargo.add(cargoItem); //Add item to list of items to load truck with
				previousTruck.capacity -= itemQuantity;
				ordinaryItems.get(counter).set(1, excessItemQuantity); //Update item with the left over quantity to be filled
			}
			counter ++;
		}
		
		//Load new cargo onto truck
		Stock cargoLoad = new Stock(cargo);
		previousTruck.cargo = cargoLoad;
		Manifest.set(Manifest.size() - 1, previousTruck); //Update old truck with new ordinary goods
		
		//All cooled trucks are now filled
		//Fill dry goods accordingly
		Delivery.OrdinaryTruck ordinaryTruck = new Delivery.OrdinaryTruck();
		cargo = new ArrayList<Item>();
		int excessItemQuantity = 0;
		
		//Loop through each dry good and fill
		for(int itemIndex = 0; itemIndex < ordinaryItems.size(); itemIndex++) {
			//Get current item details
			List<Object> item = ordinaryItems.get(itemIndex);
			String convertedToString = String.valueOf(ordinaryItems.get(itemIndex).get(1));
			int itemQuantity = Integer.parseInt(convertedToString);
			String itemName = (String) item.get(0);
			
			//Item can be completely filled into truck
			if(ordinaryTruck.capacity - itemQuantity >= 0) {
				Item cargoItem = new Item(itemName, itemQuantity); //Create relevant item
				cargo.add(cargoItem); //Add item to list of items to load truck with
				ordinaryTruck.capacity -= itemQuantity; //Update truck capacity
			}
			//Item cannot fit entirely in current truck
			else {
				//Fill as much of the item as possible
				excessItemQuantity = (ordinaryTruck.capacity - itemQuantity) - (ordinaryTruck.capacity - itemQuantity) * 2; //Originally a negative number as its the excess. Converts to positive
				itemQuantity -= excessItemQuantity; //Updates itemQuantity to fit into left over space in current truck.
				Item cargoItem = new Item(itemName, itemQuantity); //Create relevant item
				cargo.add(cargoItem); //Add item to list of items to load truck with
				
				//Truck is now full
				//Load truck with full cargo load
				cargoLoad = new Stock(cargo);
				ordinaryTruck.cargo = cargoLoad;
				ordinaryTruck.capacity -= itemQuantity;
				
				//Add to truck to the manifest
				Manifest.add(ordinaryTruck);
				
				//Reset cargo item list
				cargo = new ArrayList<Item>();
				
				//Create a new truck and fill excess item quantity
				//Could throw error if leftOverCargo is greater than capacity
				ordinaryTruck = new Delivery.OrdinaryTruck();
				Item excessCargoItem = new Item(itemName, excessItemQuantity); //Create relevant item //Makes leftOverCargo positive
				cargo.add(excessCargoItem);
				ordinaryTruck.capacity -= excessItemQuantity;
				excessItemQuantity = 0;
			}
		
			//If last item to be order or current truck full
			//add truck to manifest
			if(itemIndex == ordinaryItems.size() - 1 || ordinaryTruck.capacity == 0) {
				//Load truck with full cargo load
				cargoLoad = new Stock(cargo);
				ordinaryTruck.cargo = cargoLoad;
				Manifest.add(ordinaryTruck);
			}
		}
		
		
		for(Truck manifest : Manifest) {
			List<Item> test = manifest.cargo.getItems();
			for(int x =0; x < test.size(); x++) {
				System.out.println(test.get(x).getName());
				System.out.println(test.get(x).getQuantity());
			}
			
			System.out.println("\n");
		}
		
	}

	//Add up the cost of all truck loads and return.
	public static double GetLogisticsCost() {
		double totalCost = 0;
		for(Truck truck : Manifest) {
			totalCost += truck.GetCost();
		}
		return totalCost;
	}
}