
public class Edge {
	
	private Vertex source;
	private Vertex target;
	private int weight;
	
	public Edge(Vertex source, Vertex target, int weight) {
		
		this.source = source;
		this.target = target;
		this.weight = weight;
	}
	
	public void setSourceVertex(Vertex source) {
		
		this.source = source;
	}
	
	public Vertex getSource() {
		
		return source;
	}
	
	public int getWeight() {
		
		return weight;
	}

	public Vertex getTarget() {
	
		return target;
	}
}
