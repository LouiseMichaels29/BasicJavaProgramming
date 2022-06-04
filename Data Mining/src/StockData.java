import java.sql.ResultSet;

public class StockData {
	
	private String date;
	private double openPrice;
	private double highPrice;
	private double lowPrice;
	private double closePrice;
	private double divisor;
	
	public StockData() {

		date = "";
		openPrice = 0;
		highPrice = 0;
		lowPrice = 0;
		closePrice = 0;
		divisor = 1;
	}
	
	// Set all data for the current ticker and date 
	public void setData(ResultSet result) throws Exception{
		
		date = result.getString("TransDate");
		openPrice = result.getDouble("OpenPrice");
		highPrice = result.getDouble("HighPrice");
		lowPrice = result.getDouble("LowPrice");
		closePrice = result.getDouble("ClosePrice");
	}
	
	public String getDate() {
		
		return date;
	}
	
	public void setOpenPrice(double openPrice) {
		
		this.openPrice = openPrice;
	}
	
	public double getOpenPrice() {
		
		return openPrice;
	}
	
	public void setHighPrice(double highPrice) {
		
		this.highPrice = highPrice;
	}
	
	public double getHighPrice() {
		
		return highPrice;
	}
	
	public void setLowPrice(double lowPrice) {
		
		this.lowPrice = lowPrice;
	}
	
	public double getLowPrice() {
		
		return lowPrice;
	}
	
	public void setClosePrice(double closePrice) {
		
		this.closePrice = closePrice;
	}

	public double getClosePrice() {
		
		return closePrice;
	}
	
	public void setDivisor(double divisor) {
		
		this.divisor = divisor;
	}

	public double getDivisor() {
		
		return divisor;
	}
	
	//Set new data for investment strategy 
	public void setNewData(StockData newLine, double divisor) {
		
		newLine.setClosePrice(newLine.getClosePrice() / divisor);
		newLine.setHighPrice(newLine.getHighPrice() / divisor);
		newLine.setLowPrice(newLine.getLowPrice() / divisor);
		newLine.setOpenPrice(newLine.getOpenPrice() / divisor);
	}
	
}
