package GUI;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import Stock.Item;

public class TableModel extends AbstractTableModel {
	
	private final List<Item> inventory;
    
	private final String[] columnNames = {"Name",
            "Cost",
            "Price",
            "Re-order Point",
            "Re-order Amount",
            "Quantity"};
	
    private final Class[] columnClass = new Class[] {
    		String.class, 
    		Integer.class,
    		Integer.class,
    		Integer.class, 
    		Integer.class, 
    		Integer.class, 
    		Integer.class
    };
 
    public TableModel(List<Item> inventory)
    {
        this.inventory = inventory;
    }
     
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }
 
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }
 
    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }
 
    @Override
    public int getRowCount()
    {
        return inventory.size();
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Item row = inventory.get(rowIndex);
        if(0 == columnIndex) {
            return row.getName();
        }
        else if(1 == columnIndex) {
            return row.getCost();
        }
        else if(2 == columnIndex) {
            return row.getPrice();
        }
        else if(3 == columnIndex) {
            return row.getReorderPoint();
        }
        else if(4 == columnIndex) {
            return row.getReorderAmount();
        }

        else if(5 == columnIndex) {
            return row.getQuantity();
        }
        return null;
    }
}

