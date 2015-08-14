import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class Banking {

	public static void main(String[] args) {
		// variables decelerations
		Scanner keyboard = new Scanner(System.in);
		String choice = "", tranchoice = "";
		boolean choiceToclose = false, choiceanotheracc = true, choiceToclose2 = true;
		int accountNo = 0, nextaccountNo = 0;
		Accounts acc = new Accounts();
		Transaction tran = new Transaction();
		HashMap<Integer, String> accountsHash = new HashMap<Integer, String>();
		HashMap<Integer, String> transactionsHash = new HashMap<Integer, String>();
		String sql = "";
		PreparedStatement preStatement = null;
		ResultSet result = null;
		System.out.println("Welcome to Evil Corp Saving and Loan");
		System.out.println();

		String url = "jdbc:oracle:thin:testuser/password@localhost";

		// properties for creating connection to Oracle database
		Properties props = new Properties();
		props.setProperty("user", "testdb");
		props.setProperty("password", "password");

		// creating connection to Oracle database using JDBC
		try {
			Connection conn = DriverManager.getConnection(url, props);

			sql = "select * from ACCOUNTS";
			preStatement = conn.prepareStatement(sql);
			result = preStatement.executeQuery();

			while (result.next()) {

//				accountsHash.put(
//						Integer.parseInt(result.getString("Acct")),
//						result.getString("Name1") + " "
//								+ result.getString("birthday").substring(0, 9)+ " " +
//								Integer.parseInt(result.getString("StartingBalance"))
//								);

				System.out.printf("%s\t%s\t%s\t%s\n",
						result.getString("Accts"),
						result.getString("Name1"),
						result.getString("birthday").substring(0, 9),
						result.getString("StartingBalance"));   
		
				if (nextaccountNo < Integer.parseInt(result
						.getString("Acct")))
					nextaccountNo = Integer.parseInt(result
							.getString("Acct"));
			  }
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

}			
	

			
	/*
			
			while (!choice.equals("-1")) {

				choiceToclose = Validator.getBoolean(keyboard,
						"Is it an existing account? (y/n) : ");

				if (choiceToclose) {
					choiceToclose = Validator.getBoolean(keyboard,
							"Do you want to delete an account? (y/n) : ");
					if (choiceToclose) {
						int account = Validator
								.getInt(keyboard,
										"Enter an account # or -1 to stop entering accounts : ");
						if (account != -1) {

							if (accountsHash.containsKey(account)) {

								String[] Key_value_pair = accountsHash.get(
										account).split(" ");
								double balance = Double
										.parseDouble(Key_value_pair[Key_value_pair.length - 1]);
								if (balance == 0) {
									accountsHash.remove(account,
											accountsHash.get(account));

									// / add delete the account here
									sql = "DELETE from ACCOUNTS WHERE Acct ="
											+ account;
									preStatement = conn.prepareStatement(sql);
									result = preStatement.executeQuery();
									System.out.println("Account " + account
											+ " was closed");
								} else
									System.out
											.println("Cannot delete this accout beacause it has $"
													+ balance);
							}

							else
								System.out
										.println("This account does not exist!");
						} else {
							choice = "-1";
							break;
						}
					} else {
						break;
					}

				

				}
				
				else if (choiceToclose2) {
					while (choiceanotheracc) {

						Accounts.setNextAcountNumber(++nextaccountNo);
						int account = Accounts.getNextAcountNumber();

						String accountName = Validator.getString(keyboard,
								"Enter the name for acct ## " + account
										+ " : ");
						double accountBalance = Validator.getDouble(
								keyboard, "Enter the balance for acct #  "
										+ account + " : ", 0,
								Double.MAX_VALUE);
						acc.addAcount(account, accountName, accountBalance);
						accountsHash.put(
								acc.getAccount(),
								"	" + acc.getAccountName() + " "
										+ acc.getAccountBalance());
						sql = "insert into accounts (accountNumber,accountName,accountType,startingBalence)values('"
								+ acc.getAccount()
								+ "',' "
								+ acc.getAccountName()
								+ "','Checking','"
								+ acc.getAccountBalance() + "')";

						preStatement = conn.prepareStatement(sql);
						result = preStatement.executeQuery();
						Accounts.setNextAcountNumber(++account);
						acc = new Accounts();
						choiceanotheracc = Validator.getBoolean(keyboard,
								"Add another account? (y/n) : ");
					}
					choice = "-1";
				} else
					break;

			}

			System.out.println();
			// transactionsHash = readLines(new File(filename2),
			// transactionsHash);

			while (!tranchoice.equals("-1")) {

				String transaction = Validator
						.getString(
								keyboard,
								"Enter a transaction type (Check (C), Debit card (DC), Deposit(D) or Withdrawal(W)) or -1 to finish : ");

				if (!transaction.equals("-1")) {

					accountNo = Validator.getInt(keyboard,
							"Enter the account # : ");
					if (accountsHash.containsKey(accountNo)) {
						acc.setAccount(accountNo);
						tran.setTransactionType(transaction);

						int amount = Validator.getInt(keyboard,
								"Enter the amount of the " + accountNo + " : ",
								0, Integer.MAX_VALUE);
						tran.setAmount(amount);
						String tranDateStr = Validator.getString(keyboard,
								"Enter the date of the check: (MM/dd/yyyy) ");
						GregorianCalendar gregorianCalendar = new GregorianCalendar(
								Integer.parseInt(tranDateStr.substring(6)),
								Integer.parseInt(tranDateStr.substring(0, 2)) - 1,
								Integer.parseInt(tranDateStr.substring(3, 5)));
						tran.setTranDate(gregorianCalendar);
						;
						transaction = ""
								+ acc.addTran(transaction, accountNo, amount,
										gregorianCalendar);
						transactionsHash.put(accountNo,
								"	" + transaction + " " + amount + "	"
										+ gregorianCalendar.toZonedDateTime());
						String[] Key_value_pair = accountsHash.get(accountNo)
								.split(" ");
						acc.setAccountName(Key_value_pair[Key_value_pair.length - 2]);
						acc.setAccountBalance(Double
								.parseDouble(Key_value_pair[Key_value_pair.length - 1]));
						acc.updateBalance(tran.getTransactionType(), amount);
						accountsHash.replace(accountNo, " " + Key_value_pair[0]
								+ " " + acc.getAccountBalance());
					
						///HERE
						sql = "insert into transactions (account, amount, transaction)values("+
								acc.getAccount()
								+ ","
								+ tran.getAmount()
								+ ","
								+ tran.getTransactionType() + ")";
System.out.println(""+sql);
						preStatement = conn.prepareStatement(sql);
						result = preStatement.executeQuery();

					} else {
						System.out
								.print("The account number does not exist make sure you entered the right number");
					}
				} else
					tranchoice = "-1";

				System.out.println();

				sql = "select * from TRANSACTIONs";
				preStatement = conn.prepareStatement(sql);
				result = preStatement.executeQuery();

				while (result.next()) {
					System.out.printf("%s\t%s\t%s\t%s\n",
							result.getInt("ID"),
							result.getInt("ACCOUNT"),
							result.getInt("AMOUNT"),
							result.getInt("TRANSACTION"),
							result.getDate("DATE1"));

				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		
		*/