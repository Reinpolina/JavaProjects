package Project;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;
//import java.util.ArrayList;


import javax.swing.table.DefaultTableModel;


public class BankAccountGui extends JFrame {
	private JPanel dataPanel;
	private JPanel tablePanel;
	private JPanel buttonPanel;
	
	private JLabel lblAccountNumber;
	private JLabel lblAccountType;
	private JLabel lblAccountMinimumBalance;
	private JLabel lblAccountCurrentBalance;
	
	
	private JTextField txtAccountNumber;
	private JTextField txtAccountType;
	private JTextField txtlblAccountMinimumBalance;
	private JTextField txtlblAccountCurrentBalance;
	
	private JButton btnClear;
	private JButton btnSave;
	private JButton btncalculateInterest;
	
	private JTable dataTable;
	private JScrollPane scrollPane;
	
	//Step 2 properties of the frame
	public BankAccountGui() throws FileNotFoundException {
		super("BankAccount App");   //fix this
		setLocation(100,400);
		setSize(950,310);
		setLayout(null);
		setResizable(false);
		
	
	// instantiate all the control
	dataPanel = new JPanel();
	dataPanel.setLayout(new GridLayout(0,2));
	
	buttonPanel = new JPanel();
	buttonPanel.setLayout(new GridLayout(1,3));
			
	tablePanel = new JPanel();
	
	btnClear = new JButton("Clear");
	btnSave = new JButton("Save");
	btncalculateInterest = new JButton("calculate Interest");
	
	txtAccountNumber = new JTextField(50);
	txtAccountType = new JTextField(50);
	txtlblAccountMinimumBalance = new JTextField(50);
	txtlblAccountCurrentBalance = new JTextField(50);
	
	lblAccountNumber = new JLabel("Account Number");
	lblAccountType  = new JLabel("Account Type");
	lblAccountMinimumBalance = new JLabel("Minimum Balance");
	lblAccountCurrentBalance = new JLabel("Current Balance");
	
	dataPanel.add(lblAccountNumber);
	dataPanel.add(txtAccountNumber);
	dataPanel.add(lblAccountType);
	dataPanel.add(txtAccountType);
	dataPanel.add(lblAccountCurrentBalance);
	dataPanel.add(txtlblAccountCurrentBalance);
	dataPanel.add(lblAccountMinimumBalance);
	dataPanel.add(txtlblAccountMinimumBalance);
	
	dataPanel.setBounds(20,20,400,150);
	add(dataPanel);
	
	buttonPanel.add(btnSave);
	buttonPanel.add(btnClear);
	buttonPanel.add(btncalculateInterest);
	
	
	
	//adding jtable to the Frame
	String column[] = {"Account NO.", "AccType", "CurrBalance", "MinBalance"};
	
	DefaultTableModel model = new DefaultTableModel(column,0);
	dataTable = new JTable(model);
	
	//adding the table to the scrollpane
	scrollPane = new JScrollPane(dataTable);
	scrollPane.setBounds(20,20,350,100);
	
	//adding the tablepanel to the screen
	tablePanel.add(scrollPane);
	tablePanel.setBounds(440,20,500,150);
	add(tablePanel);
	
	
	btnClear.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtAccountNumber.setText(" ");
			txtAccountType.setText("");
			txtlblAccountCurrentBalance.setText("");
			txtlblAccountMinimumBalance.setText("");
		}
		
	});
	
	////////// new
	
	btncalculateInterest.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			try {
				calculateInterest(dataTable);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
			txtAccountNumber.setText((String)model.getValueAt(selectedRow, 0));
			txtAccountType.setText(String.valueOf(model.getValueAt(selectedRow, 1)));
			txtlblAccountCurrentBalance.setText(String.valueOf(model.getValueAt(selectedRow, 2)));
			txtlblAccountMinimumBalance.setText(String.valueOf(model.getValueAt(selectedRow, 3)));

		}
	});
	
	//dddddddddddddd
	
	// attaching an actionListener to the save button
	btnSave.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				String outputDisplay = String.format(
				"AccountNo.: %s%n AccountType: %s%n currBalance: %s%n minimumBalance %s%n",
				txtAccountNumber.getText(),
				txtAccountType.getText(),
				txtlblAccountCurrentBalance.getText(),
				txtlblAccountMinimumBalance.getText());
				
				JOptionPane.showMessageDialog(null, outputDisplay);
				
				model.addRow(new Object[] {
					txtAccountNumber.getText(),
					txtAccountType.getText(),
					txtlblAccountCurrentBalance.getText(),
					txtlblAccountMinimumBalance.getText() });
					txtAccountNumber.setText("");
					txtAccountType.setText("");
					txtlblAccountCurrentBalance.setText("");
					txtlblAccountMinimumBalance.setText("");
					writeToFile(dataTable);
			} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
		}
		
	});
	
	
	buttonPanel.setBounds(20,170,400,50);
	add(buttonPanel);
	
	//initializing the JTable with data from the file. // load data from file
	this.readFromFile(dataTable);
	
	
}
	
	
	public void writeToFile(JTable dataTable) throws FileNotFoundException {
		File newFile = new File("/Users/lusianaaltidor/eclipse-workspace/Javaprogramming/src/Project/account.txt");
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
	
	
	public void readFromFile(JTable dataTable) throws FileNotFoundException {
		File newFile = new File("/Users/lusianaaltidor/eclipse-workspace/Javaprogramming/src/Project/account.txt");
		
		Scanner readFile = new Scanner(newFile);                       //read file
		
		String fileData[];                                            //save the data
		
		Vector<Object> rowData;
		
		while(readFile.hasNext()) {
			//read from file
			String fileLine = readFile.next();
			
			//split the fileline data into chuncks
			fileData = fileLine.split(",");
			
			rowData = new Vector<Object>();
			
			rowData.add(fileData[0]);
			rowData.add(fileData[1]);
			rowData.add(Double.parseDouble(fileData[2]));
			rowData.add(Double.parseDouble(fileData[3]));
			
			DefaultTableModel model = (DefaultTableModel)dataTable.getModel();
			model.addRow(rowData);
			
		}
		
		
		
	}
	
	public void calculateInterest(JTable dataTable) throws FileNotFoundException {
		/*File newFile = new File("/Users/lusianaaltidor/eclipse-workspace/Javaprogramming/src/Project/account.txt");

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
				*/
		
		
				double curBal = 0;
				String accTypeString = "";
				char accTypeChar;

				double minBal = 0;
				
		
		
				DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
		
				
				
				int selectedRow = dataTable.getSelectedRow();
				//txtAccountNumber.setText((String)model.getValueAt(selectedRow, 0));
				 accTypeString = String.valueOf(model.getValueAt(selectedRow, 1));
				 accTypeChar = accTypeString.charAt(0);

				 
				curBal =  Double.parseDouble(String.valueOf(model.getValueAt(selectedRow, 2)));
				minBal = Double.parseDouble(String.valueOf(model.getValueAt(selectedRow, 3)));
				
				
				if(accTypeChar == 'c' || accTypeChar == 'C') {
					if(curBal < minBal) {
						curBal -= 25.0;
					} else if(curBal > minBal + 5000) {
						curBal = curBal + (0.05 * curBal);
					} else {
						curBal = curBal + (0.03 * curBal);
					}
					
				} else if (accTypeChar == 's' || accTypeChar == 'S'){
					if(curBal < minBal) {
						curBal -= 10.0;
					} else {
						curBal = curBal + (0.04 * curBal);
					}
				}
				
				
				txtlblAccountCurrentBalance.setText(String.valueOf(curBal));
				//txtlblAccountMinimumBalance.setText("");
				
		}	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		

	}

}
