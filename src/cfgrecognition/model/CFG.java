package cfgrecognition.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import astrecognition.model.Graph;

public class CFG extends Graph {

	private int id;
	private String label;
	private String exactLabel;
	private List<CFG> connections;
	private int lineNumber;
	private CFG parent;

	public CFG(String label, int id, int line) {
		this.id = id;
		this.label = label;
		this.lineNumber = line;
		this.connections = new ArrayList<CFG>();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CFG) {
			return this.compareTo((CFG) obj) == 0;
		}
		return super.equals(obj);
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
		if (!this.exactLabel.equals(cfg.getExactLabel())) {
			this.connections.add(cfg);
			cfg.setParent(this);
		}
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

	public String getExactLabel() {
		return this.exactLabel;
	}

	public void setExactLabel(String s) {
		this.exactLabel = s;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public String getUniqueLabel() {
		return this.id + ": " + this.label;
	}

	@Override
	public String toString() {
		return this.getUniqueLabel();
	}

	public int getLineNumber() {
		return this.lineNumber;
	}

	public int getId() {
		return this.id;
	}

	public void getAllNodes(HashSet<String> aggregateLabels) {

		aggregateLabels.add(this.getExactLabel());

		for (CFG cfg : this.connections) {
			if (!aggregateLabels.contains(cfg.getExactLabel())) {
				aggregateLabels.add(cfg.getExactLabel());
				cfg.getAllNodes(aggregateLabels);
			}
		}
	}

	@Override
	public int getTotalNodeCount() {
		HashSet<String> allNodes = new HashSet<String>();
		this.getAllNodes(allNodes);
		return allNodes.size();
	}
}
