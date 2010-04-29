package org.ogrm.internal.indexer.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.index.IndexHits;
import org.ogrm.util.Lists;
import org.ogrm.util.Maps;

public class MemoryIndex {

	private GraphDatabaseService graph;

	private Map<String, Long> index;

	public MemoryIndex(GraphDatabaseService graph) {
		this.graph = graph;
		this.index = Maps.newMap();
	}

	public void add( String key, Node node ) {
		index.put( key, node.getId() );
	}

	public Node get( String key ) {
		if (index.get( key ) == null)
			return null;
		return graph.getNodeById( index.get( key ) );
	}

	public IndexHits<Node> getPrefix( String prefix ) {
		List<Node> list = Lists.newList();
		for (Map.Entry<String, Long> entry : index.entrySet()) {
			if (entry.getKey().startsWith( prefix ))
				list.add( graph.getNodeById( entry.getValue() ) );

		}
		return new MemoryIndexHits( list );
	}

	public void clear() {
		index.clear();
	}

	public void remove( String key ) {
		index.remove( key );
	}

	public void removeNode( Node node ) {
		for (Map.Entry<String, Long> entry : new ArrayList<Map.Entry<String, Long>>( index.entrySet() )) {
			
			if(entry.getValue().equals( node.getId() ))
				index.remove( entry.getKey() );
			
		}
	}
}
