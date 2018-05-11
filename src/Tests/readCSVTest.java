package Tests;
import Exception.CSVFormatException;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class readCSVTest {

	//Invalid item_properties file name
	@Test
    void invalidFileName() {
        final CSVFormatException thrown = assertThrows(
        		CSVFormatException.class,
                () -> { CSV.ParseCSV.readItems("test_files/itemProperties1.csv"); }
        );
        assertEquals("Invalid file name", thrown.getMessage());
    }
	
	//Empty item_properties file
	@Test
	void emptyFile() {
        final CSVFormatException thrown = assertThrows(
        		CSVFormatException.class,
                () -> { CSV.ParseCSV.readItems("test_files/item_properties(empty).csv"); }
        );
        assertEquals("CSV should contain at least one item", thrown.getMessage());
    }

}
