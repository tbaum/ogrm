package org.ogrm.util.scan;

import java.util.List;

import org.ogrm.util.Lists;

public abstract class AbstractHandler implements MemberHandler {

	@Override
	public MemberHandler add( MemberHandler handler ) {
		List<MemberHandler> list = Lists.newList();
		list.add( this );
		list.add( handler );
		return new CompositeHandler( list );
	}

}
