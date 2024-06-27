package compPay;

public class Employee {
	
	private String fullName;
	private int hoursWorked;
	private double baseRate;
	private double totalPay;
	
	public Employee() {
		
	}
	
	public Employee(String fName, int hWorked, double bRate, double tPay) {
		this.setFullName(fName);
		this.setHoursWorked(hWorked);
		this.setBaseRate(bRate);
		this.setTotalPay(tPay);
	}
	
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getHoursWorked() {
		return hoursWorked;
	}
	public void setHoursWorked(int hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	public double getBaseRate() {
		return baseRate;
	}
	public void setBaseRate(double baseRate) {
		this.baseRate = baseRate;
	}
	public double getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(double totalPay) {
		this.totalPay = totalPay;
	}
	
	
	
}
