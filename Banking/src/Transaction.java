import java.util.Date;
import java.util.GregorianCalendar;


public class Transaction {

	private String transactionType;
	private double amount;
	private GregorianCalendar tranDate;
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		
		switch(transactionType.toLowerCase())
		{
		case "c": this.transactionType ="check"; break;
		case "d": this.transactionType ="deposit"; break;
		case "w": this.transactionType ="withdrawal"; break;
		case "dc": this.transactionType ="debit card"; break;
		}
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public GregorianCalendar getTranDate() {
		return tranDate;
	}
	public void setTranDate(GregorianCalendar tranDate) {
		this.tranDate = tranDate;
	}
	
	public Transaction()
	{
		this.tranDate = new GregorianCalendar();
		this.transactionType = "check";
		this.amount = 0;
	}
}
