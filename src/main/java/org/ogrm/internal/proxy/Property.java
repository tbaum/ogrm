package org.ogrm.internal.proxy;

import java.lang.reflect.AnnotatedElement;

public interface Property extends AnnotatedElement {

	public Object get( Object source );

	public void set( Object target, Object value );

	public String getName();
	
	public Class<?> getType();

}
