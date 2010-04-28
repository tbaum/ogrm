package org.ogrm.internal.wrap;

import java.util.Map;

import org.neo4j.graphdb.Node;
import org.ogrm.internal.context.PersistenceContext;
import org.ogrm.internal.handler.IdHandler;
import org.ogrm.internal.handler.LoadStoreHandler;
import org.ogrm.internal.handler.RelationHandler;
import org.ogrm.internal.handler.Synchronizer;
import org.ogrm.internal.handler.ValueHandler;
import org.ogrm.util.Scan;


public class NodeTypeWrapper extends ReflectionTypeWrapper<Node> {

	private PersistenceContext context;

	public NodeTypeWrapper(Class<?> c, PersistenceContext context) {
		super( c );
		this.context = context;
	}

	protected void scan( Class<?> type ) {
		Map<String, Synchronizer<Node>> map = getSynchronizers();
		Scan scan = new Scan( type );
		scan.add( new ValueHandler<Node>( map ) );
		scan.add( new IdHandler( map ) );
		scan.add( new RelationHandler( map, context ) );
		scan.add( new LoadStoreHandler( getMethodToLoadPropertyMap(), getMethodToStorePropertyMap() ) );
		scan.scanMembers();
	}
}
