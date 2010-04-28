package org.ogrm.internal.wrap;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.neo4j.graphdb.PropertyContainer;
import org.ogrm.internal.handler.Synchronizer;


import com.bsc.commons.collections.Maps;

public abstract class ReflectionTypeWrapper<T extends PropertyContainer> implements TypeWrapper<T> {

	private Class<?> c;

	private Map<String, Synchronizer<T>> synchronizers;
	private Map<String, Collection<String>> methodToLoadPropertyMap;
	private Map<String, Collection<String>> methodToStorePropertyMap;

	private boolean init;

	public ReflectionTypeWrapper(Class<?> c) {
		this.c = c;
		this.init = false;
		this.synchronizers = Maps.newMap();
		this.methodToLoadPropertyMap = Maps.newMap();
		this.methodToStorePropertyMap = Maps.newMap();
	}

	public void onLoad( Object object, T container ) {
		checkInit();
		for (String propertyName : getSynchronizers().keySet()) {
			Synchronizer<T> synchronizer = getSynchronizers().get( propertyName );
			synchronizer.onLoad( object, container );
		}
	};

	public void beforeCall( Object object, T container, Method method ) {
		checkInit();

		Collection<String> propertyNames = getMethodToLoadPropertyMap().get( method.getName() );

		if (propertyNames == null)
			return;

		for (String propertyName : getMethodToLoadPropertyMap().get( method.getName() )) {
			Synchronizer<T> synchronizer = getSynchronizers().get( propertyName );
			synchronizer.onGet( object, container );
		}
	}

	public void afterCall( Object object, T container, Method method ) {
		checkInit();

		Collection<String> propertyNames = getMethodToStorePropertyMap().get( method.getName() );

		if (propertyNames == null)
			return;

		for (String propertyName : getMethodToStorePropertyMap().get( method.getName() )) {
			Synchronizer<T> synchronizer = getSynchronizers().get( propertyName );
			synchronizer.onPut( object, container );
		}
	}

	public void onRemove( Object object, T container ) {
		checkInit();
		for (String propertyName : getSynchronizers().keySet()) {
			Synchronizer<T> synchronizer = getSynchronizers().get( propertyName );
			synchronizer.onRemove( object, container );
		}
	};

	public Class<?> getType() {
		return c;
	}

	protected abstract void scan( Class<?> c );

	private void checkInit() {
		if (!init) {
			init = true;
			scan( c );
		}
	}

	protected Map<String, Collection<String>> getMethodToLoadPropertyMap() {
		return methodToLoadPropertyMap;
	}

	protected Map<String, Collection<String>> getMethodToStorePropertyMap() {
		return methodToStorePropertyMap;
	}

	protected Map<String, Synchronizer<T>> getSynchronizers() {
		return synchronizers;
	}
}
