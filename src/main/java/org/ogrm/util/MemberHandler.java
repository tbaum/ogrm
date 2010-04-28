package org.ogrm.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface MemberHandler {

	public void handleField( Field field );

	public void handleMethod( Method method );

}
