package org.ogrm.internal.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.ogrm.annotations.Load;
import org.ogrm.annotations.Store;
import org.ogrm.util.scan.AbstractHandler;

public class LoadStoreHandler extends AbstractHandler {

	private Map<String, Collection<String>> methodToLoadPropertyMap;
	private Map<String, Collection<String>> methodToStorePropertyMap;

	public LoadStoreHandler(Map<String, Collection<String>> methodToLoadPropertyMap,
			Map<String, Collection<String>> methodToStorePropertyMap) {
		this.methodToLoadPropertyMap = methodToLoadPropertyMap;
		this.methodToStorePropertyMap = methodToStorePropertyMap;
	}

	@Override
	public void handleField( Field field ) {
		// Not on fields...
	}

	@Override
	public void handleMethod( Method method ) {
		if (method.isAnnotationPresent( Load.class )) {
			String[] properties = method.getAnnotation( Load.class ).value();
			Collection<String> props = Arrays.asList( properties );
			methodToLoadPropertyMap.put( method.getName(), props );
		}
		if (method.isAnnotationPresent( Store.class )) {
			String[] properties = method.getAnnotation( Store.class ).value();
			Collection<String> props = Arrays.asList( properties );
			methodToStorePropertyMap.put( method.getName(), props );
		}

	}

}
