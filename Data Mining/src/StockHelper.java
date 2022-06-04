import java.sql.*;
import java.util.*;
public class StockHelper {
	
	public int divisor = 1;
	public int numberOfSplits = 0;
	public static Queue<Double> movingAverage = new LinkedList<Double>();
	
	public double previousClosePrice = 0;
	public double netCash = 0;
	
	public int numberOfTransactions = 0;
	public int shares = 0;
	
	
	public Deque<StockData> getStockData(String ticker, String start, String end, Connection connection) {
		
		Deque<StockData> result = new ArrayDeque<>();
		
		PreparedStatement statement;
		ResultSet resultSet;
		
		//Run SQL commands. Retrieve data either by a range of dates or all dates. 
		try {
			
			if(checkDates(start, end)) {
				
				statement = connection.prepareStatement("SELECT * FROM pricevolume WHERE Ticker = '" + ticker + "'"
						+ " AND TransDate BETWEEN '" + start + "' AND '" + end + "' ORDER BY TransDate DESC");
				resultSet = statement.executeQuery();
			} 
			
			else {
				
				statement = connection.prepareStatement("SELECT * FROM pricevolume WHERE Ticker = '" + ticker + "'"
						+ " ORDER BY TransDate DESC");
				resultSet = statement.executeQuery();
			}
			
			double previousOpenPrice = 0;
			
			// For all entries in table that match input 
			while(resultSet.next()) {
				
				// Create object for each line in the table (for every date in the table with the same ticker) 
				StockData newLine = new StockData();

				
				if(previousOpenPrice == 0) {
					
					newLine.setOpenPrice(resultSet.getDouble("OpenPrice")); 
				}
				
				// Set data and get stock splits 
				newLine.setData(resultSet);
				getSplits(newLine, result, previousOpenPrice);
				previousOpenPrice = resultSet.getDouble("OpenPrice");
			}
			
			System.out.println(numberOfSplits + " splits in " + result.size() + " trading days");
		}
		
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
	      return result;
	}	
	
	//Get splits for stock data. Keep track of divisor too. 
	public Deque<StockData> getSplits(StockData newLine, Deque<StockData> result, double previousOpenPrice) {
		
		double getSplit = ((newLine.getClosePrice()) / previousOpenPrice);
		
		if(Math.abs(getSplit - 2.0) < 0.20) {
			
			System.out.println("2:1 split on " + newLine.getDate() + " " + newLine.getClosePrice() + " --> " + previousOpenPrice);
			divisor *= 2;
			numberOfSplits++;
		} 
		
		else if(Math.abs(getSplit - 3.0) < 0.30) {

			System.out.println("3:1 split on " + newLine.getDate() + " " + newLine.getClosePrice() + " --> " + previousOpenPrice);
			divisor *= 3;
			numberOfSplits++;
		}
		
		else if(Math.abs(getSplit - 1.5) < 0.15) {

			System.out.println("3:2 split on " + newLine.getDate() + " " + newLine.getClosePrice() + " --> " + previousOpenPrice);
			divisor *= 1.5;
			numberOfSplits++;
		}

		newLine.setNewData(newLine, divisor);
		result.addFirst(newLine);
		return result;
	}
	
	//Execute investment strategy. Should follow the algorithm correctly here too. 
	public void doStrategy(String tickerSymbol, Deque<StockData> stockData) {
		
		// Average previous closing price for the first fifty days 
		double average = getFirstFiftyDayAverage(stockData);
		previousClosePrice = stockData.peek().getClosePrice();
		
		// For each entry in our queue 
		while(!(stockData.isEmpty())) { 
			
			// Get the new moving average for that day 
			StockData data = stockData.poll();
			average = getNewAverage(stockData, average);
	
			// If the closing price is less than the average and it's considered optimal, then buy stock. 
			if((data.getClosePrice() < average) && 
					((data.getClosePrice() / data.getOpenPrice()) < 0.97000001)) {
				
				setBuy(stockData);
			}
			
			// Otherwise, if we have shares and the opening price is more than the closing price and it's considered optimal, then sell stock. 
			else if((shares >= 100) && (data.getOpenPrice() > average) 
					&& ((data.getOpenPrice() / previousClosePrice) > 1.00999999)) {
				
				setSale(data); 
			}
			
			// Get the new previous closing price, add that value to the new average. 
			previousClosePrice = data.getClosePrice();
			movingAverage.add(previousClosePrice);
		}
		
		System.out.println("Transactions Executed: " + numberOfTransactions);
		System.out.println("Net Cash: " + netCash);
		resetData();
	} 
	
	// Get the new moving average (the new average for the closing price from the previous average) 
	public static double getNewAverage(Deque<StockData> data, double average) {
		
		double sum = 0;
		
		if(movingAverage.size() <= 50) {
			
			int size = movingAverage.size();
			
			for(int k = 0; k < size; k++) {
				
				double num = movingAverage.poll();
				movingAverage.add(num);
				sum += num;
			}
			
			average = sum / 50;
		}
		
		movingAverage.poll();
		return average;
	}
	
	//Get the average closing price for the first fifty days 
	public static double getFirstFiftyDayAverage(Deque<StockData> data) {
		
		double sum = 0;
		double average = 0;

		for(int i = 0; i < 50; i++) {
			
			if(!(data.isEmpty())) {
				
				double value = data.pop().getClosePrice();
				movingAverage.add(value);
				sum += value;
			}
		}
		
		average = sum / 50;
		return average;
	}
	
	// Print the name of the company and return true. Otherwise, return false (no company found)
	public boolean getName(String ticker, Connection connection) throws SQLException {
		  
		PreparedStatement myStatement = connection.prepareStatement("select ticker, name from company");
		ResultSet myResult = myStatement.executeQuery();
	
		while(myResult.next()) {
		
			if(myResult.getString("Ticker").equals(ticker)) {
				
				System.out.println(myResult.getString("Name"));
				return true;
			}
		}
		
		return false;
	}
	
	//Check for null dates 
	public static boolean checkDates(String start, String end) {

		if(!(start == null) && (!(end == null))) {
			
			return true;
		}
		
		return false;
	}
	
	//Simple buy and sell methods
	public void setBuy(Deque<StockData> stockData) {
		
		numberOfTransactions++;
		shares += 100;
		netCash -= ((stockData.peek().getOpenPrice() * 100) + 8);
	}
	
	public void setSale(StockData data) {
		
		numberOfTransactions++;
		shares -= 100;
		netCash += (((data.getOpenPrice() + data.getClosePrice()) / 2) * 100) - 8;
	}
	
	//Reset all the data 
	public void resetData() {
		
		numberOfTransactions = 0;
		numberOfSplits = 0;
		netCash = 0;
		divisor = 1;
		shares = 0;
		movingAverage.clear();
	}
}
