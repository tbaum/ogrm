package org.ogrm.internal.proxy;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.PropertyContainer;
import org.ogrm.config.Configuration;
import org.ogrm.internal.wrap.TypeWrapper;
import org.ogrm.util.Lists;
import org.ogrm.util.ReflectionHelper;

public class JavaProxyFactory implements ProxyFactory{

	private Map<String, List<Class<?>>> interfacesCache; 
	private Configuration config;
	
	
	public JavaProxyFactory(Map<String, List<Class<?>>> interfacesCache, Configuration configuration) {
		this.interfacesCache = interfacesCache;
		this.config = configuration;
	}



	@Override
	public <T extends PropertyContainer> Object createProxy( T container, TypeWrapper<T> wrapper, Class<?> instanceClass ) {
		
		Object instance = ReflectionHelper.newInstance( instanceClass );
		
		wrapper.onLoad( instance, container );

		JavaEntityInterceptor<T> handler = new JavaEntityInterceptor<T>( container, wrapper, instance );

		List<Class<?>> interfaces = Lists.newList( interfacesCache.get( instance.getClass().getName() ) );

		Object proxy = Proxy.newProxyInstance( config.getResourceLoader().getClassLoader(), interfaces
				.toArray( new Class<?>[0] ), handler );

		return proxy;
	}

}
