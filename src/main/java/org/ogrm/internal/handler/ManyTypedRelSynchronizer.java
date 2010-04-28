package org.ogrm.internal.handler;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.ogrm.internal.context.PersistenceContext;
import org.ogrm.internal.proxy.Property;
import org.ogrm.relation.TypedManyBag;

public class ManyTypedRelSynchronizer implements Synchronizer<Node> {

	private Property property;

	private PersistenceContext context;

	private Direction direction;

	private String name;

	public ManyTypedRelSynchronizer(String name, Property property, PersistenceContext context, Direction direction) {
		this.property = property;
		this.context = context;
		this.direction = direction;
		this.name = name;
	}

	@Override
	public void onGet( Object object, Node container ) {
		// No action
	}

	@Override
	public void onPut( Object object, Node container ) {
		// put is handled in the sets
	}

	@Override
	public void onLoad( Object object, Node container ) {
		Object set = new TypedManyBag<Object>( context, (Node) container, name, direction );
		property.set( object, set );

	}

	@Override
	public void onRemove( Object object, Node container ) {

	}

}
