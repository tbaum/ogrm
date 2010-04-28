package org.ogrm.internal.converter;

public class PrimitiveConverter implements ValueConverter {

	@Override
	public Object fromSimpleValue( Object simpleValue ) {
		return simpleValue;
	}

	@Override
	public Object toSimpleValue( Object realValue ) {
		return realValue;
	}

}
