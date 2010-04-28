package org.ogrm.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TypeHelper {
	private static final Set<Class<?>> primitiveTypes = new HashSet<Class<?>>();

	static {
		primitiveTypes.add( Byte.class );
		primitiveTypes.add( Short.class );
		primitiveTypes.add( Character.class );
		primitiveTypes.add( Integer.class );
		primitiveTypes.add( Long.class );
		primitiveTypes.add( Float.class );
		primitiveTypes.add( Double.class );
		primitiveTypes.add( Boolean.class );
		primitiveTypes.add( String.class );

	}

	public static boolean isPrimitive( Class<?> c ) {
		return c.isPrimitive() || primitiveTypes.contains( c );
	}

	public static boolean isPrimitive( Object o ) {
		return isPrimitive( o.getClass() );
	}

	public static boolean isPrimitiveArray( Class<?> c ) {
		return c.isArray() && isPrimitive( c.getComponentType() );
	}

	public static boolean isPrimitiveArray( Object o ) {
		return isPrimitiveArray( o.getClass() );
	}

	public static boolean isSerializable( Class<?> c ) {
		return Serializable.class.isAssignableFrom( c );
	}

	public static boolean isSerializable( Object o ) {
		return Serializable.class.isAssignableFrom( o.getClass() );
	}
}
