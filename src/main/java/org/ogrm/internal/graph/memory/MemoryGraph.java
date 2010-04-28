package org.ogrm.internal.graph.memory;

import java.util.TreeMap;


import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.ogrm.internal.graph.Graph;

public class MemoryGraph implements Graph {

	private TreeMap<Long, MemoryNode> nodes;
	private TreeMap<Long, MemoryRelationship> relationships;
	private Node referenceNode;
	private NodeIdFactory idFactory;

	public MemoryGraph() {
		nodes = new TreeMap<Long, MemoryNode>();
		relationships = new TreeMap<Long, MemoryRelationship>();
		idFactory = new NodeIdFactory();
		referenceNode = createNode();
	}

	@Override
	public Node createNode() {
		MemoryNode node = new MemoryNode( idFactory.getNextId(), this );
		nodes.put( node.getId(), node );
		return node;
	}

	@Override
	public Transaction getAndBeginTx() {
		return new MemoryTransaction();
	}

	@Override
	public Node getNodeById( Object id ) {
		return nodes.get( (Long) id );
	}

	@Override
	public Node getReferenceNode() {
		return referenceNode;
	}

	@Override
	public Relationship getRelationshipById( Object id ) {
		return relationships.get( (Long) id );
	}

	public TreeMap<Long, MemoryNode> getNodes() {
		return nodes;
	}

	public TreeMap<Long, MemoryRelationship> getRelationships() {
		return relationships;
	}

	public MemoryRelationship createRelationship( MemoryNode from, MemoryNode to, RelationshipType type ) {
		MemoryRelationship rel = new MemoryRelationship( idFactory.getNextId(), this, from, to, type );
		from.getOutgoing().add( rel );
		to.getIncoming().add( rel );
		relationships.put( rel.getId(), rel );
		return rel;
	}

	public void deleteRelationship( MemoryRelationship rel ) {
		rel.getStartNode().removeRelationship( rel );
		rel.getEndNode().removeRelationship( rel );
		relationships.remove( rel.getId() );
	}

	public void deleteNode( MemoryNode node ) {
		for (Relationship rel : node.getRelationships()) {
			MemoryRelationship memrel = (MemoryRelationship) rel;
			deleteRelationship( memrel );
		}

		nodes.remove( node.getId() );
	}

	@Override
	public void dispose() {
		
	}
}
