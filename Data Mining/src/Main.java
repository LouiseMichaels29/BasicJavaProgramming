import java.io.*;
import java.sql.*;
import java.util.*;

public class Main {
	
	public static Connection connection;
	public static String inputFile = "readerparams.txt";
	public static StockHelper stockHelper = new StockHelper();
	public static final String prompt = "Enter ticker symbol [start/end dates]:";
	
	public Main(String[] args) throws Exception {
		
		start(args);
	}

	// Method containing program  
	public static void start(String[] args) throws Exception{	
			
		if(args.length >= 1) {inputFile = args[0]; }
		Properties connectionProperties = new Properties();
		connectionProperties.load(new FileInputStream(inputFile));
		
		// Connect to database 
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			String dburl = connectionProperties.getProperty("dburl");
	        	String username = connectionProperties.getProperty("user");
			connection = DriverManager.getConnection(dburl, connectionProperties);
			System.out.printf("Database connection %s %s established.%n", dburl, username);
			
			// Prompt user for input 
			Scanner scanner = new Scanner(System.in);
			System.out.println(prompt);
			String input = scanner.nextLine().trim();
			
			// Continue running until no data is entered 
			while(input.length() > 0) {
				
				String[] lineSplit = input.split("\\s+");
				String tickerSymbol = lineSplit[0];
				String startDate = null, endDate = null;
				
				// Return true if ticker exists in table 
				if(stockHelper.getName(tickerSymbol, connection)) {
					
					// Get start and end dates 
					if(lineSplit.length >= 3) {
						
						startDate = lineSplit[1];
						endDate = lineSplit[2];
					}
				
					// Returns a queue for all date entries with the same tickerSymbol 
					Deque<StockData> stockData = stockHelper.getStockData(tickerSymbol, startDate, endDate, connection);
					System.out.println();
					System.out.println("Executing investment strategy");
					stockHelper.doStrategy(tickerSymbol, stockData);
				}
				
				else {
					
					System.out.println(tickerSymbol + " not found in database");
				}
				
				System.out.println();
				System.out.println(prompt);
				input = scanner.nextLine().trim();
			}
			
			
			scanner.close();
			connection.close();
			
			System.out.println("Database connection closed.");
		}
	
		catch(SQLException ex) {
			
			 System.out.printf("SQLException: %s%nSQLState: %s%nVendorError: %s%n",
                     ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		new Main(args);
	}
}



