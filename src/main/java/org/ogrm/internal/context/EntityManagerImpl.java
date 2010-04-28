package org.ogrm.internal.context;

import java.util.concurrent.locks.Lock;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.ogrm.EntityManager;
import org.ogrm.Search;
import org.ogrm.config.Configuration;
import org.ogrm.index.IndexProvider;
import org.ogrm.internal.graph.Graph;

public class EntityManagerImpl implements EntityManager {

	private Graph graph;

	private IndexProvider indexProvider;

	private PersistenceContext context;

	public EntityManagerImpl(Configuration config) {
		this.graph = config.getGraph();
		this.indexProvider = config.getIndexer();
		context = new PersistenceContextImpl( config );
	}

	@Override
	public Transaction beginTransaction() {
		return graph.getAndBeginTx();
	}

	@Override
	public <T> T create( Class<T> type ) {
		return (T) context.createNode( type );
	}

	@Override
	public <T> Search<T> find( Class<T> type ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T get( Class<T> type, Object id ) {
		Node node = graph.getNodeById( id );
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
		graph.dispose();
		indexProvider.dispose();
	}

	@Override
	public Lock getLock( Object entity ) {
		// TODO Auto-generated method stub
		return null;
	}

}
