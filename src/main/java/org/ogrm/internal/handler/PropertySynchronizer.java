/**
 * 
 */
package org.ogrm.internal.handler;

import org.neo4j.graphdb.PropertyContainer;
import org.ogrm.annotations.Value;
import org.ogrm.index.Indexer;
import org.ogrm.internal.converter.ValueConverter;
import org.ogrm.internal.proxy.Property;
import org.ogrm.util.ObjectHelper;

class PropertySynchronizer<T extends PropertyContainer> implements Synchronizer<T> {

	private ValueConverter converter;

	private Property property;

	private String fullProperyName;

	public PropertySynchronizer(Property property, ValueConverter converter, String indexName) {
		this.converter = converter;
		this.property = property;

		if (indexName.equals( Value.NO_INDEX ))
			fullProperyName = property.getName();

		else
			fullProperyName = Indexer.createPropertyName( property.getName(), indexName );
	}

	public void onGet( Object object, T container ) {
		Object simpleValue = container.getProperty( fullProperyName );
		Object orginalValue = converter.fromSimpleValue( simpleValue );

		property.set( object, orginalValue );
	}

	public void onPut( Object object, T container ) {
		Object orginalValue = property.get( object );
		Object simpleValue = converter.toSimpleValue( orginalValue );

		Object existingSimpleValue = container.getProperty( fullProperyName, null );

		if (ObjectHelper.areNotEqual( simpleValue, existingSimpleValue ))
			container.setProperty( fullProperyName, simpleValue );

	}

	@Override
	public void onLoad( Object object, T container ) {

	}

	@Override
	public void onRemove( Object object, T container ) {

	}
}