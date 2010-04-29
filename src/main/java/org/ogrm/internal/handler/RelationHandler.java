package org.ogrm.internal.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.ogrm.Relation;
import org.ogrm.annotations.FromMany;
import org.ogrm.annotations.FromOne;
import org.ogrm.annotations.ToMany;
import org.ogrm.annotations.ToOne;
import org.ogrm.internal.context.PersistenceContext;
import org.ogrm.internal.proxy.FieldProperty;
import org.ogrm.util.scan.AbstractHandler;

public class RelationHandler extends AbstractHandler{

	private Map<String, Synchronizer<Node>> synchronizers;

	private PersistenceContext context;

	public RelationHandler(Map<String, Synchronizer<Node>> synchronizers, PersistenceContext context) {
		this.synchronizers = synchronizers;
		this.context = context;
	}

	@Override
	public void handleField( Field field ) {
		if (field.isAnnotationPresent( ToOne.class )) {

			if (Relation.class.isAssignableFrom( field.getType() )) {
				synchronizers.put( field.getName(), new OneRelSynchronizer( new FieldProperty( field ), context,
						Direction.OUTGOING ) );
			} else {
				synchronizers.put( field.getName(), new OneSynchronizer( new FieldProperty( field ), context,
						Direction.OUTGOING ) );
			}
		} else if (field.isAnnotationPresent( ToMany.class )) {
			if (field.getAnnotation( ToMany.class ).typed()) {
				synchronizers.put( field.getName(), new ManyTypedRelSynchronizer( field.getName(), new FieldProperty(
						field ), context, Direction.OUTGOING ) );
			} else {
				synchronizers.put( field.getName(), new ManyRelSynchronizer( new FieldProperty( field ), context,
						Direction.OUTGOING ) );
			}
		} else if (field.isAnnotationPresent( FromOne.class )) {
			if (Relation.class.isAssignableFrom( field.getType() )) {
				synchronizers.put( field.getAnnotation( FromOne.class ).incoming(), new OneRelSynchronizer(
						new FieldProperty( field ), context, Direction.INCOMING ) );
			} else {
				synchronizers.put( field.getAnnotation( FromOne.class ).incoming(), new OneSynchronizer(
						new FieldProperty( field ), context, Direction.INCOMING ) );
			}
		} else if (field.isAnnotationPresent( FromMany.class )) {
			String name = field.getAnnotation( FromMany.class ).incoming();
			if (field.getAnnotation( FromMany.class ).typed()) {
				synchronizers.put( field.getName(), new ManyTypedRelSynchronizer( name, new FieldProperty( field ), context,
						Direction.INCOMING ) );
			} else {
				synchronizers.put( field.getName(), new ManyRelSynchronizer( new FieldProperty( field ), context,
						Direction.INCOMING ) );
			}
		}
	}

	@Override
	public void handleMethod( Method method ) {
		// TODO No support for methods yet.
	}

}
