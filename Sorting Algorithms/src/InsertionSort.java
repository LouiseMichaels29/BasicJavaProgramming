
public class InsertionSort {
	
	public int count = 0;
	public boolean sorted = false;
	
	public void insertionSort(int[] array) {

		int arrayLength = array.length;

		//Insertion sort will work by iterating through the entire array...
		count++;
		for (int i = 1; i < arrayLength; i++) {

			count++;
			int key = array[i];
			int index = (i - 1);

			//When we find an element that needs to be swapped, shift entire array 
			count++;
			while ((index > -1) && (array[index] > key)) {

				array[index + 1] = array[index];
				index--;
				count++;
			}

			//Insert the element into the array at the correct position (key) 
			array[index + 1] = key;
		}

		numberOfComparisons(count);
		count = 0;
	}

	public static void numberOfComparisons(int n) {

		System.out.println("Number of Comparisons: " + n);
	}

}
