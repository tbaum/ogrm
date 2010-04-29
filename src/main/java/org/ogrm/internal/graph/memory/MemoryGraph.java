package org.ogrm.internal.graph.memory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.event.KernelEventHandler;
import org.neo4j.graphdb.event.TransactionEventHandler;

public class MemoryGraph implements GraphDatabaseService {

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
	public Transaction beginTx() {
		return new MemoryTransaction();
	}

	public Node getNodeById( long id ) {
		return nodes.get( id );
	}

	@Override
	public Node getReferenceNode() {
		return referenceNode;
	}

	@Override
	public Relationship getRelationshipById( long id ) {
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
	public Iterable<Node> getAllNodes() {
		return new ArrayList<Node>(nodes.values());
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<RelationshipType> getRelationshipTypes() {
		return Collections.EMPTY_LIST;
	}
	
	@Override
	public void shutdown() {
		
	}

	@Override
	public boolean enableRemoteShell() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean enableRemoteShell( Map<String, Serializable> arg0 ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public KernelEventHandler registerKernelEventHandler( KernelEventHandler arg0 ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> TransactionEventHandler<T> registerTransactionEventHandler( TransactionEventHandler<T> arg0 ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KernelEventHandler unregisterKernelEventHandler( KernelEventHandler arg0 ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> TransactionEventHandler<T> unregisterTransactionEventHandler( TransactionEventHandler<T> arg0 ) {
		// TODO Auto-generated method stub
		return null;
	}
}
