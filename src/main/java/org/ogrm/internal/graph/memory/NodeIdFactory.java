package org.ogrm.internal.graph.memory;

public class NodeIdFactory {

	private long nextId;

	public NodeIdFactory() {
		this.nextId = 0;
	}

	public Long getNextId() {
		return nextId++;
	}

}
