package org.ogrm.internal.converter;

public interface ValueConverter {

	public Object fromSimpleValue( Object simpleValue );

	public Object toSimpleValue( Object realValue );
}
