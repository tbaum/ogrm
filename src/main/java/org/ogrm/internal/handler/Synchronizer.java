/**
 * 
 */
package org.ogrm.internal.handler;

import org.neo4j.graphdb.PropertyContainer;

public interface Synchronizer<T extends PropertyContainer> {

	public void onLoad( Object object, T container );

	public void onGet( Object object, T container );

	public void onPut( Object object, T container );

	public void onRemove( Object object, T container );
}