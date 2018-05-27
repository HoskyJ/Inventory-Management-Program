package Main;
import Stock.Store;
import java.io.IOException;

import Exception.CSVFormatException;
import GUI.GUI;

public class Entry {
	public static Store store;
	public static void main(String[] args) throws IOException, CSVFormatException {
		//Create store object
		store = new Store("Store", 100000);
		
		//Start GUI
		GUI.initializeWindow();
	}
	
	
}
