import java.util.*;

public class Graph {
	
	//Here we use a hash map for storing our graph. 
	private HashMap<String, Vertex> graph;
	
	public Graph() {
		
		graph = new HashMap<String, Vertex>();
	}
	
	//Add edge method will add a new edge to our graph for each line in the file. We first check if the source and target vertex are already in our graph. 
	//If so, we will add a new edge to our vertices. Otherwise, we will create a new source or target vertex (or both) for our graph, and then add that 
	//edge to the new vertices. 
	public void addEdge(String source, String target, int weight) {
		
		Vertex sourceVertex = getVertex(source);
		Vertex targetVertex = getVertex(target);
		
		sourceVertex.addNeighbour(new Edge(sourceVertex, targetVertex, weight));
	}
	
	//This method will check if the current vertex is found in our graph. 
	private Vertex getVertex(String currentVertexName) {
		
		//We first check if our string value is found in our hash map. If so, we return the vertex at the end of the method. 
		Vertex currentVertex = graph.get(currentVertexName);
		
		//Otherwise, if we have a null value, we will create a new vertex and add it to our graph. 
		if(currentVertex == null) {
			
			currentVertex = new Vertex(currentVertexName);
			graph.put(currentVertexName, currentVertex);
		}
		
		return currentVertex;
	}
	
	//This method will compute Dijkstra's algorithm to find the shortest path from the source vertex to each node in the graph. 
	public void getShortestPathToEachNode(Vertex sourceVertex) {
		
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		
		//Set the distance for the source vertex to be zero, add to queue. 
		sourceVertex.setDistance(0);
		vertexQueue.add(sourceVertex);
		sourceVertex.setVisited(true);
		
		while(!vertexQueue.isEmpty()) {
			
			//Here we will pull the next vertex from the queue
			Vertex currentVertex = vertexQueue.poll();
			
			//For every edge of that vertex, we will get the target, and store the new distance if it is less than the previous distance. 
			for(Edge edge : currentVertex.getEdges()) {
				
				Vertex nextVertex = edge.getTarget();

				if(!(nextVertex.isVisited())) {
					
					int newDistance = currentVertex.getDistance() + edge.getWeight();
					if(newDistance < nextVertex.getDistance()) {
						
						vertexQueue.remove(nextVertex);
						nextVertex.setDistance(newDistance);
						nextVertex.setPreviousVertex(currentVertex);
						vertexQueue.add(nextVertex);
					}
				}
			}
			
			currentVertex.setVisited(true);
		}
	}

	// This method finds all nodes within' a given distance from the source 
	public void allNodesWithinDistance(String source, String first, int number){
		
		ArrayList<Vertex> vertexArrayList = new ArrayList<Vertex>();
		String option = "";
	
		if(first.equals("lt")) option = source + " less than " + number + " : ";
		else if(first.equals("gt")) option = source + " greater than " + number + " : ";
		else if(first.equals("eq")) option = source + " equal to " + number + " : ";
		
		for(String name : graph.keySet()) {

			Vertex targetVertex = graph.get(name);
			if(first.equals("lt")) 
				if((targetVertex.getDistance() < number) && (targetVertex.getDistance() > 0))
					vertexArrayList.add(targetVertex);

			else if(first.equals("gt")) 
				if(targetVertex.getDistance() > number) 
					vertexArrayList.add(targetVertex);
			
			else
				if(targetVertex.getDistance() == number) 
					vertexArrayList.add(targetVertex);
		}

		if(vertexArrayList.isEmpty()) 
			System.out.println(option + "none");

		else {
			System.out.print(option);
			for(int i = 0; i < vertexArrayList.size(); i++) 
				System.out.print(vertexArrayList.get(i).getName() + " ");
			
			System.out.println();
		}
	}
	
	/* Depth first search. Start at the source vertex, iterate through the first edge of all vertexes who have not yet been visited. */
	public void depthFirstSearch(String source) {
		
		Vertex getSource = graph.get(source);
		getSource.setVisited(true);
		System.out.print(getSource.getName() + " ----> ");
		
		for(int i = 0; i < getSource.getEdges().size(); i++)
			if(!getSource.getEdges().get(i).getTarget().isVisited())
				depthFirstSearch(getSource.getEdges().get(i).getTarget().getName());
	}
	
	/* Breadth first search. Start at the source vertex, iterate through all edges of that vertex, then proceed to the next vertex in the list. */
	public void breadthFirstSearch(String source) {
		
		Vertex getSource = graph.get(source);
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		getSource.setVisited(true);
		queue.add(getSource);
		
		while(!queue.isEmpty()) {
			
			getSource = queue.poll();
			System.out.print(getSource.getName() + " -----> ");
			
			for(int i = 0; i < getSource.getEdges().size(); i++) {
				if(!getSource.getEdges().get(i).getTarget().isVisited()) {
					getSource.getEdges().get(i).getTarget().setVisited(true);
					queue.add(getSource.getEdges().get(i).getTarget());
				}
			}
		}
	}

	//For options A and B we will first execute Dijkstra's algorithm from the source vertex from user input
	public void getOptionA(String source, String target) {
		
		getSourceVertex(source);
		Vertex targetVertex = graph.get(target);
		System.out.println(source + " -> " + targetVertex.getName() + " : " + targetVertex.getDistance());
	}
	
	//For option B we simply find all nodes within' that distance. 
	public void getOptionB(String source, String var) {

		getSourceVertex(source);
		String stringNumber = var.substring(2, var.length());
		String first = var.substring(0, 2);
		int number = Integer.parseInt(stringNumber);
		allNodesWithinDistance(source, first, number);
	}

	public void getSourceVertex(String source) {
		
		Vertex sourceVertex = graph.get(source);
		getShortestPathToEachNode(sourceVertex);
	}
}
