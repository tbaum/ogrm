package org.ogrm.util.scan;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

import org.ogrm.util.Lists;
import org.ogrm.util.ReflectionHelper;

public class Scan {

	private Class<?> target;

	private Collection<MemberHandler> handlers;

	public Scan(Class<?> target) {
		this.target = target;
		this.handlers = Lists.newList();
	}

	public Scan add( MemberHandler handler ) {
		handlers.add( handler );
		return this;
	}

	public void scanMembers() {
		for (MemberHandler handler : handlers) {

			for (Field field : ReflectionHelper.getDeclaredFields( target )) {
				handler.handleField( field );
			}
			for (Method method : ReflectionHelper.getPublicMethods( target )) {
				handler.handleMethod( method );
			}
		}
	}

}
