package org.ogrm.internal.indexer.memory;

import java.util.List;
import java.util.Map;



import org.neo4j.graphdb.Node;
import org.ogrm.index.Index;
import org.ogrm.internal.graph.Graph;

import com.bsc.commons.collections.Lists;
import com.bsc.commons.collections.Maps;

public class MemoryIndex implements Index {

	private Graph graph;

	private Map<String, Long> index;

	public MemoryIndex(Graph graph) {
		this.graph = graph;
		this.index = Maps.newMap();
	}

	@Override
	public void add( String key, Node node ) {
		index.put( key, node.getId() );
	}

	@Override
	public Node get( String key ) {
		if (index.get( key ) == null)
			return null;
		return graph.getNodeById( index.get( key ) );
	}

	@Override
	public Iterable<Node> getPrefix( String prefix ) {
		List<Node> list = Lists.newList();
		for (Map.Entry<String, Long> entry : index.entrySet()) {
			if (entry.getKey().startsWith( prefix ))
				list.add( graph.getNodeById( entry.getValue() ) );

		}
		return list;
	}

	@Override
	public void remove( String key ) {
		index.remove( key );
	}

}
