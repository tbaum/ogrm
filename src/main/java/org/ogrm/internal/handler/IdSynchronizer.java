/**
 * 
 */
package org.ogrm.internal.handler;

import org.neo4j.graphdb.Node;
import org.ogrm.internal.proxy.Property;

class IdSynchronizer implements Synchronizer<Node> {

	private Property property;

	public IdSynchronizer(Property property) {
		this.property = property;
	}

	@Override
	public void onGet( Object object, Node container ) {

	}

	@Override
	public void onPut( Object object, Node container ) {
		// ID never changes
	}

	@Override
	public void onLoad( Object object, Node container ) {
		property.set( object, container.getId() );
		
	}

	@Override
	public void onRemove( Object object, Node container ) {
		
	}

}