package org.ogrm.internal.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.neo4j.graphdb.Relationship;
import org.ogrm.annotations.From;
import org.ogrm.annotations.To;
import org.ogrm.internal.context.PersistenceContext;
import org.ogrm.internal.proxy.FieldProperty;
import org.ogrm.util.scan.AbstractHandler;

public class ToFromHandler extends AbstractHandler{

	private Map<String, Synchronizer<Relationship>> synchronizers;

	private PersistenceContext context;

	public ToFromHandler(Map<String, Synchronizer<Relationship>> synchronizers, PersistenceContext context) {
		this.synchronizers = synchronizers;
		this.context = context;
	}

	@Override
	public void handleField( Field field ) {
		if (field.isAnnotationPresent( To.class )) {
			synchronizers.put( field.getName(), new ToSynchronizer( new FieldProperty( field ), context ) );
		} else if (field.isAnnotationPresent( From.class )) {
			synchronizers.put( field.getName(), new FromSynchronizer( new FieldProperty( field ), context ) );
		}

	}

	@Override
	public void handleMethod( Method method ) {
		// TODO Auto-generated method stub

	}

}
