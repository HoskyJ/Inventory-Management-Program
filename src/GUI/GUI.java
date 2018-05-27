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

	public static void initializeWindow() throws FileNotFoundException, CSVFormatException {
		Stock inventory = new Stock();
		inventory.initializeStock(CSV.ReadCSV.readItemProperties("test_files/item_properties.csv"));
		Main.Entry.store.UpdateInventory(inventory);

		// create frame
		frame = new JFrame("Inventory Manager");
		// create buttons
		JButton generateManifestButton = new JButton("Generate Manifest");
		JButton loadManifestButton = new JButton("Load Manifest");
		JButton loadSalesButton = new JButton("Load Sales");
		loadManifestButton.setEnabled(false);
		loadSalesButton.setEnabled(false);

		JLabel capitalLabel = new JLabel();
		capitalLabel.setText("Store Capital: $" + decimalFormat.format(Main.Entry.store.getCapital()));
		capitalLabel.setFont(new Font("Helvitica", Font.BOLD, 24));

		// action listener for sales button
		loadSalesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					chooseFile();

					if (fileChosen) {
						file = CSV.ReadCSV.readSales(filename);

						for (int i = 0; i < file.length; i++) {
							String xval = file[i][0];
							for (int j = 0; j < inventory.getSize(); j++) {
								if (xval.equals(inventory.getItem(j).getName())) {
									// updates item quantity
									inventory.getItem(j).subtractQuantity(Integer.parseInt(file[i][1]));
									// gets cost of each item and multiplies by quantity
									Main.Entry.store.setCapital(Main.Entry.store.getCapital()
											+ (Integer.parseInt(file[i][1]) * inventory.getItem(i).getSellPrice()));
									// display updates capital to label
									capitalLabel.setText(
											"Store Capital: $" + decimalFormat.format(Main.Entry.store.getCapital()));
								}
							}
						}
						Main.Entry.store.UpdateInventory(inventory);
						model.fireTableDataChanged();						
						fileChosen = false;
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (CSVFormatException e1) {
					e1.printStackTrace();
				}
				generateManifestButton.setEnabled(true);
			}
		});

		// action listener for manifest button
		loadManifestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					chooseFile();

					if (fileChosen) {
						manifestFile = CSV.ReadCSV.readManifest(filename);

						for (int i = 0; i < inventory.getSize(); i++) {
							String inventoryVal = inventory.getItem(i).getName();
							for (int j = 0; j < manifestFile.size(); j++) {
								String fileVal = manifestFile.get(j).get(0);
								if (inventoryVal.equals(fileVal)) {
									// updates item quantity
									inventory.getItem(i).addQuantity(Integer.parseInt(manifestFile.get(j).get(1)));
									// gets cost of each item and multiplies by quantity
									Main.Entry.store.setCapital(Main.Entry.store.getCapital() - (Integer.parseInt(manifestFile.get(j).get(1))
													* inventory.getItem(i).getCost()));
									// display updates capital to label
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
				loadSalesButton.setEnabled(true);

			}
		});

		generateManifestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Manifest.GetItemDetails(Main.Entry.store.GetCurrentyInventory().getItems());
				Manifest.SortTemp();
				Manifest.CooledLogistics();
				Manifest.OrdinaryItems();
				try {
					CSV.WriteCSV.WriteManifest();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println(Manifest.GetLogisticsCost());

				loadManifestButton.setEnabled(true);
				generateManifestButton.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Manifest Generated!");
			}

		});

		// get stock
		Dimension tableDim = new Dimension(720, 600);

		// create table
		model = new TableModel(inventory.getItems());
		table = new JTable(model);
		table.setRowHeight(25);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModel = table.getColumnModel();
		table.setSize(tableDim);
		table.setPreferredScrollableViewportSize(tableDim);

		// resize column width
		columnModel.getColumn(0).setPreferredWidth(120);
		columnModel.getColumn(1).setPreferredWidth(120);
		columnModel.getColumn(2).setPreferredWidth(120);
		columnModel.getColumn(3).setPreferredWidth(120);
		columnModel.getColumn(4).setPreferredWidth(120);
		columnModel.getColumn(5).setPreferredWidth(120);

		// create group panel
		JPanel groupPanel = new JPanel();

		// add components to panel
		groupPanel.add(generateManifestButton);
		groupPanel.add(loadManifestButton);
		groupPanel.add(loadSalesButton);
		groupPanel.add(loadSalesButton);
		groupPanel.add(new JScrollPane(table));
		groupPanel.add(capitalLabel);

		// setup frame
		frame.add(groupPanel);
		frame.setSize(900, 740);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

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