public class BinarySearchTree {
	
	//Iterate through left and then right side of tree 
	public Node insert(Node node, int data) {
		
		if(node == null) 
			return new Node(data);
	
		else if(data < node.data)
			node.left = insert(node.left, data);
		
		else if(data > node.data)
			node.right = insert(node.right, data);
		
		return node;
	}
	
	//Get height of binary tree. (Greatest value from root to leaf node)
	public int getHeight(Node node) {
		
		if(node == null)
			return -1;
		
		int left = getHeight(node.left);
		int right = getHeight(node.right);
		
		return (left > right) ? (left + 1) : (right + 1);
	}
	
	//Find the width of binary tree. At each level of the height, return the greatest number of nodes 
	public int getMaxWidth(Node node) {
		
		int height = height(node);
		int maxWidth = 0;
		int width = 0;
		
		for(int i = 1; i <= height; i++) {
			
			width = getWidth(node, i);
			
			if(width > maxWidth)
				maxWidth = width;
		}
		
		return maxWidth;
	}
	
	//For the left and right node of any node, we want to return 2 for the total width at that level 
	public int getWidth(Node node, int level) {
		
		if(node == null)
			return 0;
		
		if(level == 1)
			return 1;
		
		else if(level > 1) 
			return getWidth(node.left, level - 1) 
					+ getWidth(node.right, level - 1);
		
		return 0;
	}
	
	//Get height for finding width. (Width of root node (one) for height (zero)) 
	public int height(Node node) {
		
		if(node == null)
			return 0;
		
		else {
			
			int left = height(node.left);
			int right = height(node.right);
			return (left > right) ? (left + 1) : (right + 1);
		}
	}
	
	//Search for node in binary tree. Return true if any node along the path matches the input data
	public boolean search(Node node, int data) {
		
		if(node == null)
			return false;
		
		if(node.data == data)
			return true;
		
		return search(node.left, data) || search(node.right, data);
	}
	
	//Print binary tree sideways (can be used for testing) 
	public void printSideways(Node currentNode, int level) {
		
		if(currentNode != null) {
			
			printSideways(currentNode.right, level + 1);
			for(int i = 0; i < level; i++) 
				System.out.print("     ");
			
			System.out.println(currentNode.data);
			printSideways(currentNode.left, level + 1);	
		}
	} 
}
