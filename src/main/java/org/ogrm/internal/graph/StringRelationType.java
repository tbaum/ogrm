package org.ogrm.internal.graph;

import org.neo4j.graphdb.RelationshipType;

public class StringRelationType implements RelationshipType {

	private String name;

	public StringRelationType(String name) {
		this.name = name;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return "Type(" + name + ")";
	}
}
