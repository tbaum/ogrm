package org.ogrm.internal.graph.memory;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MemoryRelationship extends PropertyHolderSupport implements Relationship, Comparable<MemoryRelationship> {

	private RelationshipType type;
	private MemoryGraph graph;
	private Long id;
	private MemoryNode from;
	private MemoryNode to;

	public MemoryRelationship(Long id, MemoryGraph graph, MemoryNode from, MemoryNode to, RelationshipType type) {
		this.graph = graph;
		this.id = id;
		this.from = from;
		this.to = to;
		this.type = type;
	}

	@Override
	public void delete() {
		graph.deleteRelationship( this );
	}

	@Override
	public MemoryNode getEndNode() {
		return to;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public Node[] getNodes() {
		return new Node[] { from, to };
	}

	@Override
	public Node getOtherNode( Node arg0 ) {
		return from.equals( arg0 ) ? to : from;
	}

	@Override
	public MemoryNode getStartNode() {
		return from;
	}

	@Override
	public RelationshipType getType() {
		return type;
	}

	@Override
	public boolean isType( RelationshipType arg0 ) {
		return arg0.name().equals( type.name() );
	}

	@Override
	public int compareTo( MemoryRelationship o ) {
		return id.compareTo( o.id );
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE ).append( "type", type ).append( "graph",
				graph ).append( "id", id ).append( "from", from.getId() ).append( "to", to.getId() ).toString();
	}

}
