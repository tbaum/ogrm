package org.ogrm.util.scan;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.ogrm.util.Lists;

public class CompositeHandler implements MemberHandler {

	private List<MemberHandler> components;

	public CompositeHandler() {
		this( Lists.<MemberHandler> newList() );
	}

	public CompositeHandler(List<MemberHandler> components) {
		super();
		this.components = components;
	}

	@Override
	public void handleField( Field field ) {
		for (MemberHandler component : components) {
			component.handleField( field );
		}

	}

	@Override
	public void handleMethod( Method method ) {
		for (MemberHandler component : components) {
			component.handleMethod( method );
		}
	}

	@Override
	public MemberHandler add( MemberHandler handler ) {
		List<MemberHandler> list = Lists.newList();
		list.addAll( components );
		list.add( handler );
		return new CompositeHandler( list );
	}

}
