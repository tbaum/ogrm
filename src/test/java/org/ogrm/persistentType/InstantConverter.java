package org.ogrm.persistentType;

import org.ogrm.internal.converter.ValueConverter;

public class InstantConverter implements ValueConverter {

	@Override
	public Object fromSimpleValue( Object simpleValue ) {
		return Instant.fromMillis( (Long) simpleValue );
	}

	@Override
	public Object toSimpleValue( Object realValue ) {
		return ((Instant) realValue).getInstant();
	}

}
