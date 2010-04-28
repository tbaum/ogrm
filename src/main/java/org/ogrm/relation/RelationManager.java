package org.ogrm.relation;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.ogrm.internal.graph.StringRelationType;

public class RelationManager {

	private Node owner;

	private RelationshipType relName;

	private boolean incoming;

	private Direction direction;

	public RelationManager(String name, Node owner, Direction direction) {
		this.owner = owner;
		this.incoming = direction.equals( Direction.INCOMING );
		this.direction = direction;
		this.relName = new StringRelationType( name );
	}

	public Relationship createRelationship( Node entity ) {
		if (entity == null)
			return null;

		if (incoming)
			return entity.createRelationshipTo( owner, relName );
		else
			return owner.createRelationshipTo( entity, relName );
	}

	public Relationship createOrReplaceRelationship( Node entity ) {
		if (entity == null)
			return null;
		removeAllRelationshipsWith( entity );
		return createRelationship( entity );
	}

	public void removeRelationship( Node entity ) {
		if (entity == null)
			return;
		for (Relationship rel : owner.getRelationships( relName, direction )) {
			if (rel.getOtherNode( owner ).equals( entity )) {
				rel.delete();
				return;
			}
		}
	}

	public void removeAllRelationships() {
		for (Relationship rel : owner.getRelationships( relName, direction )) {
			rel.delete();
		}
	}

	public void removeAllRelationshipsWith( Node node ) {
		for (Relationship rel : owner.getRelationships( relName, direction )) {
			if (rel.getOtherNode( owner ).equals( node ))
				rel.delete();
		}
	}

	public Iterable<Relationship> getRelationships() {
		return owner.getRelationships( relName, direction );
	}

	public Relationship getSingleRelationship() {
		Relationship rel = owner.getSingleRelationship( relName, direction );
		return rel;
	}

}
