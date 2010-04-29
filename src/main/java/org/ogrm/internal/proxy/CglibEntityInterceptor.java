package org.ogrm.internal.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.neo4j.graphdb.PropertyContainer;
import org.ogrm.internal.wrap.TypeWrapper;

public class CglibEntityInterceptor<T extends PropertyContainer> implements MethodInterceptor {
	private T propertyContainer;
	private TypeWrapper<T> synchronizer;

	public CglibEntityInterceptor(T propertyContainer, TypeWrapper<T> synchronizer) {
		this.propertyContainer = propertyContainer;
		this.synchronizer = synchronizer;
	}

	@Override
	public Object intercept( Object object, Method method, Object[] args, MethodProxy methodProxy ) throws Throwable {

		if (method.getName().equals( "getContainer" ))
			return (T) propertyContainer;

		if (method.getName().equals( "hashCode" ))
			return propertyContainer.hashCode();

		if (method.getName().equals( "equals" ) && args.length == 1) {
			Object arg = args[0];
			if (arg instanceof ContainerWrapper<?>)
				return propertyContainer.equals( ((ContainerWrapper<?>) arg).getContainer() );

		}

		synchronizer.beforeCall( object, propertyContainer, method );

		Object reply = methodProxy.invokeSuper( object, args );

		synchronizer.afterCall( object, propertyContainer, method );

		return reply;
	}

	public PropertyContainer getPropertyContainer() {
		return propertyContainer;
	}

}
