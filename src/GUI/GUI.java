package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import Delivery.Manifest;
import Exception.CSVFormatException;
import Stock.Item;
import Stock.Stock;

public class GUI extends JFrame {
	
	static List<Item> inventory;
	static TableModel model;
	static JTable table;
	static JFrame frame;
	static String filename;
	static String[][] file;
	static List<List<String>> manifestFile;
	static double capital;
	static DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
	static boolean fileChosen;

	//Creates GUI and its elements and fills table with item properties
	public static void initializeWindow() throws FileNotFoundException, CSVFormatException {
		//Update store inventory with initial item properties
		Stock inventory = new Stock();
		inventory.initializeStock(CSV.ReadCSV.readItemProperties("test_files/item_properties.csv"));
		Main.Entry.store.updateInventory(inventory);

		//Create buttons
		JButton generateManifestButton = new JButton("Generate Manifest");
		JButton loadManifestButton = new JButton("Load Manifest");
		JButton loadSalesButton = new JButton("Load Sales");
		
		//Load manifest and sales buttons are disabled until first manifest is created
		loadManifestButton.setEnabled(false);
		loadSalesButton.setEnabled(false);
		
		//Add label for showing store capital and set it to appropriate value
		JLabel capitalLabel = new JLabel();
		capitalLabel.setText("Store Capital: $" + decimalFormat.format(Main.Entry.store.getCapital()));
		capitalLabel.setFont(new Font("Helvitica", Font.BOLD, 24));
		
		//Create table
		model = new TableModel(inventory.getItems());
		table = new JTable(model);
		
		//Table sizing
		table.setRowHeight(25);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModel = table.getColumnModel();
		Dimension tableDim = new Dimension(720, 600);
		table.setSize(tableDim);
		table.setPreferredScrollableViewportSize(tableDim);

		//Resize column width
		columnModel.getColumn(0).setPreferredWidth(120);
		columnModel.getColumn(1).setPreferredWidth(120);
		columnModel.getColumn(2).setPreferredWidth(120);
		columnModel.getColumn(3).setPreferredWidth(120);
		columnModel.getColumn(4).setPreferredWidth(120);
		columnModel.getColumn(5).setPreferredWidth(120);

		//Create group panel
		JPanel groupPanel = new JPanel();

		//Add components to panel
		groupPanel.add(generateManifestButton);
		groupPanel.add(loadManifestButton);
		groupPanel.add(loadSalesButton);
		groupPanel.add(loadSalesButton);
		groupPanel.add(new JScrollPane(table));
		groupPanel.add(capitalLabel);
		
		//Create the window with title
		frame = new JFrame("Inventory Manager");
		frame.add(groupPanel);
		frame.setSize(900, 740);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		
		//BUTTON LISTENERS//
		//Activates when load sales button is pressed
		loadSalesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//Open window for user to select file to load
					chooseFile();
					if (fileChosen) {
						file = CSV.ReadCSV.readSales(filename);
						
						//Goes through CSV data
						for (int i = 0; i < file.length; i++) {
							String xval = file[i][0];
							for (int j = 0; j < inventory.getSize(); j++) {
								if (xval.equals(inventory.getItem(j).getName())) {
									//Updates item quantity from sales data
									inventory.getItem(j).subtractQuantity(Integer.parseInt(file[i][1]));
									//Gets cost of each item and multiplies by quantity
									//Increase store capital with resulting value
									Main.Entry.store.setCapital(Main.Entry.store.getCapital()
											+ (Integer.parseInt(file[i][1]) * inventory.getItem(i).getPrice()));
									//Display updated capital to label (gain of capital)
									capitalLabel.setText(
											"Store Capital: $" + decimalFormat.format(Main.Entry.store.getCapital()));
								}
							}
						}
						Main.Entry.store.updateInventory(inventory);
						model.fireTableDataChanged();						
						fileChosen = false;
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (CSVFormatException e1) {
					e1.printStackTrace();
				}
				
				//Allow user to now press generate manifest button
				generateManifestButton.setEnabled(true);
			}
		});

		//Activates when load manifest button is pressed
		loadManifestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//Open window for user to select file to load
					chooseFile();
					
					if (fileChosen) {
						manifestFile = CSV.ReadCSV.readManifest(filename);
						
						//Goes through CSV data
						for (int i = 0; i < inventory.getSize(); i++) {
							String inventoryVal = inventory.getItem(i).getName();
							for (int j = 0; j < manifestFile.size(); j++) {
								String fileVal = manifestFile.get(j).get(0);
								if (inventoryVal.equals(fileVal)) {
									//Updates item quantity from manifest data
									inventory.getItem(i).addQuantity(Integer.parseInt(manifestFile.get(j).get(1)));
									//Gets cost of each item and multiplies by quantity
									//Increase store capital with resulting value
									Main.Entry.store.setCapital(Main.Entry.store.getCapital()
											- (Integer.parseInt(manifestFile.get(j).get(1))
													* inventory.getItem(i).getCost()));
									//Display updated capital to label (loss of capital)
									capitalLabel.setText(
											"Store Capital: $" + decimalFormat.format(Main.Entry.store.getCapital()));
								}
							}
						}
						fileChosen = false;
						model.fireTableDataChanged();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (CSVFormatException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				//It is now possible for users to load a manifest
				//So enable load manifest button
				loadManifestButton.setEnabled(false);
				
				//Enable load sales button
				//because items under restock have been ordered
				loadSalesButton.setEnabled(true);

			}
		});
		
		//Activates when generate manifest button is pressed
		generateManifestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Manifest.getItemDetails(Main.Entry.store.getInventory().getItems());
				Manifest.sortTemp();
				Manifest.cooledLogistics();
				Manifest.ordinaryLogistics();
				try {
					CSV.WriteCSV.writeManifest();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println(Manifest.getLogisticsCost());
				
				//It is now possible to load the new manifest
				//So enable the load manifest button
				loadManifestButton.setEnabled(true);
				
				//Disable generate manifest button as it has just been pressed
				generateManifestButton.setEnabled(false);
			}

		});
	}

	//Used to bring a window up which allows users to select a file to load
	public static void chooseFile() throws FileNotFoundException, CSVFormatException {
		String userDir = System.getProperty("user.dir");
		JFileChooser fileChooser = new JFileChooser(userDir + "/test_files");
		int result = fileChooser.showOpenDialog(table);

		if (result == JFileChooser.APPROVE_OPTION) {
			filename = fileChooser.getSelectedFile().getAbsolutePath();
			fileChosen = true;
			JOptionPane.showMessageDialog(null, "File Loaded Successfully");
		}
	}

}