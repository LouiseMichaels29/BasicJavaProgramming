import java.io.File;
import java.util.Scanner;

public class Main {

	//Variables including file name and scanner for user input. 
	public static String filename = "users.txt";
	public static Scanner scanner = new Scanner(System.in);
	
	public static Graph graph = new Graph();
	
	public Main(String[] args) {
		
		readFile(filename);
	}
	
	//Read file method to read the contents of the file and set data. 
	public static void readFile(String filename) {
		
		File file = new File(filename);
		
		try {
			
			Scanner scanner = new Scanner(file);
			setData(scanner);
			scanner.close();
			
			user();
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
		}
	}
	
	//This methods adds a new edge for each line in the file. Setting the source, target, and weight variables of each edge. This new edge is then added 
	//to our graph object. 
	public static void setData(Scanner sc) {
		
		while(sc.hasNextLine()) {
			
			String line = sc.nextLine();
			String[] split = line.split("\\s+");
			String source = split[0];
			String target = split[1];
			int weight = Integer.parseInt(split[2]);
			
			graph.addEdge(source, target, weight);
		}
	}
	
	//User method to create simple interface of program. User can select either dijkstra's algorithm, or a separate algorithm that can be used to 
	//calculate all nodes within' a given distance (less than, greater than, or equal to), of a specific source node. These options are then executed in the 
	//graph class. 
	public static void user() {

		System.out.println("Enter A for Dijkstra's algorithm or B for all nodes "
									+ " within' a given distance:");
		char option = scanner.nextLine().charAt(0);
		option = Character.toLowerCase(option);
		
		if(option == 'a') {
			
			System.out.println("Enter source node: "); String source = scanner.nextLine();
			System.out.println("Enter target node: "); String target = scanner.nextLine();
			graph.getOptionA(source, target);
		}
		
		else if(option == 'b') {
			
			System.out.println("Enter source node: "); String source = scanner.nextLine();
			System.out.println("Enter lt# for less than       Example: lt9 (all nodes less than nine)");
			System.out.println("Enter gt# for greater than    Example: gt5 (all nodes greater than five)");
			System.out.println("Enter eq# for equal to        Example: eq8 (all nodes equal to eight)");
			String var = scanner.nextLine();
			graph.getOptionB(source, var);	
		}
		
		else {
			
			System.out.println("Invalid option. [Exiting...]");
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		
		new Main(args);
	}
}