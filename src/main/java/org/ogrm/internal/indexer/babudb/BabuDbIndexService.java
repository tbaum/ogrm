package org.ogrm.internal.indexer.babudb;

import org.neo4j.graphdb.Node;
import org.neo4j.index.IndexHits;
import org.neo4j.index.IndexService;
import org.neo4j.index.Isolation;

public class BabuDbIndexService implements IndexService {

	private BabudbIndexProvider indexProvider;

	@SuppressWarnings("unused") //Not removed for future neo4j API compliance 
	private Isolation isolation;

	public BabuDbIndexService(BabudbIndexProvider indexProvider) {
		this.indexProvider = indexProvider;
	}

	@Override
	public IndexHits<Node> getNodes( String name, Object key ) {
		BabuDbIndex index = indexProvider.getIndex( name );
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
		BabuDbIndex index = indexProvider.getIndex( name );
		index.add( key.toString(), node );
	}

	@Override
	public void removeIndex( String name ) {
		BabuDbIndex index = indexProvider.getIndex( name );
		index.clear();

	}

	@Override
	public void removeIndex( Node node, String name ) {
		BabuDbIndex index = indexProvider.getIndex( name );
		index.removeSuffix( node.getId() );
	}

	@Override
	public void removeIndex( Node node, String name, Object key ) {
		BabuDbIndex index = indexProvider.getIndex( name );
		index.remove( key.toString(), node );
	}

	@Override
	public void setIsolation( Isolation isolation ) {
		this.isolation = isolation;
	}

	@Override
	public void shutdown() {
		indexProvider.dispose();
	}

}
