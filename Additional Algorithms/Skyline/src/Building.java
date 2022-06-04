
public class Building {
	
	//Simple variables for leftX, rightX and height coordinates of building vector. This class is pretty self explanatory and simply stores vector info 
	private int leftX;
	private int height;
	private int rightX;
	
	public Building(int leftX, int rightX, int height) {
		
		this.leftX = leftX;
		this.height = height;
		this.rightX = rightX;
	}

	public int getLeftX() {
		
		return leftX;
	}
	
	public int getRightX() {
		
		return rightX;
	}
	
	public int getHeight() {
		
		return height;
	}
}
