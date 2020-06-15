package hanlp;

public class word {

	private String name;
	private int id;
	private String cx;
	private int headId;
	private boolean inGraph = false;
	private int graphNum;
	
	public word() {
		
	}
	
	public word(String name,int id,String cx,int headId) {
		this.name = name;
		this.id = id;
		this.cx = cx;
		this.headId = headId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCx() {
		return cx;
	}
	public void setCx(String cx) {
		this.cx = cx;
	}
	public void putInGraph() {
		this.inGraph = true;
	}

	public int getHeadId() {
		return headId;
	}

	public void setHeadId(int headId) {
		this.headId = headId;
	}

	public boolean isInGraph() {
		return inGraph;
	}

	public void setInGraph(boolean inGraph) {
		this.inGraph = inGraph;
	}

	public int getGraphNum() {
		return graphNum;
	}

	public void setGraphNum(int graphNum) {
		this.graphNum = graphNum;
	}
}
