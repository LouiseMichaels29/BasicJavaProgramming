
public class RadixSort {
	
	int count = 0;
	
	//Get min and max methods for radix sort 
	public int getMin(int array[]) {
		
		 int min = array[0];
		 
		 count++;
		 for(int i = 1; i < array.length; i++) {
			 
			 count++; count++;
			 if(array[i] < min) {
				 
				 min = array[i];
			 }
		 }
		 
		 return min;
	}
	
	public int getMax(int array[]) {
		
		int max = array[0];

		 count++;
		 for(int i = 1; i < array.length; i++) {
			 
			 count++; count++;
			 if(array[i] > max) {
				 
				 max = array[i];
			 }
		 }
		 
		 return max;
	}
	
	public void radixSort(int array[]) {
		
		int minValue = getMin(array);
		int maxValue = getMax(array);
		int exp = 1;
		
		//While min and max values still contain a value digit (i.e. not zero) 
		count++;
		while((maxValue - minValue) / exp >= 1) {
			count++;
			radixSort(array, exp, minValue);
			exp *= 10;
		}
		
		numberOfComparisons(count);
	}
	
	//Helper method for radix sort. (Not needed, but preferred) 
	public void radixSort(int array[], int exp, int minValue) {
		
		//Create our bucket array
		int currentIndex = 0;
		int bucketArray[] = new int[10];
		int outputArray[] = new int[array.length];
		
		//Start with the lease significant digit
		count++;
		for(int i = 0; i < array.length; i++) {
			count++;
			currentIndex = (((array[i] - minValue) / exp) % 10);
			bucketArray[currentIndex]++;
		}
		
		//Create a running summation of our bucket array 
		count++;
		for(int i = 1; i < 10; i++) {
			count++;
			bucketArray[i] += bucketArray[i - 1];
		}
		
		//Subtract one for each value added to our output array 
		count++;
		for(int i = (array.length - 1); i >= 0; i--) {
			count++;
			currentIndex = (((array[i] - minValue) / exp) % 10);
			outputArray[--bucketArray[currentIndex]] = array[i];
		}
		
		//Copy correct elements into our array 
		count++;
		for(int i = 0; i < array.length; i++) {
			count++;
			array[i] = outputArray[i];
		}	
	}	
	
	public static void numberOfComparisons(int n) {

		System.out.println("Number of Comparisons " + n);
	}
}
