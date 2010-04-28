package org.ogrm.internal.indexer.babudb;

import java.util.Iterator;
import java.util.Map.Entry;


import org.neo4j.graphdb.Node;
import org.ogrm.PersistenceException;
import org.ogrm.index.Index;
import org.ogrm.internal.graph.Graph;
import org.ogrm.util.ByteHelper;
import org.xtreemfs.babudb.BabuDBException;
import org.xtreemfs.babudb.lsmdb.Database;

public class BabuDbIndex implements Index {

	private Database index;

	private Graph graph;

	public BabuDbIndex(Database index, Graph graph) {
		this.index = index;
		this.graph = graph;
	}
	
	@Override
	public Iterable<Node> getPrefix( String type ) {
		String partition = type;
		try {
			final Iterator<Entry<byte[], byte[]>> wrappedIterator = index.prefixLookup( 0, partition.getBytes(), null )
					.get();

			final Iterator<Node> nodeIter = new Iterator<Node>() {

				@Override
				public boolean hasNext() {
					return wrappedIterator.hasNext();
				}

				@Override
				public Node next() {
					return graph.getNodeById( ByteHelper.makeLongFromByte( wrappedIterator.next().getValue() ) );
				}

				@Override
				public void remove() {
				}
			};

			return new Iterable<Node>() {

				@Override
				public Iterator<Node> iterator() {
					return nodeIter;
				}
			};
		} catch (BabuDBException e) {
			throw new PersistenceException( "Could not load prefixes", e );
		}

	}

	@Override
	public Node get( String key ) {

		try {
			byte[] idAsBytes = index.lookup( 0, key.getBytes(), null ).get();
			return graph.getNodeById( ByteHelper.makeLongFromByte( idAsBytes ) );
		} catch (BabuDBException e) {

			throw new PersistenceException( "Could not lookup " + key, e );

		}
	}

	@Override
	public void add( String key, Node node ) {
		try {

			byte[] bytes = ByteHelper.makeByteFromLong( node.getId() );

			index.singleInsert( 0, key.getBytes(), bytes, null ).get();

		} catch (Exception e) {

			throw new PersistenceException( "Could not store " + key, e );

		}

	}

	@Override
	public void remove( String key ) {
		try {
			index.singleInsert( 0, key.getBytes(), null, null ).get();
		} catch (BabuDBException e1) {
			throw new PersistenceException( "Could not remove " + key, e1 );
		}

	}

}
