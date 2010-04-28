package org.ogrm.internal.graph.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.ogrm.internal.graph.Graph;

public class Neo4jGraph implements Graph {

	private GraphDatabaseService neo;

	public Neo4jGraph(GraphDatabaseService neo) {
		this.neo = neo;
	}

	@Override
	public Node createNode() {
		return neo.createNode();
	}

	@Override
	public Transaction getAndBeginTx() {
		return neo.beginTx();
	}

	@Override
	public Node getNodeById( Object id ) {
		return neo.getNodeById( (Long) id );
	}

	@Override
	public Node getReferenceNode() {
		return neo.getReferenceNode();
	}

	@Override
	public Relationship getRelationshipById( Object id ) {
		return neo.getRelationshipById( (Long) id );
	}

	@Override
	public void dispose() {
		neo.shutdown();
	}

}
