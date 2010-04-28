package org.ogrm.resource;

import org.ogrm.PersistenceException;

public class DefaultResourceLoader implements ResourceLoader {

	@Override
	public Class<?> classForName( String name ) {
		try {
			return Class.forName( name );
		} catch (ClassNotFoundException e) {
			throw new PersistenceException( "Could not load class " + name, e );
		}
	}

	@Override
	public ClassLoader getClassLoader() {
	return ClassLoader.getSystemClassLoader();
	}

}
