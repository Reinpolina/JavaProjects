package arithmeticOperations;
import javax.swing.JOptionPane;

public class Arithmetic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input1;
		String input2;		
		String operationSign;		
        float finalResult = 0;
        String invalid = "0";
		
		input1  = JOptionPane.showInputDialog("Please Enter A Number:  ");                        //storing user input as string
		operationSign  = JOptionPane.showInputDialog("Please Enter Operation Sign:  ");          //storing user input as string
		input2 = JOptionPane.showInputDialog("Please Enter Another Number:  ");                 //storing user input  as string

		float covertedinput1 =  Float.parseFloat(input1);                                   // storing user input  as string

		float covertedinput2 =  Float.parseFloat(input2);
		
		
		
		if(operationSign.charAt(0) == '+') {                                  // convert operation string to character, then compare, then add
			finalResult = covertedinput1 + covertedinput2; 
			
		}
		else if(operationSign.charAt(0) == '-') {                            //convert operation string to character, then compare, then subtract 
			finalResult = covertedinput1 - covertedinput2;
		}
		else if(operationSign.charAt(0) == '/') {
			finalResult = covertedinput1 / covertedinput2;                 // convert operation string to character, then compare, then divide 
		}
		else if (operationSign.charAt(0) == '*') {
			finalResult = covertedinput1 * covertedinput2;                // convert operation string to character, then compare, then multiply
		}   
		else {
			invalid = String.valueOf(finalResult);                        // convert final result into string if value is invalid to let user know
			
			//finalResult = 0; 
		}
		
		
		String result;
		if(operationSign.charAt(0) != '+' || operationSign.charAt(0) != '-' || operationSign.charAt(0) == '/' || operationSign.charAt(0) != '*') {
			result = String.format("invalid operater %s", invalid) + "\n";

		}
		else {	
			result = String.format(" %1$.1f %2$s %3$.1f =  %4$.1f", covertedinput1, operationSign, covertedinput2, finalResult) + "\n";
		}
		
	    JOptionPane.showMessageDialog( null, result);
		
		
		

	}

}
