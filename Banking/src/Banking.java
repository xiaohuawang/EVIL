import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;

public class Banking {

	public static void main(String[] args) {
		System.out.println("Welcome to Evil Corp Saving and Loan");
		System.out.println();
		// variables decelerations
		Scanner keyboard = new Scanner(System.in);
		String choice = "", tranchoice = "", choiceToclose = "";
		int accountNo = 0;
		Accounts acc = new Accounts();
		HashMap<Integer, String> accountsHash = new HashMap<Integer, String>();
		HashMap<Integer, String> transactionsHash = new HashMap<Integer, String>();
		// ArrayList <Accounts> accList = new ArrayList <Accounts>();
		String filename = (System.getProperty("user.dir") + File.separatorChar + "accounts.txt");
		String filename2 = (System.getProperty("user.dir") + File.separatorChar + "transactions.txt");
		accountsHash = readLines(new File(filename), accountsHash);

		while (!choice.equals("-1")) {

			System.out.println("Please create the user account(s)");
			System.out.println();
			for (Integer key : accountsHash.keySet()) {
				System.out.println("The balance for account " + key + " is "
						+ accountsHash.get(key));
			}
			choiceToclose = Validator.getString(keyboard,
					"Do you want to close an existing account? (y/n) : " );
			int account = Validator.getInt(keyboard,
					"Enter an account # or -1 to stop entering accounts : ");
			if(choiceToclose.equalsIgnoreCase("y"))
			{
				if (account != -1) 
				{
					if(accountsHash.containsKey(account))
					{
						String[] Key_value_pair = accountsHash.get(account)
								.split(" ");
						double balance =Double.parseDouble(Key_value_pair[Key_value_pair.length - 1]);
						if(balance == 0)
						{
							accountsHash.remove(account, accountsHash.get(account));
							System.out.println("Account " +account +" was closed");
							for (Integer key : accountsHash.keySet()) {
								System.out.println("The balance for account " + key + " is "
										+ accountsHash.get(key));
								writeHashMap(accountsHash, filename);
							}
						}
						else
							System.out.println("Cannot delete this accout beacause it has $" +balance);
					}
					else
						System.out.println("This account does not exist!");
				}
				else
				{
					choice = "-1";
					break;
				}
			}
			else if(choiceToclose.equalsIgnoreCase("n"))
			{
			if (account != -1) {
				String accountName = Validator.getString(keyboard,
						"Enter the name for acct # " + account + " : ");
				double accountBalance = Validator.getDouble(keyboard,
						"Enter the balance for acct #  " + account + " : ", 0,
						Double.MAX_VALUE);
				acc.addAcount(account, accountName, accountBalance);
				// accList.add(acc);
				accountsHash.put(acc.getAccount(), "	" + acc.getAccountName()
						+ " " + acc.getAccountBalance());
				acc = new Accounts();
			} else
				choice = "-1";
			}
			else
				System.out.println("Y /N !!!!");
		}
		System.out.println();
		transactionsHash = readLines(new File(filename2), transactionsHash);
		
		while (!tranchoice.equals("-1")) {

			String transaction = Validator
					.getString(
							keyboard,
							"Enter a transaction type (Check (C), Debit card (DC), Deposit(D) or Withdrawal(W)) or -1 to finish : ");

			if (!transaction.equals("-1")) {

				accountNo = Validator
						.getInt(keyboard, "Enter the account # : ");
				if (accountsHash.containsKey(accountNo)) {
					acc.setAccount(accountNo);

					double amount = Validator.getDouble(keyboard,
							"Enter the amount of the " + accountNo + " : ", 0,
							Double.MAX_VALUE);
					String tranDateStr = Validator.getString(keyboard,
							"Enter the date of the check: (MM/dd/yyyy) ");
					GregorianCalendar gregorianCalendar = new GregorianCalendar(
							Integer.parseInt(tranDateStr.substring(6)),
							Integer.parseInt(tranDateStr.substring(0, 2)) - 1,
							Integer.parseInt(tranDateStr.substring(3, 5)));

					transaction = acc.addTran(transaction, accountNo, amount,
							gregorianCalendar);
					transactionsHash.put(accountNo,
							"	" + transaction + " " + amount + "	"
									+ gregorianCalendar.toZonedDateTime());
					String[] Key_value_pair = accountsHash.get(accountNo)
							.split(" ");
					acc.setAccountName(Key_value_pair[Key_value_pair.length - 2]);
					acc.setAccountBalance(Double
							.parseDouble(Key_value_pair[Key_value_pair.length - 1]));
					acc.updateBalance(transaction, amount);
					accountsHash.replace(accountNo, " " + Key_value_pair[0]
							+ " " + acc.getAccountBalance());
				} else {
					System.out
							.print("The account number does not exist make sure you entered the right number");
				}
			} else
				tranchoice = "-1";

			System.out.println();
			for (Integer key : accountsHash.keySet()) {
				if (acc.runTransactions((int) key)) {
					System.out.println("The balance for account " + key +" for " + acc.getAccountName()
							+ " is " + acc.getAccountBalance());
				} else {
					System.out.println("The balance for account " + key
							+ " is " + accountsHash.get(key));
				}

			}
		}

		writeHashMap(accountsHash, filename);
		writeHashMap(transactionsHash, filename2);
	}

	public static HashMap<Integer, String> readLines(File file,
			HashMap<Integer, String> hashmap) {
		if (!file.exists()) {
			return hashmap;
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {

				String[] Key_value_pair = line.split("\t");
				hashmap.put(Integer.parseInt(Key_value_pair[0]),
						Key_value_pair[Key_value_pair.length - 1]);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashmap;
	}

	public static HashMap<Integer, String> writeHashMap(
			HashMap<Integer, String> n, String filename) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(new File(filename));
			for (Integer key : n.keySet())
				writer.println("" + key + "	" + n.get(key));
			// you must close the PrintWriter
			writer.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return n;
	}
}
