import java.util.*;

public class Vertex implements Comparable<Vertex>{
	
	private String name;
	private int distance;
	private boolean visited;
	private Vertex previousVertex;
	private LinkedList<Edge> adjacencyMatrix;

	public Vertex(String name) {
		
		this.name = name;
		this.distance = Integer.MAX_VALUE;
		adjacencyMatrix = new LinkedList<Edge>();
	}
	
	public void addNeighbour(Edge edge) {
		
		this.adjacencyMatrix.add(edge);
	}

	public String getName() {
		
		return name;
	}
	
	public void setDistance(int distance) {
		
		this.distance = distance;
	}

	public int getDistance() {
		
		return distance;
	}

	public void setVisited(boolean visited) {
		
		this.visited = visited;
	}
	
	public boolean isVisited() {
		
		return visited;
	}
	
	public LinkedList<Edge> getAdjacencyMatrix(){
		
		return adjacencyMatrix;
	}
	
	public void setPreviousVertex(Vertex previousVertex) {
		
		this.previousVertex = previousVertex;
	}

	public Vertex getPreviousVertex() {
		
		return previousVertex;
	}

	public int compareTo(Vertex adjacentVertex) {
		
		return Integer.compare(this.distance, adjacentVertex.getDistance());
	}
}
