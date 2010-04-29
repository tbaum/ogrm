package org.ogrm.util.scan;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NullHandler implements MemberHandler {

	@Override
	public void handleField( Field field ) {

	}

	@Override
	public void handleMethod( Method method ) {

	}

	@Override
	public MemberHandler add( MemberHandler handler ) {
		return handler;
	}

}
