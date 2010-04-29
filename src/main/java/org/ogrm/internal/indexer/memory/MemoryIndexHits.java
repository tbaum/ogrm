package org.ogrm.internal.indexer.memory;

import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.index.IndexHits;

public class MemoryIndexHits  implements IndexHits<Node>{

	private int size;
	
	private Iterator<Node> iterator;
	
	
	public MemoryIndexHits(List<Node> hits) {
		this.size = hits.size();
		this.iterator = hits.iterator();
	}

	@Override
	public void close() {
		
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Node next() {
		return iterator.next();
	}

	@Override
	public void remove() {
		
	}

	@Override
	public Iterator<Node> iterator() {
		return this;
	}
	
	
	
}
