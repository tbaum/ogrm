package org.ogrm.internal.wrap;

import java.util.Map;

import org.neo4j.graphdb.Relationship;
import org.ogrm.internal.context.PersistenceContext;
import org.ogrm.internal.handler.LoadStoreHandler;
import org.ogrm.internal.handler.Synchronizer;
import org.ogrm.internal.handler.ToFromHandler;
import org.ogrm.util.Scan;

public class RelationshipTypeWrapper extends ReflectionTypeWrapper<Relationship> {
	private PersistenceContext context;

	public RelationshipTypeWrapper(Class<?> c, PersistenceContext context) {
		super( c );
		this.context = context;
	}

	protected void scan( Class<?> type ) {
		Map<String, Synchronizer<Relationship>> map = getSynchronizers();
		Scan scan = new Scan( type );
		scan.add( new ToFromHandler( map, context ) );
		scan.add( new LoadStoreHandler( getMethodToLoadPropertyMap(), getMethodToStorePropertyMap() ) );
		scan.scanMembers();
	}
}
