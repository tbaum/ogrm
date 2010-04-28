package org.ogrm.internal.graph;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

public interface Graph {

	public Transaction getAndBeginTx();

	public Node createNode();

	public Node getReferenceNode();

	public Node getNodeById( Object id );

	public Relationship getRelationshipById( Object id );

	public void dispose();
}
