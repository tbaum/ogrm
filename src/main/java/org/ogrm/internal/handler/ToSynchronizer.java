package org.ogrm.internal.handler;

import org.neo4j.graphdb.Relationship;
import org.ogrm.internal.context.PersistenceContext;
import org.ogrm.internal.proxy.Property;

public class ToSynchronizer implements Synchronizer<Relationship> {

	private Property property;
	
	private PersistenceContext context;
	
	public ToSynchronizer(Property property, PersistenceContext context) {
		this.property = property;
		this.context = context;
	}

	@Override
	public void onGet( Object object, Relationship container ) {
		
	}

	@Override
	public void onLoad( Object object, Relationship container ) {
		Object to = context.getEntity( container.getEndNode() );
		property.set( object, to );
	}

	@Override
	public void onPut( Object object, Relationship container ) {
		
	}

	@Override
	public void onRemove( Object object, Relationship container ) {
		
	}

}
