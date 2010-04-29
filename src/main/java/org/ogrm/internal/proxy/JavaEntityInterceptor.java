package org.ogrm.internal.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.neo4j.graphdb.PropertyContainer;
import org.ogrm.internal.wrap.TypeWrapper;

public class JavaEntityInterceptor<T extends PropertyContainer> implements InvocationHandler {

	private T propertyContainer;
	private TypeWrapper<T> synchronizer;
	private Object entity;

	public JavaEntityInterceptor(T propertyContainer, TypeWrapper<T> synchronizer, Object entity) {
		this.propertyContainer = propertyContainer;
		this.synchronizer = synchronizer;
		this.entity = entity;
	}

	@Override
	public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {

		if (method.getName().equals( "getContainer" ))
			return (T) propertyContainer;

		if (method.getName().equals( "hashCode" ))
			return propertyContainer.hashCode();

		if (method.getName().equals( "equals" ) && args.length == 1) {
			Object arg = args[0];
			if (arg instanceof ContainerWrapper<?>) 
				return propertyContainer.equals( ((ContainerWrapper<?>) arg).getContainer() );
			
		}

		synchronizer.beforeCall( entity, propertyContainer, method );

		Object reply = method.invoke( entity, args );

		synchronizer.afterCall( entity, propertyContainer, method );

		return reply;
	}

	public PropertyContainer getPropertyContainer() {
		return propertyContainer;
	}

}
