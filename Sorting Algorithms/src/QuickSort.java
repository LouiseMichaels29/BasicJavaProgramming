
public class QuickSort {
	
	int count = 0;

	public void quickSort(int[] array) {
		
		quickSortHelper(array, 0, (array.length - 1));
		numberOfComparisons(count);
	}
	
	//Additional method to call method recursively with correct parameters 
	public void quickSortHelper(int[] array, int leftIndex, int rightIndex) {
		
		//Grab the partition element (middle element) 
		int partitionIndex = partition(array, leftIndex, rightIndex);
		
		count++;
		if(leftIndex < (partitionIndex - 1)) {
			
			quickSortHelper(array, leftIndex, partitionIndex - 1);
		}
		
		count++;
		if(partitionIndex < rightIndex) {
			
			quickSortHelper(array, partitionIndex, rightIndex);
		}
	}
	
	public int partition(int[] array, int leftIndex, int rightIndex) {
		
		//Pivot will be middle element 
		int pivot = array[((leftIndex + rightIndex) / 2)];
		
		//Continue to iterate until we find the correct values. (Greater than pivot on left side, less than on right side) 
		count++;
		while(leftIndex <= rightIndex) {
			count++;
			while(array[leftIndex] < pivot) leftIndex++;
			while(array[rightIndex] > pivot) rightIndex--;
			
			//Once we find those values, we will swap
			count++;
			if(leftIndex <= rightIndex) {
				count++;
				swap(array, leftIndex, rightIndex);
				leftIndex++;
				rightIndex--;
			}	
		}
		
		return leftIndex;
	}
	
	public void swap(int array[], int leftIndex, int rightIndex) {
		
		int temp = array[leftIndex];
		array[leftIndex] = array[rightIndex];
		array[rightIndex] = temp;
	}	
	
	public static void numberOfComparisons(int n) {

		System.out.println("Number of Comparisons " + n);
	}
}
