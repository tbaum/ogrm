package org.ogrm.internal.handler;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.ogrm.internal.context.PersistenceContext;
import org.ogrm.internal.proxy.Property;
import org.ogrm.relation.FromManySet;
import org.ogrm.relation.ToManySet;

public class ManyRelSynchronizer implements Synchronizer<Node> {

	private Property property;

	private PersistenceContext context;

	private Direction direction;

	public ManyRelSynchronizer(Property property, PersistenceContext context, Direction direction) {
		this.property = property;
		this.context = context;
		this.direction = direction;
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
		Object set = null;
		if (direction.equals( Direction.OUTGOING ))
			set = new ToManySet<Object>( context, (Node) container, property.getName() );
		else
			set = new FromManySet<Object>( context, (Node) container, property.getName() );
		property.set( object, set );

	}

	@Override
	public void onRemove( Object object, Node container ) {

	}

}
