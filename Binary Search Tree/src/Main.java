import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	private static String filename;
	private static BinarySearchTree tree = new BinarySearchTree();
	public static Scanner scanner = new Scanner(System.in);
	private static Node root = null;

	public Main() {
		
		user();
	}

	//Read the contents of the file and implement our BST 
	public static void readFile(String filename) {
		
		File file = new File(filename);

		try {
			
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) {
				
				String number = scan.nextLine();
				root = tree.insert(root, Integer.parseInt(number));
			}
	
			scan.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}

	//Simple method to handle the program. Prompt user for input, search tree. 
	public static void user() {
		
		System.out.println("Enter the file you wish to scan: ");
		System.out.println("1. ranNums_10.txt");
		System.out.println("2. ranNums_50.txt");
		System.out.println("3. ranNums_100.txt");
		int fileNumber = Integer.parseInt(scanner.nextLine());
		
		switch(fileNumber) {
		
			case 1: 
				filename = "randNums_10.txt";
				break;
				
			case 2: 
				filename = "randNums_50.txt";
				break; 
				
			case 3: 
				filename = "randNums_100.txt";
				break;
				
			default: 
				System.out.println("Invalid filename!");
		}
		
		readFile(filename);
		options();
	}
	
	public static void options() {
		
		while(true) {
			
			System.out.println("---------------");
			System.out.println("1. Print Tree");
			System.out.println("2. Tree Height");
			System.out.println("3. Tree Width");
			System.out.println("4. In Order Successor");
			System.out.println("5. Search Node");
			int option = Integer.parseInt(scanner.nextLine());

			switch(option) {
			
				case 1:
				tree.printSideways(root, 0);
				break;
				
				case 2:
				System.out.println("Height: " + tree.getHeight(root) + "\n"); 
				break;
				
				case 3:
				System.out.println("Width: " + tree.getMaxWidth(root) + "\n");
				break;
				
				case 4:
				System.out.println("Enter input node: ");
				Node inputNode = tree.getNode(root, Integer.parseInt(scanner.nextLine()));
				System.out.println("In order successor: " + tree.inOrderSuccessor(inputNode).data);
				break;
				
				case 5:
				System.out.println("Enter input node: ");
				int search = Integer.parseInt(scanner.nextLine());
				if(tree.search(root, search)) System.out.println("Node " + search + " found in BST!");
				else System.out.println("Node " + search + " not found in BST.");
				break;
			
				default:
				break;	
			}
		}
	}

	public static void main(String[] args) {
		
		new Main(); 
	}
}


