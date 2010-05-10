package org.ogrm.internal.indexer.memory;

import org.neo4j.graphdb.Node;
import org.neo4j.index.IndexHits;
import org.neo4j.index.IndexService;
import org.neo4j.index.Isolation;
import org.ogrm.config.Configuration;

public class MemoryIndexService implements IndexService {

	private MemoryIndexProvider indexProvider;

	private Isolation isolation = Isolation.SAME_TX;
	
	public MemoryIndexService(Configuration config) {
		indexProvider = new MemoryIndexProvider( config );
	}

	@Override
	public IndexHits<Node> getNodes( String name, Object key ) {
		MemoryIndex index = indexProvider.getIndex( name );
		return index.getPrefix( key.toString() );
	}

	@Override
	public Node getSingleNode( String name, Object key ) {
		IndexHits<Node> hits = getNodes( name, key );
		if (hits.hasNext())
			return hits.next();

		return null;
	}

	@Override
	public void index( Node node, String name, Object key ) {
		MemoryIndex index = indexProvider.getIndex( name );
		index.add( key.toString(), node );
	}

	@Override
	public void removeIndex( String name ) {
		MemoryIndex index = indexProvider.getIndex( name );
		index.clear();

	}

	@Override
	public void removeIndex( Node node, String name ) {
		MemoryIndex index = indexProvider.getIndex( name );
		index.removeNode( node );

	}

	@Override
	public void removeIndex( Node node, String name, Object key ) {
		MemoryIndex index = indexProvider.getIndex( name );
		index.remove( key.toString() );
	}

	@Override
	public void setIsolation( Isolation isolation ) {
		this.isolation = isolation;

	}

	@Override
	public void shutdown() {

	}

}
