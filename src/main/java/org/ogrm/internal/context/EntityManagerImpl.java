package org.ogrm.internal.context;

import java.util.concurrent.locks.Lock;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.index.IndexService;
import org.ogrm.EntityManager;
import org.ogrm.config.Configuration;
import org.ogrm.internal.search.FindType;
import org.ogrm.search.Find;

public class EntityManagerImpl implements EntityManager {

	private GraphDatabaseService graph;

	private IndexService indexService;

	private PersistenceContext context;

	public EntityManagerImpl(Configuration config) {
		this.graph = config.getGraph();
		this.indexService = config.getIndexService();
		context = new PersistenceContextImpl( config );
	}

	@Override
	public Transaction beginTransaction() {
		return graph.beginTx();
	}

	@Override
	public <T> T create( Class<T> type ) {
		return (T) context.createNode( type );
	}

	@Override
	public <T> Find<T> find( Class<T> type ) {
		return new FindType<T>( type, indexService, context );
	}

	@Override
	public <T> T get( Class<T> type, Object id ) {
		Node node = graph.getNodeById( (Long) id );
		if (node == null)
			return null;
		Object entity = context.getEntity( node );
		return type.cast( entity );
	}

	@Override
	public void remove( Object entity ) {
		Node node = context.getNode( entity );
		node.delete();
	}

	@Override
	public void dispose() {
		graph.shutdown();
		indexService.shutdown();
	}

	public GraphDatabaseService getGraph() {
		return graph;
	}

	@Override
	public Lock getLock( Object entity ) {
		// TODO Auto-generated method stub
		return null;
	}

}
