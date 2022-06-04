import java.util.ArrayList;
import java.util.Comparator;

public class MinHeap implements Comparator<Node>{
	
	//Here we have an array list for storing our heap, as well as an integer for it's size. 
	public ArrayList<Node> heap;
	public int size;
	
	//Small constructor will program, assigns values to variables. 
	public MinHeap(int size) {
		
		this.size = size;
		heap = new ArrayList<Node>();
	}
	
	//The parent, left child, and right child methods simply calculates the resulting index
	private int parent(int position) {
		
		return ((position - 1) / 2);
	}

	private int leftChild(int index) {
		
		return ((2 * index) + 1);
	}

	private int rightChild(int index) {

		return ((2 * index) + 2);
	}

	//Here we insert a new node into the heap. This will produce a min heap.
	public void insert(Node node) {
		
		//Add the new node to the array list and set the new size of the heap. 
		heap.add(node);
		int currentIndex = heap.size() - 1;

		//While we haven't reached the root, continue to swap the indexes of the current and parent nodes until we find the correct position
		while(currentIndex > 0 && compare(heap.get(currentIndex), heap.get(parent(currentIndex))) < 0) {
			
			swap(heap, currentIndex, parent(currentIndex));
			currentIndex = parent(currentIndex);
		}
	}

	public void swap(ArrayList<Node> heap, int index1, int index2) {
		
		Node holdElement = heap.get(index1);
		heap.set(index1, heap.get(index2));
		heap.set(index2, holdElement);
	}

	//This method will reorder the heap when the root value (minimum value) is removed. 
	public void heapify(ArrayList<Node> heap, int index) {
		
		int left = leftChild(index);
		int right = rightChild(index);
		int highestPriority = index;
	
		//Compare the current node with the left child. If it is greater, then swap. 
		if(left <= heap.size() - 1 && compare(heap.get(left), heap.get(index)) < 0) {
			
			highestPriority = left;
		}

		//We need to compare the same node with the right child to determine the highest priority. (Whether that is the index or the left child) 
		if(right <= heap.size() - 1 && compare(heap.get(right), heap.get(highestPriority)) < 0) {
			
			highestPriority = right;
		}
		
		//If we have swapped any values, we will need to continue running our method until we find the correct position! 
		if(highestPriority != index) {

			swap(heap, index, highestPriority);
			heapify(heap, highestPriority);
		}
	}
	
	//Remove min method to remove the minimum node from the heap. 
	public Node removeMin() {
		
		if(heap.size() <= 0) {
			
			return null;
		}

		//Assign a value to the element we need removed. Then, move the last index of our heap to the root and find it's new position. 
		else {
			
			Node highestPriority = heap.get(0);
			heap.set(0, heap.get(heap.size() - 1));
			heap.remove(heap.size() - 1);
			heapify(heap, 0);
			return highestPriority;
		}
	}

	//Option 1 method will simply remove all nodes from our heap and print the resulting data 
	public void option1() {
		
		for(int i = 0; i < size; i++) {
		
			System.out.println(removeMin().toString);
		}
	}
	
	//Option 2 will nodes from our heap, add then to a separate array list, and then print the values in reverse order. (Used for max heap) 
	public void option2() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(int i = 0; i < size; i++) {
			
			list.add(removeMin().toString);
		}
		
		for(int i = (size - 1); i >= 0; i--) {
			
			System.out.println(list.get(i));
		}
	}

	public int compare(Node node1, Node node2) {
		
		return node1.priority - node2.priority;
	}
}
