package org.ogrm.internal.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.ogrm.annotations.Id;
import org.ogrm.internal.proxy.FieldProperty;
import org.ogrm.util.scan.AbstractHandler;

public class IdHandler extends AbstractHandler {

	private Map<String, Synchronizer<Node>> synchronizers;

	public IdHandler(Map<String, Synchronizer<Node>> synchronizers) {
		this.synchronizers = synchronizers;
	}

	@Override
	public void handleField( Field field ) {
		if (field.isAnnotationPresent( Id.class )) {
			synchronizers.put( field.getName(), new IdSynchronizer( new FieldProperty( field ) ) );
		}

	}

	@Override
	public void handleMethod( Method method ) {
		// TODO Auto-generated method stub

	}

}
