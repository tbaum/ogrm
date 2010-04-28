package org.ogrm.internal.handler;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.ogrm.internal.context.PersistenceContext;
import org.ogrm.internal.proxy.Property;
import org.ogrm.relation.RelationManager;

public class OneSynchronizer implements Synchronizer<Node> {

	private Property property;

	private PersistenceContext context;

	private Direction direction;

	public OneSynchronizer(Property property, PersistenceContext context, Direction direction) {
		this.property = property;
		this.context = context;
		this.direction = direction;
	}

	@Override
	public void onGet( Object object, Node container ) {
		Node node = (Node) container;

		RelationManager manager = new RelationManager( property.getName(), node, direction );

		Relationship rel = manager.getSingleRelationship();

		Node other = rel.getOtherNode( node );

		Object relatedEntity = context.getEntity( other );

		property.set( object, relatedEntity );

	}

	@Override
	public void onPut( Object object, Node container ) {
		Object newEntity = property.get( object );

		Node node = (Node) container;

		RelationManager manager = new RelationManager( property.getName(), node, direction );

		Relationship rel = manager.getSingleRelationship();

		Node otherCurrentNode= rel != null ? rel.getOtherNode( node ) : null;

		Node otherNewNode = newEntity != null ? context.getNode( newEntity ) : null;

		if(otherCurrentNode==null && otherNewNode!=null)
			manager.createRelationship( otherNewNode );
		
		else if(otherCurrentNode!=null && otherNewNode==null)
			rel.delete();
		
		else if(otherCurrentNode.getId()!=otherNewNode.getId())
			manager.createOrReplaceRelationship( otherNewNode );
		
	}

	@Override
	public void onLoad( Object object, Node container ) {
		
	}

	@Override
	public void onRemove( Object object, Node container ) {
		
	}
}
