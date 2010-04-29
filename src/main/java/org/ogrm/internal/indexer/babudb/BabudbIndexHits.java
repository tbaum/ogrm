package org.ogrm.internal.indexer.babudb;

import java.util.Iterator;
import java.util.Map.Entry;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.index.IndexHits;
import org.ogrm.util.ByteHelper;

public class BabudbIndexHits implements IndexHits<Node> {

	private GraphDatabaseService graphService;

	Iterator<Entry<byte[], byte[]>> entryIterator;

	public BabudbIndexHits(GraphDatabaseService graphService, Iterator<Entry<byte[], byte[]>> entryIterator, int size) {
		this.graphService = graphService;
		this.entryIterator = entryIterator;
	}

	@Override
	public void close() {

	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean hasNext() {
		return entryIterator.hasNext();
	}

	@Override
	public Node next() {
		return graphService.getNodeById( getId( entryIterator.next() ) );
	}

	@Override
	public void remove() {

	}

	@Override
	public Iterator<Node> iterator() {
		return this;
	}

	private long getId( Entry<byte[], byte[]> entry ) {
		long id = Long.parseLong( new String( entry.getValue() ) );
		return id;
	}

}
