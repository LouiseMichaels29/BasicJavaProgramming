import java.awt.Point;
import java.io.*;
import java.util.*;

public class Skyline {
	
	public static ArrayList<Building> startArray = new ArrayList<Building>();
	public static ArrayList<Point> endArray = new ArrayList<Point>(); 
	public static String filename = "buildings.txt";

	//Constructor. We will read file and then print contents of array output 
	public Skyline(String filename) { 
		
		readFile(filename);
		endArray = getSkyLines(0, startArray.size() - 1, startArray);
	
		for(int i = 1; i < endArray.size(); i++) {
			
			if(endArray.get(i).getY() == endArray.get(i - 1).getY()) {
				
				endArray.remove(i);
			}
		}

		for(int i = 0; i < endArray.size(); i++) {
			
			System.out.print("<" + (int) endArray.get(i).getX() + " " + (int) endArray.get(i).getY() + ">");
		}
		
		System.out.println();
	}
	
	//Read file method. This method simply reads the txt file and stores the vectors into the starting array of type building objects 
	public static void readFile(String filename) {
		
		//File input
		File file = new File(filename);
		
		try {
			
			Scanner scanner = new Scanner(file);
			
			//While scanner has next line, split line and add to start array
			while(scanner.hasNext()) {
				
				String lineScan = scanner.nextLine(); 
				String[] lineSplit = lineScan.split("<|>", 0);
				
				for(int i = 1; i < lineSplit.length; i += 2) {
					
					//Split line and store building object. Add to starting array 
					String[] buildingSplit = lineSplit[i].split(" "); 
					
					Building building = 
							new Building(Integer.parseInt(buildingSplit[0]), 
									Integer.parseInt(buildingSplit[1]), 
									Integer.parseInt(buildingSplit[2]));
					startArray.add(building);
				}
			}
			
			scanner.close();
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	//Get skyline method. Will continuously divide our building vectors until we have points for each and every x and y value. Then, we will continusouly 
	//merge our skylines recursively to produce our result. 
	public static ArrayList<Point> getSkyLines(int low, int high, ArrayList<Building> buildings) {

		//Point list array to keep track of current vector points 
		ArrayList<Point> pointList = new ArrayList<Point>();
		
		//If the low index is greater than the highest index, we have reached the end of our array 
		if(low > high) {
			
			return pointList;
		}
		
		//If the low index equals the high index, we will create two new points. 
		else if(low == high) {

			//One point will consist of the first x and y coordinate. While the other consists of the last x coordinate and the value of zero for the y coordinate. (Since 
			//we know all buildings will not change in height). 
			pointList.add(new Point(buildings.get(low).getLeftX(), buildings.get(low).getHeight())); 
			pointList.add(new Point(buildings.get(low).getRightX(), 0));
			return pointList;
		}
		
		//Otherwise, we will split our array by the middle element and merge skylines. 
		else {
			
			//Find middle index. Then, create two new arrays and continue to recursively call this function until we only have two building vectors. Then, merge 
			//vectors to produce skylines. 
			int middle = (low + high) / 2;
			ArrayList<Point> leftDivide = getSkyLines(low, middle, buildings);
			ArrayList<Point> rightDivide = getSkyLines(middle + 1, high, buildings);
			return merge(leftDivide, rightDivide);
		}
	}
	
	//Merge method. Will merge two building vectors together while checking for all overlapping buildings as well 
	public static ArrayList<Point> merge(ArrayList<Point> leftDivide, ArrayList<Point> rightDivide){
		
		//Create new array list to store ending points. 
		ArrayList<Point> mergeSkyLines = new ArrayList<Point>();
		int firstPoint = 0, secondPoint = 0;
		
		//While neither the left or right building vector array is empty 
		while(!leftDivide.isEmpty() && !rightDivide.isEmpty()) { 
		
			//Assign two new points to check 
			Point point1 = leftDivide.get(0);
			Point point2 = rightDivide.get(0); 

			//If the x values are the same, then we will include a point with the highest y value 
			if(point1.getX() == point2.getX()) {
				
				mergeSkyLines.add(new Point((int) point1.getX(), 
										(int) Math.max(point1.getY(), point2.getY())));
				firstPoint = (int) point1.getY(); 
				secondPoint = (int) point2.getY();
				leftDivide.remove(0);
				rightDivide.remove(0);
			}
			
			//Otherwise, we if the first x coordinate is less. We will include that x point, and check for highest y value 
			else if(point1.getX() < point2.getX()) { 
				
				mergeSkyLines.add(new Point((int) point1.getX(), (int) Math.max(point1.getY(), secondPoint)));
				firstPoint = (int) point1.getY();
				leftDivide.remove(0);
			}
			
			//Otherwise, we will use second x coordinate and check for current highest y value 
			else {
				
				mergeSkyLines.add(new Point((int) point2.getX(), (int) Math.max(point2.getY(), firstPoint)));
				secondPoint = (int) point2.getY(); 
				rightDivide.remove(0);
			}
		}
		
		//Once we have reached the end of our arrays, we will simply append whatever's left to our output array 
		if(leftDivide.isEmpty()) {
			
			mergeSkyLines.addAll(rightDivide);
		}
		
		else if(rightDivide.isEmpty()) {
			
			mergeSkyLines.addAll(leftDivide);
		}
		
		return mergeSkyLines;
	}
	
	public static void main(String[] args) {
		
		new Skyline(filename);
	}
}
