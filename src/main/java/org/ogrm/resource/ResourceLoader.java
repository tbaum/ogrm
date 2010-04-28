package org.ogrm.resource;

public interface ResourceLoader {

	public Class<?> classForName( String name );

	public ClassLoader getClassLoader();
}
