package org.ogrm.internal.proxy;

import org.neo4j.graphdb.PropertyContainer;
import org.ogrm.internal.wrap.TypeWrapper;

public interface ProxyFactory {

	public <T extends PropertyContainer> Object createProxy( T container, TypeWrapper<T> wrapper, Class<?> instanceClass);
	
}
