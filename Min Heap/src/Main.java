import java.io.File;
import java.util.Scanner;

public class Main {

	public static String filename = "flights.txt";
	public static int count = 0;
	
	public Main(String[] args) {
		
		user();
	}

	//Read file method. Will read the contents of the file and assign each node it's data. 
	public static void readFile(String filename, int option) {
		
		File file = new File(filename);
		
		try {
			
			//Get line count will get the total number of lines in the file. 
			Scanner lineScanner = new Scanner(file);
			getLineCount(lineScanner);
			lineScanner.close();
			
			//Here we create our heap, given the number of lines in the file. 
			MinHeap heap = new MinHeap(count);
			Scanner sc = new Scanner(file);
			
			//Here we will scan our file once more, and assign each node it's given data. Then, we will insert that node into the heap. 
			while(sc.hasNextLine()) {
				
				Node node = getInfo(option, sc);
				heap.insert(node);
			}
			
			//Get display method simply prints the resulting heap, depending on the option chosen
			getDisplay(heap, option);
			sc.close();
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
		}
	}

	//Get display method. Here we simply print the resulting heap. 
	public static void getDisplay(MinHeap heap, int option) {
		
		if(option == 1) {
		
			System.out.println("\nMin Heap (take-off time) : \n");
			heap.option1();
		}
		
		else {
			
			System.out.println("\nMax Heap (passenger count) : \n");
			heap.option2();
		}
	}
	
	//User method. A small method used to make the program a little simpler.
	public void user() {

		System.out.println("Enter 1 for generating a min heap (take - off time)\n"
				+ "or 2 for max heap (passenger count)");
		Scanner user = new Scanner(System.in);
		
		try {
			
			int option = user.nextInt();
			readFile(filename, option);
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
		user.close();
	}
	
	//Get line method will count the total number of lines in the file 
	public static void getLineCount(Scanner sc) {
		
		while(sc.hasNextLine()) {
			
			sc.nextLine();
			count++;
		}
	}
	
	//Get info method will assign the resulting info to each node. 
	public static Node getInfo(int option, Scanner sc) {
		
		String line = sc.nextLine();
		Node node = new Node();
		
		String[] lineSplit = line.split(" ");
		String[] timeSplit = lineSplit[2].split(":");
		String timeHr = timeSplit[0];
		String timeMin = timeSplit[1];
		String passengers = lineSplit[1];
		String AmOrPm = lineSplit[3];
		
		int hour = Integer.parseInt(timeHr);
		int minutes = Integer.parseInt(timeMin);
		
		//Here we use a simple algorithm to convert the AM and PM symbols. 
		if(AmOrPm.equals("AM")) {
			
			minutes += (hour * 60);
		}
		
		else if(AmOrPm.equals("PM")) {
			
			if(hour == 12) {
				
				minutes += (hour * 60);
			}
			
			else {
				
				minutes += ((hour + 12) * 60);
			}
		}
		
		//Here we set our output display for each node. (Depending on the option chosen). 
		if(option == 1) {
			
			node.priority = minutes;
			node.toString = lineSplit[0] + " " + lineSplit[2]
					+ " " + lineSplit[3] + " " + lineSplit[1];
		}
		
		else {
			
			node.priority = Integer.parseInt(passengers);
			node.toString = lineSplit[0] + " " + passengers;
		}
		
		return node;
	}
	
	public static void main(String[] args) {

		new Main(args);	
	}
}
