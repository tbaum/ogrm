/**
 * 
 */
package org.ogrm.internal.handler;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.neo4j.graphdb.PropertyContainer;
import org.ogrm.PersistenceException;
import org.ogrm.annotations.Value;
import org.ogrm.internal.converter.PrimitiveConverter;
import org.ogrm.internal.converter.SerializableConverter;
import org.ogrm.internal.converter.ValueConverter;
import org.ogrm.internal.proxy.FieldProperty;
import org.ogrm.internal.proxy.MethodProperty;
import org.ogrm.internal.proxy.Property;
import org.ogrm.util.ReflectionHelper;
import org.ogrm.util.TypeHelper;
import org.ogrm.util.scan.AbstractHandler;

public class ValueHandler<T extends PropertyContainer> extends AbstractHandler {

	private Map<String, Synchronizer<T>> synchronizers;

	public ValueHandler(Map<String, Synchronizer<T>> synchronizers) {
		this.synchronizers = synchronizers;
	}

	@Override
	public void handleField( Field field ) {
		if (field.isAnnotationPresent( Value.class )) {
			doHandle( new FieldProperty( field ) );
		}

	}

	public void handleMethod( Method method ) {
		if (method.isAnnotationPresent( Value.class )) {
			Method setter = ReflectionHelper.findSetterFor( method );
			if (setter == null)
				throw new PersistenceException( String.format( "No setter could be found for %s in type %s", method
						.getName(), method.getDeclaringClass().getName() ) );
			doHandle( new MethodProperty( method, setter ) );
		}
	}

	private void doHandle( Property property ) {
		Class<?> converterClass = property.getAnnotation( Value.class ).converter();

		String indexName = property.getAnnotation( Value.class ).index();

		if (converterClass.equals( PrimitiveConverter.class )) {

			if (TypeHelper.isPrimitive( property.getType() ) || TypeHelper.isPrimitiveArray( property.getType() ))
				converterClass = PrimitiveConverter.class;

			else if (Serializable.class.isAssignableFrom( property.getType() ))
				converterClass = SerializableConverter.class;

		}

		ValueConverter converter = (ValueConverter) ReflectionHelper.newInstance( converterClass );

		synchronizers.put( property.getName(), new PropertySynchronizer<T>( property, converter, indexName ) );
	}
}