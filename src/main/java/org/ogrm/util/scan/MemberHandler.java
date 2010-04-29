package org.ogrm.util.scan;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface MemberHandler {

	public MemberHandler add(MemberHandler handler);
	
	public void handleField( Field field );

	public void handleMethod( Method method );

}
