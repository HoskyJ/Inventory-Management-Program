package CSV;
import Exception.CSVFormatException;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ParseCSV {

	/* 
	 * Reads from item_properties.csv. Returns the contents of
	 * item_properties.csv as a multidimensional String array.
	 */
	@SuppressWarnings("resource")
	public static String[][] readItems(String fileName) throws CSVFormatException, FileNotFoundException {
		Scanner scanner;
		Scanner tempScanner; //Used for initially determining array size in case more items are added.
		String InputLine = "";
		
		try {
			scanner = new Scanner (new BufferedReader(new FileReader(fileName)));
			tempScanner = new Scanner (new BufferedReader(new FileReader(fileName)));	
		} catch(FileNotFoundException e){
			throw new CSVFormatException("Invalid file name");
		}
		
		//throws exception if file is empty
		File file = new File(fileName);
        if (file.length() == 0) {
        	throw new CSVFormatException("File is empty");
        }
		
		//Create appropriate sized 2D array for storing item data
		int rowCounter = 0;
		int noOfItemProperties = 6;
		
		//This counts the number of items in CSV. Then creates array to the proper size.
		while (tempScanner.hasNextLine()) {
			rowCounter ++;
			tempScanner.nextLine();
		}
		
		String myArray[][] = new String[rowCounter][noOfItemProperties];
		rowCounter = 0; //Reset so it can be used to assign properties.
		
		
		while (scanner.hasNextLine()) {
			InputLine = scanner.nextLine();
			String [] InArray = InputLine.split(",");
			
			for (int x = 0; x < InArray.length; x++) {
				myArray[rowCounter][x] = (InArray[x]);
			}
			rowCounter ++;
		}
		return myArray;
		
	}
	

	/*
	 * Reads from sales log CSV. Takes the filename as a String and returns the contents of
	 * the specified sales log as a multidimensional String array.
	 */
	@SuppressWarnings("resource")
	public static String[][] readSales(String fileName) throws FileNotFoundException {
		Scanner scanner = null;
		String InputLine = "";
		String myArray[][] = new String[24][2];
		int rowCounter = 0;
		scanner = new Scanner (new BufferedReader(new FileReader(fileName)));
		
		while (scanner.hasNextLine()) {
			InputLine = scanner.nextLine();
			String [] InArray = InputLine.split(",");
			
			for (int x = 0; x < InArray.length; x++) {
				myArray[rowCounter][x] = (InArray[x]);
			}
			rowCounter ++;
		} 
		return myArray;
	}
	

	/*
	 * Reads from manifest. Returns the contents of
	 * the manifest as a List of String Lists.
	 */
	public static List<List<String>> readManifest() throws IOException {
	    String row = null;
	    BufferedReader buffer = null;
	    List<List<String>> csvData = new ArrayList<List<String>>();
	    
	    //throws file not found exception if file is not found.
	    try {
	        buffer = new BufferedReader(new FileReader("test_files/manifest.csv"));
	        //data is split into string array then added to list.
	        while ((row = buffer.readLine()) != null) {
	            String[] splitData = row.split(",");
	            List<String> dataLine = new ArrayList<String>(splitData.length);
	            for (String data : splitData)
	                dataLine.add(data);
	            csvData.add(dataLine);
	        }   
	    //buffer is closed if value is not null
	    } finally {
	        if (buffer != null)
	            buffer.close();
	    }
	    return csvData;
	}

}
