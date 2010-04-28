/**
 * 
 */
package org.ogrm.internal.handler;

import org.neo4j.graphdb.PropertyContainer;
import org.ogrm.internal.converter.ValueConverter;
import org.ogrm.internal.proxy.Property;
import org.ogrm.util.ObjectHelper;

class PropertySynchronizer<T extends PropertyContainer> implements Synchronizer<T> {

	private ValueConverter converter;

	private Property property;

	public PropertySynchronizer(Property property, ValueConverter converter) {
		this.converter = converter;
		this.property = property;
	}

	public void onGet( Object object, T container ) {
		Object simpleValue = container.getProperty( property.getName() );
		Object orginalValue = converter.fromSimpleValue( simpleValue );

		property.set( object, orginalValue );
	}

	public void onPut( Object object, T container ) {
		String propertyName = property.getName();
		Object orginalValue = property.get( object );
		Object simpleValue = converter.toSimpleValue( orginalValue );

		Object existingSimpleValue = container.getProperty( propertyName );

		if (ObjectHelper.areNotEqual( simpleValue, existingSimpleValue ))
			container.setProperty( propertyName, simpleValue );

	}

	@Override
	public void onLoad( Object object, T container ) {
		
	}

	@Override
	public void onRemove( Object object, T container ) {
		
	}
}