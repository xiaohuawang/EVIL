import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;



public class Accounts {

	private int account;
	private String accountName;
	private double accountBalance;
	
	private static ArrayList <Accounts> accountslist = new ArrayList <Accounts>(); 
	private static ArrayList <Transaction> transactionslist = new ArrayList <Transaction>(); 
	public ArrayList<Accounts> getAccountslist() {
		return getAccountslist();
	}
	public void setAccountslist(ArrayList<Accounts> accountslist2) {
		accountslist = accountslist2;
	}
	public HashMap<Integer,  ArrayList <Transaction>> getTransactionList() {
		return transactionHash;
	}
	public void setTransactionList(HashMap<Integer,  ArrayList <Transaction>> transactionList) {
		this.transactionHash = transactionList;
	}

	private static HashMap <Integer , ArrayList <Transaction>> transactionHash = new HashMap <Integer,  ArrayList <Transaction>>();

	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public Accounts()
	{
		this.account =0;
		this.accountName ="";
		this.accountBalance =0;
		Accounts.transactionslist = new ArrayList <Transaction>();
	}
	
	public void addAcount(int account, String accountName, double accountBalance)
	{
		this.account = account;
		this.accountName = accountName;
		this.accountBalance = accountBalance;
		accountslist.add(this);
	}
	
	public void addTran(String tranType, int accountNo, double amount, GregorianCalendar tranDate)
	{
		Transaction tran = new Transaction();
		tran.setAmount(amount);
		tran.setTranDate(tranDate);
		tran.setTransactionType(tranType);
		if(transactionHash.containsKey(accountNo))
		{
			transactionslist = transactionHash.get(accountNo);
			transactionslist.add(tran);
			transactionHash.replace(accountNo, transactionslist);
		}
		else
		{
			transactionslist = new ArrayList <Transaction>();
			transactionslist.add(tran);
		transactionHash.put(accountNo,transactionslist);
		}
	
	}
	public void updateBalance(String tranType, double amount)
	{
		switch(tranType)
		{
		case "check": this.sub(amount); break;
		case "deposit": this.add(amount); break;
		case "withdrawal": this.sub(amount); break;
		case "debit card": this.sub(amount); break;
		}
	}
	
	public void sub(double amount)
	{
		this.accountBalance -= amount;
		if(this.accountBalance < 0)
			this.accountBalance -= 35;
	}
	
	public void add(double amount)
	{
		this.accountBalance += amount;
	}
	
	public void runTransactions()
	{
		
		transactionslist = transactionHash.get(account);
		if(transactionslist.size()>1)
		{
		Collections.sort(transactionslist, new Comparator<Transaction>()
				{
			  public int compare(Transaction o1, Transaction o2) {
			      if (o1.getTranDate() == null || o2.getTranDate() == null)
			        return 0;
			      return o1.getTranDate().compareTo(o2.getTranDate());
			  }
			});
		}
		if(transactionslist.size()>0)
		{
			System.out.println("********************Transactions********************");
			System.out.println("Transaction Type    	     Amount			Date");
		for(Transaction tran :transactionslist)
		{
			System.out.println("   "+tran.getTransactionType()+"    	     "+tran.getAmount() +"    	     "+tran.getTranDate().toZonedDateTime());
			updateBalance(tran.getTransactionType(), tran.getAmount());
		}
		System.out.println();
		}
		
	}
	public Accounts getAccount(int accountNo)
	{
		for ( Accounts account: accountslist)
		{
			 if(account.getAccount()==accountNo)
			{
				 return account;
			}
		}
		return null;
	}
}