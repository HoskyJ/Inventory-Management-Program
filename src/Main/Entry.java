package Main;
import Exception.CSVFormatException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Entry {

	public static void main(String[] args) throws IOException, CSVFormatException {
		//test readItems
		String[][] itemProperties = CSV.ParseCSV.readItems("test_files/item_properties(empty).csv");
		System.out.println("ITEM PROPERTIES " + Arrays.deepToString(itemProperties));
		
		//test readSales
		String[][] salesList = CSV.ParseCSV.readSales("test_files/sales_log_0.csv");
		System.out.println("SALES LIST " + Arrays.deepToString(salesList));
		
		//test readManifest and writeManifest
		//WriteCSV.WriteManifest("Refridgerated", "icecream", 1);
		List<List<String>> manifestList = CSV.ParseCSV.readManifest();
		System.out.println("MANIFEST " + manifestList);
		//WriteCSV.WriteManifest("Ordinary", "cheese", 9);
		manifestList = CSV.ParseCSV.readManifest();
		System.out.println("MANIFEST " + manifestList);
		
		System.out.println("MANIFEST " + manifestList);
	}

}

