package org.ogrm.relation;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.ogrm.TypedRelation;
import org.ogrm.internal.context.PersistenceContext;

public class TypedFromOneRel<T> implements TypedRelation<T> {

	private PersistenceContext context;

	private RelationManager helper;

	public TypedFromOneRel(PersistenceContext context, Node owner, String name) {
		super();
		this.context = context;
		this.helper = new RelationManager( name, owner, Direction.INCOMING );
	}

	@Override
	public <E extends T> E createAndSetRelationTo( Object entity, Class<E> type ) {
		Node other = context.getNode( entity );
		Relationship rel = helper.createOrReplaceRelationship( other );
		return context.createTypedRelationship( rel, type );
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get() {
		Relationship rel = helper.getSingleRelationship();
		return (T) context.getTypedRelation( rel );
	}

}
