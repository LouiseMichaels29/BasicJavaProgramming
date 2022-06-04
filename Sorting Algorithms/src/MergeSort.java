
public class MergeSort {
	
	public int count = 0;
	public boolean sorted = false;

	public void mergeSort(int[] array) {

		int length = array.length;
		int subArray[] = new int[length];
		mergeSortHelper(array, subArray, 0, length - 1);

		numberOfComparisons(count);
	}
	
	//Helper method to be used with correct parameters 
	public void mergeSortHelper(int array[], int subArray[], int lowerIndex, int higherIndex) {

		//Continue to partition array recursively, combine elements as needed 
		count++;
		if (lowerIndex < higherIndex) {

			int middle = (lowerIndex + ((higherIndex - lowerIndex) / 2));
			mergeSortHelper(array, subArray, lowerIndex, middle);
			mergeSortHelper(array, subArray, middle + 1, higherIndex);
			mergeArrays(array, subArray, lowerIndex, middle, higherIndex);
		}
	}
	
	//Merge all partitioned arrays in the correct order 
	public void mergeArrays(int array[], int subArray[], int lowerIndex, int middle, int higherIndex) {

		//Sub array will store partitioned elements of the current recursive call 
		count++;
		for (int i = lowerIndex; i <= higherIndex; i++) {

			count++;
			subArray[i] = array[i];
		}
		
		//We will simply track the middle position and the current index at which we are combining elements 
		int middlePlusOne = (middle + 1);
		int currentIndex = lowerIndex;

		//Iterate through our sub array. If we find a value at our lower index less than our middle elements, then the elements will clearly need to be 
		//swapped. Otherwise, we will continue to add elements to our array at the correct position. 
		count++;
		while (lowerIndex <= middle && middlePlusOne <= higherIndex) {
			
			count++; count++;
			if (subArray[lowerIndex] <= subArray[middlePlusOne]) {

				array[currentIndex] = subArray[lowerIndex];
				lowerIndex++;
			}

			else {

				array[currentIndex] = subArray[middlePlusOne];
				middlePlusOne++;
			}

			currentIndex++;
		}

		count++;
		
		//If we have not reached the value of our middle index, then we can continue to add elements to our array as needed (elements were in correct position) 
		while (lowerIndex <= middle) {

			count++;
			array[currentIndex] = subArray[lowerIndex];
			currentIndex++;
			lowerIndex++;
		}
	}

	public static void numberOfComparisons(int n) {

		System.out.println("Number of Comparisons " + n);
	}
	
	
}
