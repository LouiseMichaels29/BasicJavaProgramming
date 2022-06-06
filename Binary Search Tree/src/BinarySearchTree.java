public class BinarySearchTree {
	
	/* Iterate through left and right side of tree. Add parent to the previous node recursively. */
	public Node insert(Node node, int data) {
		
		if(node == null) 
			return new Node(data);
	
		else if(data < node.data) {
			
			Node leftChild = insert(node.left, data);
			node.left = leftChild;
			leftChild.parent = node;
		}
		
		else if(data > node.data) {
			
			Node rightChild = insert(node.right, data);
			node.right = rightChild;
			rightChild.parent = node;
		}
		
		return node;
	}

	/* In order successor of the input node. The in order successor of a node is the next smallest node in the in order traversal. */
	public Node inOrderSuccessor(Node node) {
		
		if(node == null)
			return null;
		
		/* If there is a right node, return the node with the smallest value from the next right node. */
		if(node.right != null)
			return minNode(node.right);
		
		/* Else, continue to traverse the tree up recursively until we reach the next left node of the parent. */
		else {
			
			Node current = node;
			Node parent = node.parent;
			
			while(parent != null && parent.left != current) {
				
				current = parent;
				parent = parent.parent;
			}
			
			return parent;
		}
	}
	
	//Retrieve the minimum node from the given node in the BST. 
	public Node minNode(Node node) {
		
		Node current = node;
		
		while(current.left != null)
			current = current.left;
		
		return current;
	}
	
	/* Return a node from binary search tree */
	public Node getNode(Node node, int data) {
		
		if(node.data == data) 
			return node;

		else if(data < node.data)
		   return getNode(node.left, data);
		
		else
		   return getNode(node.right, data);
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
		
		int height = (getHeight(node) + 1);
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
