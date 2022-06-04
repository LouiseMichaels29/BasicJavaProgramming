import java.io.File;
import java.util.*;

public class Main {

	int index = 0;
	public static ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
	public static ArrayList<String> positions = new ArrayList<String>();
	public static int startingArray[][];
	public static int pathArray[][];
	public static String filename = "path.txt";
	
	public Main() {
		
		readFile(filename);
	}
	
	//Read file method simply reads the contents of the file and produces a 2D array 
	public void readFile(String filename) {
		
		File file  = new File(filename);
		
		try {
			
			Scanner scanner = new Scanner(file);
			
			while(scanner.hasNextLine()) {
				
				String line = scanner.nextLine();
				list.add(new ArrayList<Integer>());
				String lineSplit[] = line.split(" ");
				
				for(int i = 0; i < lineSplit.length; i++) {
					
					list.get(index).add(Integer.parseInt(lineSplit[i]));
				}
				
				index++;
			}
			
			startingArray = new int[list.size()][list.get(0).size()];
			pathArray = new int[list.size()][list.get(0).size()];
			
			for(int i = 0; i < list.size(); i++) {
				
				for(int j = 0; j < list.get(i).size(); j++) {
					
					startingArray[i][j] = list.get(i).get(j);
					pathArray[i][j] = list.get(i).get(j);
				}
			}
			
			getMaxPath(pathArray);
			scanner.close();
			
		}catch(Exception e){
		
			e.printStackTrace();
		}
	}
	
	public static void printList() {
		
		for(int i = 0; i < list.size(); i++) {
			
			for(int j = 0; j < list.get(i).size(); j++) {
				
				System.out.print(list.get(i).get(j) + " ");
			}
			
			System.out.println();
		}
	}
	
	//This method uses memoization to find the optimal path. If statements are for left, middle, and right sides of our array. 
	public static int getMaxPath(int pathArray[][]) {
		
		for(int currentRow = 1; currentRow < pathArray.length; currentRow++) {
			
			for(int currentColumn = 0; currentColumn < pathArray[0].length; currentColumn++) {
				
				//Middle elements (not left or right side). We want the maximum value from either the previous left, middle, or right. Simply use two math.abs to find the max. 
				if(currentColumn > 0 && currentColumn < pathArray[0].length - 1) {
					
					pathArray[currentRow][currentColumn] += Math.max(pathArray[currentRow - 1][currentColumn], 
							Math.max(pathArray[currentRow - 1][currentColumn - 1], pathArray[currentRow - 1][currentColumn + 1]));
				}
				
				//Right side. Compare previous left and middle positions to find the max 
				else if(currentColumn > 0) {
					
					pathArray[currentRow][currentColumn] += Math.max(pathArray[currentRow - 1][currentColumn], 
							pathArray[currentRow - 1][currentColumn - 1]);
				}
				
				//We can start here. Since the first row of our static array will not change, we can start by finding the maximum path on the second row. (Left and middle)
				else if(currentColumn < pathArray[0].length - 1) {
					
					pathArray[currentRow][currentColumn] += Math.max(pathArray[currentRow - 1][currentColumn], 
											pathArray[currentRow - 1][currentColumn + 1]);
				}
			}
		}
		
		//Here we simply get the max value from the last row of our array 
		int maxPath = pathArray[pathArray.length - 1][0];
		for(int i = 1; i < pathArray[0].length; i++) {
			
			maxPath = Math.max(pathArray[pathArray.length - 1][i], maxPath);
		}
		
		int currentMax = 0; 
		int positionsIndex = 0;
		
		//Check the last position (Max value) 
		int index = 0;
		for(int i = pathArray.length - 1; i >= 0; i--) {
			
			if(currentMax < pathArray[pathArray.length - 1][i]) {
				
				currentMax = pathArray[pathArray.length - 1][i];
				index = i;
			}
		}
		
		addPosition(positionsIndex, pathArray.length - 1, index);
		
		currentMax = 0;
		positionsIndex++;
		
		//Fairly simple algorithm to get positions and values. 
		for(int i = pathArray.length - 1; i >= 1; i--) {
			
			for(int j = pathArray[0].length - 1; j >= 0; j--) { 
				
				if(currentMax < pathArray[i][j]) {
					
					//Left diagonal 
					if(j > 0) {
						
						if((pathArray[i][j] == (startingArray[i][j] + pathArray[i - 1][j - 1]))) {
							
							currentMax = pathArray[i - 1][j - 1];
							addPosition(positionsIndex, i - 1, j - 1);
						}
					}
					
					//Right diagonal 
					if(j < (pathArray.length - 1)) {
						
						if(pathArray[i][j] == (startingArray[i][j] + pathArray[i - 1][j + 1])) {
							
							currentMax = pathArray[i - 1][j + 1];
							addPosition(positionsIndex, i - 1, j + 1);
						}
					}
					
					//Middle 
					if((j > 0) && (j < pathArray.length - 1)){
						
						if(pathArray[i][j] == (startingArray[i][j] + pathArray[i - 1][j])) {
							
							currentMax = pathArray[i - 1][j];
							addPosition(positionsIndex, i - 1, j);
						}
					}
				}
			}
			
			currentMax = 0;
			positionsIndex++;
		} 

		//Simply print the positions, value, and max value 
		for(int i = positions.size() - 1; i >= 0; i--) {
			
			System.out.println(positions.get(i));
		}
		
		System.out.println(maxPath + " truffles");
		
		return maxPath;
	}
	
	//Add or set position of the new maximum to our positions array 
	public static void addPosition(int positionsIndex, int i, int j) {
		
		if(positions.size() <= positionsIndex) {
			
			positions.add("[" + (i + 1) + ", " + (j + 1) + "] - "
							+ startingArray[i][j]);
		}
		
		else {
			
			positions.set(positionsIndex, "[" + (i + 1) + ", " + (j + 1) + 
							"] - " + startingArray[i][j]);
		}
	}
	
	public static void main(String[] args) {
		
		new Main();
	}
}
