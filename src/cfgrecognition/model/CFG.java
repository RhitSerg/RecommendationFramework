package cfgrecognition.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import astrecognition.model.Graph;

public class CFG extends Graph {

	private int id;
	private String label;
	private List<CFG> connections;
	private CFG parent;

	public CFG(String label, int id) {
		this.id = id;
		this.label = label;
		this.connections = new ArrayList<CFG>();
	}

	@Override
	public int compareTo(Graph graph) {
		if (graph.getUniqueLabel().equals(getUniqueLabel()))
			return 0;
		return -1;
	}

	@Override
	public boolean isSink() {
		return (getConnections().size() == 0);
	}

	public int addConnection(CFG cfg) {
		this.connections.add(cfg);
		cfg.setParent(this);
		return this.connections.size() - 1;
	}

	public void setParent(CFG cfg) {
		if (null == this.parent)
			this.parent = cfg;
	}

	public CFG getParent() {
		return this.parent;
	}

	@Override
	public Collection<Graph> getConnections() {
		Collection<Graph> connections = new ArrayList<Graph>();
		for (CFG cfg : this.connections) {
			connections.add((Graph) cfg);
		}
		return connections;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public String getUniqueLabel() {
		return this.label + this.id;
	}

	@Override
	public String toString() {
		return this.getUniqueLabel();
	}
}
