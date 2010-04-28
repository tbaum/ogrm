package org.ogrm.internal.graph.memory;

import java.util.TreeSet;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;

import com.bsc.commons.string.StringAppender;

public class MemoryNode extends PropertyHolderSupport implements Node {

	private Long id;
	private MemoryGraph graph;
	private TreeSet<MemoryRelationship> outgoing;
	private TreeSet<MemoryRelationship> incoming;

	public MemoryNode(Long id, MemoryGraph graph) {
		super();
		this.id = id;
		this.graph = graph;
		this.incoming = new TreeSet<MemoryRelationship>();
		this.outgoing = new TreeSet<MemoryRelationship>();
	}

	@Override
	public Relationship createRelationshipTo( Node arg0, RelationshipType arg1 ) {
		return graph.createRelationship( this, (MemoryNode) arg0, arg1 );
	}

	@Override
	public void delete() {
		graph.deleteNode( this );

	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public Iterable<Relationship> getRelationships() {
		return getRelationships( Direction.BOTH );
	}

	@Override
	public Iterable<Relationship> getRelationships( RelationshipType... arg0 ) {
		TreeSet<Relationship> set = new TreeSet<Relationship>();

		for (Relationship r : incoming) {
			for (RelationshipType t : arg0)
				if (r.getType().name().equals( t.name() ))
					set.add( r );
		}
		for (Relationship r : outgoing) {
			for (RelationshipType t : arg0)
				if (r.getType().name().equals( t.name() ))
					set.add( r );
		}

		return set;
	}

	@Override
	public Iterable<Relationship> getRelationships( Direction arg0 ) {
		TreeSet<Relationship> set = new TreeSet<Relationship>();

		if (arg0.equals( Direction.INCOMING ))
			set.addAll( incoming );

		else if (arg0.equals( Direction.OUTGOING ))
			set.addAll( outgoing );

		else {
			set.addAll( incoming );
			set.addAll( outgoing );
		}

		return set;
	}

	@Override
	public Iterable<Relationship> getRelationships( RelationshipType type, Direction direction ) {
		TreeSet<Relationship> set = new TreeSet<Relationship>();

		if (direction.equals( Direction.INCOMING ))
			for (Relationship r : incoming) {
				if (r.getType().name().equals( type.name() ))
					set.add( r );
			}
		else if (direction.equals( Direction.OUTGOING ))
			for (Relationship r : outgoing) {
				if (r.getType().name().equals( type.name() ))
					set.add( r );
			}

		else {
			for (Relationship r : incoming) {
				if (r.getType().name().equals( type.name() ))
					set.add( r );
			}
			for (Relationship r : outgoing) {
				if (r.getType().name().equals( type.name() ))
					set.add( r );
			}
		}

		return set;
	}

	@Override
	public Relationship getSingleRelationship( RelationshipType type, Direction direction ) {
		if (direction.equals( Direction.INCOMING ))
			for (Relationship r : incoming) {
				if (r.getType().name().equals( type.name() ))
					return r;
			}
		else if (direction.equals( Direction.OUTGOING ))
			for (Relationship r : outgoing) {
				if (r.getType().name().equals( type.name() ))
					return r;
			}

		else {
			for (Relationship r : incoming) {
				if (r.getType().name().equals( type.name() ))
					return r;
			}
			for (Relationship r : outgoing) {
				if (r.getType().name().equals( type.name() ))
					return r;
			}
		}
		return null;
	}

	@Override
	public boolean hasRelationship() {
		return hasRelationship( Direction.BOTH );
	}

	@Override
	public boolean hasRelationship( RelationshipType... types ) {
		for (Relationship r : incoming) {
			for (RelationshipType t : types)
				if (r.getType().name().equals( t.name() ))
					return true;
		}
		for (Relationship r : outgoing) {
			for (RelationshipType t : types)
				if (r.getType().name().equals( t.name() ))
					return true;
		}

		return false;
	}

	@Override
	public boolean hasRelationship( Direction direction ) {
		if (direction.equals( Direction.INCOMING ))
			return !incoming.isEmpty();

		if (direction.equals( Direction.OUTGOING ))
			return !outgoing.isEmpty();

		return !outgoing.isEmpty() || !incoming.isEmpty();
	}

	@Override
	public boolean hasRelationship( RelationshipType t, Direction arg1 ) {
		for (Relationship r : getRelationships( arg1 )) {
			if (r.getType().name().equals( t.name() ))
				return true;
		}
		return false;
	}

	@Override
	public Traverser traverse( Order arg0, StopEvaluator arg1, ReturnableEvaluator arg2, Object... arg3 ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Traverser traverse( Order arg0, StopEvaluator arg1, ReturnableEvaluator arg2, RelationshipType arg3,
			Direction arg4 ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Traverser traverse( Order arg0, StopEvaluator arg1, ReturnableEvaluator arg2, RelationshipType arg3,
			Direction arg4, RelationshipType arg5, Direction arg6 ) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeRelationship( MemoryRelationship rel ) {
		incoming.remove( rel );
		outgoing.remove( rel );
	}

	public TreeSet<MemoryRelationship> getOutgoing() {
		return outgoing;
	}

	public TreeSet<MemoryRelationship> getIncoming() {
		return incoming;
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.SHORT_PREFIX_STYLE ).append( "id", id )
				.append( getProperties() ).append( "outgoing", getOutgoingIds() ).toString();
	}

	private String getOutgoingIds() {
		return new StringAppender<Relationship>( outgoing ) {

			@Override
			protected void item( Relationship item ) {
				appendIfFirst( "{" );
				appendIfNotFirst( "," );
				append( item.getId() );
				appendIfLast( "}" );
			}
		}.build();

	}

}
