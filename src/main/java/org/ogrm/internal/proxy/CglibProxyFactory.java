package org.ogrm.internal.proxy;

import net.sf.cglib.proxy.Enhancer;

import org.neo4j.graphdb.PropertyContainer;
import org.ogrm.config.Configuration;
import org.ogrm.internal.wrap.TypeWrapper;

public class CglibProxyFactory implements ProxyFactory {

	private Configuration config;

	public CglibProxyFactory(Configuration configuration) {
		this.config = configuration;
	}

	@Override
	public <T extends PropertyContainer> Object createProxy( T container, TypeWrapper<T> wrapper, Class<?> instanceClass ) {

		CglibEntityInterceptor<T> interceptor = new CglibEntityInterceptor<T>( container, wrapper );

		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass( instanceClass );
		enhancer.setInterfaces( new Class[]{ContainerWrapper.class} );
		enhancer.setCallback( interceptor );
		enhancer.setClassLoader( config.getResourceLoader().getClassLoader() );

		Object proxy = enhancer.create();

		wrapper.onLoad( proxy, container );

		return proxy;
	}

}
