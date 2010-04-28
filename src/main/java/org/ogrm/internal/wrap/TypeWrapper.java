package org.ogrm.internal.wrap;

import java.lang.reflect.Method;

import org.neo4j.graphdb.PropertyContainer;


public interface TypeWrapper<T extends PropertyContainer> {

	public void onLoad( Object object, T container);
	
	public void beforeCall( Object object, T container,Method method);
	
	public void afterCall( Object object, T container,Method method);

	public void onRemove(Object object, T container);

}
