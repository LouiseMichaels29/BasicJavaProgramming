import java.util.*;

public class Main {

	//Identify objects for program (so code isn't too clunky) 
	public static MergeSort merge = new MergeSort();
	public static QuickSort quick = new QuickSort();
	public static RadixSort radix = new RadixSort();
	public static InsertionSort insertion = new InsertionSort();
	
	//Assign variables here for program 
	public static int array[] = new int[1000];
	public static Random random = new Random();
	private static Scanner scanner = new Scanner(System.in);

	//Only constructor will appear in main. Simply assign random array and start
	public Main() {
		
		for(int i = 0; i < array.length; i++) {
			
			array[i] = Math.abs(random.nextInt()) % 10000;
		}
		
		start();
	}
	
	public static void printArray(int array[]) {
		
		for(int i = 0; i < array.length; i++) {
			
			System.out.print(array[i] + " ");
		}
		
		System.out.println();
	}

	//Simple method for starting the program. Each sorting algorithm is assigned it's own object 
	public static void start() {
		
		System.out.println("Please select a sorting option.");
		System.out.println("1. Merge Sort");
		System.out.println("2. Insertion Sort");
		System.out.println("3. Quick Sort");
		System.out.println("4. Radix Sort");
		int selectSort = scanner.nextInt();

		switch (selectSort) {

		case 1:
			merge.mergeSort(array);
			break;

		case 2:
			insertion.insertionSort(array);
			break;

		case 3:
			quick.quickSort(array);
			break;

		case 4:
			radix.radixSort(array);
			break;

		}
		
	}

	public static void main(String[] args) {

		new Main();
	}
}
