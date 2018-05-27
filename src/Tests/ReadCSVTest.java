package Tests;
import Exception.CSVFormatException;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReadCSVTest {
	@Test
	//Invalid item_properties file name
    void invalidFileName() {
        final CSVFormatException thrown = assertThrows(
        		CSVFormatException.class,
                () -> { CSV.ReadCSV.readItemProperties("test_files/itemProperties1.csv"); }
        );
        assertEquals("Invalid file name", thrown.getMessage());
    }
	
	@Test
	//Empty item_properties file
	void emptyFile() {
		
        final CSVFormatException thrown = assertThrows(
        		CSVFormatException.class,
                () -> { CSV.ReadCSV.readItemProperties("test_files/item_properties(empty).csv"); }
        );
        assertEquals("CSV should contain at least one item", thrown.getMessage());
    }
}
