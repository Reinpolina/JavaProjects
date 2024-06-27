package fahrenheitToCelsius;
import javax.swing.JOptionPane;
public class Temperature {
	public static void main(String[] args) {
	
	String tempInputInFa;
	double celciUs	= 0;
	
	
	double covertToCelsius = 0.0;
	
	
	
	tempInputInFa = JOptionPane.showInputDialog("Please Enter Temperature in Fahrenheit:  ");          //storing user input
	
	
	covertToCelsius = (Integer.parseInt(tempInputInFa) - 32) / 1.8;                              //convert fahrenheit into celcius
			
	String result;
		
	result = String.format("Your input of: %1$s °F in celsius is %2$.2f", tempInputInFa, covertToCelsius)  + "°C" + "\n";
	
		
	JOptionPane.showMessageDialog(null, result); 
		

	}

}
