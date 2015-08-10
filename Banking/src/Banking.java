import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Banking {

	public static void main(String[] args) {
		System.out.println("Welcome to Evil Corp Saving and Loan");
		System.out.println();
		// variables decelerations
		Scanner keyboard = new Scanner(System.in);
		String choice = "", tranchoice = "";
		int accountNo = 0;
		Date date = new Date();
		SimpleDateFormat inDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Accounts acc = new Accounts();
	
		ArrayList <Accounts> accList = new ArrayList <Accounts>();
		while (!choice.equals("-1")) {
			
			System.out.println("Please create the user account(s)");
			System.out.println();
			int account = Validator.getInt(keyboard,
					"Enter an account # or -1 to stop entering accounts : ");
			if (account != -1) {
				String accountName = Validator.getString(keyboard,
						"Enter the name for acct # " + account + " : ");
				double accountBalance = Validator.getDouble(keyboard,
						"Enter the balance for acct #  " + account + " : ", 0,
						Double.MAX_VALUE);
				acc.addAcount(account, accountName, accountBalance);
				accList.add(acc);
				acc = new Accounts();
			} else
				choice = "-1";
		}
		System.out.println();
		while (!tranchoice.equals("-1")) {
			String transaction = Validator
					.getString(
							keyboard,
							"Enter a transaction type (Check (C), Debit card (DC), Deposit(D) or Withdrawal(W)) or -1 to finish : ");
			
			if (!transaction.equals("-1")) {
				
				accountNo = Validator
						.getInt(keyboard, "Enter the account # : ");
				if (acc.getAccount(accountNo) != null) {
					acc=acc.getAccount(accountNo);
					double amount = Validator.getDouble(keyboard,
							"Enter the amount of the "+accountNo + " : ", 0,
							Double.MAX_VALUE);
					String tranDateStr = Validator.getString(keyboard,
							"Enter the date of the check: (MM/dd/yyyy) ");
					GregorianCalendar gregorianCalendar = new GregorianCalendar(Integer.parseInt(tranDateStr.substring(6)),Integer.parseInt(tranDateStr.substring(0,2))-1,Integer.parseInt(tranDateStr.substring(3,5)));
					/*	try {
						date = inDateFormat.parse(tranDateStr);

					} catch (ParseException e) {
						System.out.println(e.toString());
						break;
					}
					*/
					acc.addTran(transaction, accountNo, amount, gregorianCalendar);

				} else {
					System.out
							.print("The account number does not exist make sure you entered the right number");
				}
			} else
				tranchoice = "-1";
			
		
			System.out.println();
		}
		for( Accounts accounts : accList)
			{
					accounts.runTransactions();
		System.out.print("The balance for account " + accounts.getAccount()+ " is "
				+ accounts.getAccountBalance());
	}
	}
}

