package org.ogrm.relation;


import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.ogrm.Relation;
import org.ogrm.internal.context.PersistenceContext;

public class ToOneRel<T> implements Relation<T> {

	private PersistenceContext context;

	private RelationManager helper;

	public ToOneRel(PersistenceContext context, Node owner, String name) {
		this.context = context;
		this.helper = new RelationManager( name, owner,Direction.OUTGOING );
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get() {
		return (T) context.getEntity( helper.getSingleRelationship().getStartNode() );
	}

	@Override
	public void set( T related ) {
		helper.createOrReplaceRelationship( context.getNode( related ) );
	}

}
