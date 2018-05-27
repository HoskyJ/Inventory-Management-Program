package CSV;

import java.io.File;
import Delivery.Manifest;

import java.io.FileWriter;
import java.io.IOException;

public class WriteCSV {

	public static void WriteManifest() throws IOException {

		FileWriter printWriter = new FileWriter(new File("test_files/manifest.csv"));
		String csvString = "";

		for (int i = 0; i < Manifest.Manifest.size(); i++) {
			
			if (i > 0) {
				csvString += "\n";
			}
			
			csvString += ">" + Manifest.Manifest.get(i).type;

			for (int j = 0; j < Manifest.Manifest.get(i).cargo.getSize(); j++) {
				csvString += ("\n");
				csvString += (Manifest.Manifest.get(i).cargo.getItem(j).getName());
				csvString += (",");
				csvString += Manifest.Manifest.get(i).cargo.getItem(j).getQuantity();
			}
			
		}
		printWriter.write(csvString);
		printWriter.close();
		System.out.println(csvString);
		System.out.println("Wrote manifest");
	}
}