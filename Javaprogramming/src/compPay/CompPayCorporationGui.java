package compPay;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class CompPayCorporationGui extends JFrame {
	private static final long serialVersionUID = 1L;

	
	////////////////////////////////////////////
	////STEP ONE
	////////////////////////////////////////////
	private JButton btnCalculate;
	private JButton btnClear;
	private JButton btnSaveData;
	private JButton btnIncreasePay;

	private JLabel lblEmployeeName;
	private JLabel lblBasePay;
	private JLabel lblHoursWorked;

	private JTextField txtFullname;
	private JTextField txtBasePay;
	private JTextField txtHoursWorked;

	private JPanel dataPanel;
	private JPanel tablePanel;

	private JTable dataTable;

	private JScrollPane scrollPane;
	private int numberOfEmployee = 0;

	
	
	///////////////////////////////////////////////////////
	//////////////STEP TWO
	////////////////////////////////////////////////////////
	public CompPayCorporationGui() throws FileNotFoundException {
		// properties of the Frame
		super("Calculate Pay");
		setLocation(100, 400);
		setSize(850, 250);
		setLayout(null);
		setResizable(false);

		// panel to contain the gui elements
		dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(0, 2));

		// creating labels for the frame
		lblEmployeeName = new JLabel("Employee Name");
		lblBasePay = new JLabel("Base Pay");
		lblHoursWorked = new JLabel("Hours Worked");

		//creating textfields
		txtFullname = new JTextField();
		txtBasePay = new JTextField();
		txtHoursWorked = new JTextField();

		//creating buttons
		btnCalculate = new JButton("Calculate");
		btnClear = new JButton("Clear");
		btnSaveData = new JButton("Save to File");
		btnIncreasePay = new JButton("Increase Pay");

		//adding labels to the pane, text-alignment:center
		lblEmployeeName.setHorizontalAlignment(SwingConstants.CENTER);
		dataPanel.add(lblEmployeeName);
		dataPanel.add(txtFullname);

		//adding labels to the pane, text-alignment:center
		lblBasePay.setHorizontalAlignment(SwingConstants.CENTER);
		dataPanel.add(lblBasePay);
		dataPanel.add(txtBasePay);

		//adding labels to the pane, text-alignment:center
		lblHoursWorked.setHorizontalAlignment(SwingConstants.CENTER);
		dataPanel.add(lblHoursWorked);
		dataPanel.add(txtHoursWorked);

		//adding the buttons to the frame
		dataPanel.add(btnClear);
		dataPanel.add(btnCalculate);
		
		//adding the saveFile button to the frame. 
		btnSaveData.setBounds(630, 180, 200, 30);
		add(btnSaveData);
		
		//adding the increasepay button to the frame
		btnIncreasePay.setBounds(430,180, 200, 30);
		add(btnIncreasePay);
		//creating a panel for the Jtable display
		tablePanel = new JPanel();
		tablePanel.setLayout(new GridLayout(0, 1));

		//columns for the JTable
		String column[] = { "Emp.Name", "BasePay", "HoursWorked", "TotalPay" };

		//creating a defaultTable model to handle tablle data
		DefaultTableModel model = new DefaultTableModel(column, 0);
		dataTable = new JTable(model);

		//the table is placed in a scrollpanel
		scrollPane = new JScrollPane(dataTable);

		
		//adding the scroll panel to the display panel
		tablePanel.add(scrollPane);

		//attaching an actionListener to the clear button
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtHoursWorked.setText("");
				txtBasePay.setText("");
				txtFullname.setText("");
			}
		});

		//attaching an actionListener to the calculate button
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double totalPay = 0.00;

				// collecting data
				String fullname = txtFullname.getText();
				double basePay = Double.parseDouble(txtBasePay.getText());
				int hoursWorked = Integer.parseInt(txtHoursWorked.getText());

				if (basePay < 8.00) {
					JOptionPane.showMessageDialog(null, "Base pay must not be below minimum wage($8)");
				} else if (hoursWorked > 60) {
					JOptionPane.showMessageDialog(null, "You have worked too many hours, we can't pay you.");
				} else {

					if (hoursWorked > 40) {
						totalPay = basePay * hoursWorked + 1.5 * basePay * (hoursWorked - 40);
					} else {
						totalPay = basePay * hoursWorked;
					}

					String outputDisplay = String.format(
							"Employee Name: %s%n  Hours Worked: %d%n Base Pay: %.2f%n total Pay: %.2f", fullname,
							hoursWorked, basePay, totalPay);

					JOptionPane.showMessageDialog(null, outputDisplay);
					
					model.addRow(new Object[] { fullname, String.valueOf(basePay), String.valueOf(hoursWorked),
							String.valueOf(totalPay) });

					txtHoursWorked.setText("");
					txtBasePay.setText("");
					txtFullname.setText("");
				}

			}
		});

		//adding a listener to the selectionModel of the JTable
		ListSelectionModel select = dataTable.getSelectionModel();
		select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//adding the listener. 
		select.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				int selectedRow = dataTable.getSelectedRow();
				txtFullname.setText((String)model.getValueAt(selectedRow, 0));
				txtBasePay.setText(String.valueOf(model.getValueAt(selectedRow, 1)));
				txtHoursWorked.setText(String.valueOf(model.getValueAt(selectedRow, 2)));

			}
		});

		//attaching an actionListener to the save button
		btnSaveData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					writeToFile(dataTable);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//attaching an actionListern to the increase pay button
		btnIncreasePay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					increasePay(dataTable);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// this.setContentPane(dataPanel);
		dataPanel.setBounds(20, 20, 400, 150);
		tablePanel.setBounds(430, 20, 400, 150);

		//adding the panels to the frame. 
		add(dataPanel);
		add(tablePanel);

		//deciding to exit the program when close button clicked
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		//initializing the JTable with data from the file. 
		// load data from file
		this.readFromFile(dataTable);
	}

	
	
	
	///////////////////////////////////////////////////////////
	//////////////STEP THREE
	///////////////////////////////////////////////////////////

	/**
	 * This method is used to write from the jtable to the file. 
	 * @param dataTable
	 * @throws FileNotFoundException
	 */
	public void writeToFile(JTable dataTable) throws FileNotFoundException {
		File newFile = new File("//Users//lusianaaltidor//eclipse-workspace//Javaprogramming//src//compPay//pay.dat");

		//We use a printWriter class to do the writing to a file. 
		PrintWriter outFile = new PrintWriter(newFile);

		for (int row = 0; row < dataTable.getRowCount(); row++) {
			for (int col = 0; col < dataTable.getColumnCount(); col++) {
				outFile.print(dataTable.getValueAt(row, col));
				outFile.print(",");
			}
			outFile.println(" ");
		}

		outFile.close();
	}

	
	///////////////////////////////////////////////////////////////////////
	///////////////////////STEP FOUR
	///////////////////////////////////////////////////////////////////////
	/**
	 * The method is used to read the data from the file and add it to the jtable. 
	 * @param dataTable
	 * @throws FileNotFoundException
	 */
	public void readFromFile(JTable dataTable) throws FileNotFoundException {
		File newFile = new File("//Users//lusianaaltidor//eclipse-workspace//Javaprogramming//src//compPay//pay.dat");

		//scanner object is used to read from the file
		Scanner readFile = new Scanner(newFile);

		//auxiliary variable to handle datafrom the file. 
		String fileData[];
		Vector<Object> rowData;

		while (readFile.hasNext()) {
			//read the whole line from the file
			//eg. Kwame Ghana,9.0,56,720.0, 
			String fileLine = readFile.nextLine();
			
			//split the line data into bit
			//store it int the fileData array
			fileData = fileLine.split(",");
			
			//create a new datastructure
			//vector to be used to add rows to the jTable. 
			rowData = new Vector<Object>();
			
			//loading up the vector datastructure
			rowData.add(fileData[0]);
			rowData.add(Double.parseDouble(fileData[1]));
			rowData.add(Integer.parseInt(fileData[2]));
			rowData.add(Double.parseDouble(fileData[1]) * Integer.parseInt(fileData[2]));
			
			//calling the data model for the jtable to add to the rows
			DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
			model.addRow(rowData);

		}
	}
	
	/**
	 * method to increase the pay of the employees by 10percent
	 * @param dataTable
	 * @throws FileNotFoundException
	 */
	public void increasePay(JTable dataTable) throws FileNotFoundException {
		File newFile = new File("C:\\Users\\kwabe\\javaprogramming\\programming\\src\\compPay\\pay.dat");

		//scanner object is used to read from the file
				Scanner readFile = new Scanner(newFile);

				//auxiliary variable to handle datafrom the file. 
				String fileData[];
				Vector<Object> rowData;
				//calling the data model for the jtable to add to the rows
				DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
				model.setRowCount(0);
				
				while (readFile.hasNext()) {
					//read the whole line from the file
					//eg. Kwame Ghana,9.0,56,720.0, 
					String fileLine = readFile.nextLine();
					
					//split the line data into bit
					//store it int the fileData array
					fileData = fileLine.split(",");
					
					//create a new datastructure
					//vector to be used to add rows to the jTable. 
					rowData = new Vector<Object>();
					
					//loading up the vector datastructure
					rowData.add(fileData[0]);
					rowData.add(String.format("%.2f", Double.parseDouble(fileData[1]) * 1.10));
					rowData.add(Integer.parseInt(fileData[2]));
					rowData.add(String.format("%.2f",Double.parseDouble(fileData[3]) * 1.10));
					
					model.addRow(rowData);
					

				}
		}
	

}
