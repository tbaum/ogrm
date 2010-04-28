package org.ogrm.internal.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.ogrm.util.ReflectionHelper;


public class MethodProperty implements Property {

	private String name;

	private Method getter;

	private Method setter;

	public MethodProperty(Method getter, Method setter) {
		this.getter = getter;
		this.setter = setter;
		setName();
	}

	@Override
	public Object get( Object source ) {
		return ReflectionHelper.invokeAndWrap( getter, source );
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void set( Object target, Object value ) {
		ReflectionHelper.invokeAndWrap( setter, target, value );
	}

	private void setName() {
		if (getter.getName().startsWith( "get" ))
			name = StringUtils.uncapitalize( getter.getName().substring( 3 ) );
		else
			name = getter.getName();
	}

	@Override
	public Class<?> getType() {
		return getter.getReturnType();
	}

	@Override
	public <T extends Annotation> T getAnnotation( Class<T> annotationClass ) {
		return getter.getAnnotation( annotationClass );
	}

	@Override
	public Annotation[] getAnnotations() {
		return getter.getAnnotations();
	}

	@Override
	public Annotation[] getDeclaredAnnotations() {
		return getter.getDeclaredAnnotations();
	}

	@Override
	public boolean isAnnotationPresent( Class<? extends Annotation> annotationClass ) {
		return getter.isAnnotationPresent( annotationClass );
	}
}
