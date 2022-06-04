import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static int minInterval;
	public static ArrayList<Integer> list = new ArrayList<Integer>();
	public static String filename = "path.txt";
	
	public Main() { 
		
		readFile(filename);
	}
	
	//Read file method. We simply read the contents of our file 
	public static void readFile(String filename) {
			
		File file = new File(filename);
		
		try {
			
			//Scan the min interval of the file and then the sub array
			Scanner scanner = new Scanner(file);
			String minScan = scanner.nextLine(); 
			minInterval = Integer.parseInt(minScan);
			
			String lineScan = scanner.nextLine();
			String[] lineSplit = lineScan.split("\t");
			for(int i = 0; i < lineSplit.length; i++) {
				
				list.add(Integer.parseInt(lineSplit[i]));
			}
			
			//Sum method calculates maximum sub array
			scanner.close();
			sum(list, list.size() - 1, minInterval);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void sum(ArrayList<Integer> list, int index, int minInterval) {
		
		//Values and positions simply tracks the values and positions we use for our sub array 
		int total = 0;
		ArrayList<Integer> values = new ArrayList<Integer>();
		ArrayList<Integer> positions = new ArrayList<Integer>();
		
		//Running summation to track the current most highest value of our subarray
		int sum[] = new int[list.size()];
		
		for(int j = 0; j < list.size(); j++) {
			
			//If we have only one value in our file we simply store that data 
			if(j == 0) {
				
				sum[0] = list.get(0);
			}
			
			//Else, if we are in the interval range, we will keep track of the largest first value
			else if(j < minInterval && j > 0) { 
				
				sum[j] = Math.max(sum[j - 1], list.get(j)); 
			}
			
			//Basically, this will use a running summation, adding our current highest value and appending that to the sum array
			else { 
				
				sum[j] = Math.max(sum[j - minInterval] + list.get(j), sum[j - 1]);
			}
		}

		//Although this looks slightly confusing it is very simple. We simply start at the end of our array and notice the first 
		//change in our running summation. Simply add the current position and value before the change! 
		for(int i = (sum.length - 2); i >= 0; i--) {

			if(sum[i] < sum[i + 1]) {
				
				positions.add(i + 1);
				values.add(list.get(i + 1)); 
			}
	
			if(!((i + 1) <= minInterval) && (sum[i] < sum[i + 1])) {
				
				i -= (minInterval - 1);
			}
			
			else if((i + 1) < minInterval && (sum[i] < sum[i + 1])) {
				
				break;
			}
			
			else if(i == 0) {
				
				positions.add(i + 1);
				values.add(list.get(i + 1));
			}
		}
		
		//Total variable to store maximum value of our non contiguous sub array. 
		total = sum[list.size() - 1];
		print(positions, values, total);
	}
	
	public static void print(ArrayList<Integer> positions, ArrayList<Integer> values, int total) {
		
		System.out.print("Positions: \t");
		for(int i = (positions.size() - 1); i >= 0; i--) {
			
			System.out.print(positions.get(i) + " \t");
		}
		System.out.println();
		
		System.out.print("Values: \t");
		for(int i = (values.size() - 1); i  >= 0; i--) {
			
			System.out.print(values.get(i) + " \t");
		}
		System.out.println();
		
		System.out.print("Total: \t\t" + total);
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		new Main();
	}
}
