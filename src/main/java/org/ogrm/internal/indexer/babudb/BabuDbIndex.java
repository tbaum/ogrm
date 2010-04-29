package org.ogrm.internal.indexer.babudb;

import java.util.Iterator;
import java.util.Map.Entry;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.index.IndexHits;
import org.ogrm.PersistenceException;
import org.ogrm.util.ByteHelper;
import org.xtreemfs.babudb.BabuDBException;
import org.xtreemfs.babudb.lsmdb.BabuDBInsertGroup;
import org.xtreemfs.babudb.lsmdb.Database;

public class BabuDbIndex {

	private Database index;

	private GraphDatabaseService graph;

	public BabuDbIndex(Database index, GraphDatabaseService graph) {
		this.index = index;
		this.graph = graph;
	}

	public IndexHits<Node> getPrefix( String type ) {
		try {
			final Iterator<Entry<byte[], byte[]>> wrappedIterator = index.prefixLookup( 0, type.getBytes(), null )
					.get();

			return new BabudbIndexHits( graph, wrappedIterator, 0 );

		} catch (BabuDBException e) {
			throw new PersistenceException( "Could not load prefixes", e );
		}

	}

	public void add( String key, Node node ) {
		try {

			byte[] bytes = (key+node.getId()).getBytes(); //toIndexKey( key, node.getId() );
			byte[] value = new Long( node.getId() ).toString().getBytes();
			index.singleInsert( 0, bytes, value, null ).get();

		} catch (Exception e) {

			throw new PersistenceException( "Could not store " + key, e );

		}

	}

	public void remove( String key, Node node ) {
		try {
			byte[] bytes = (key+node.getId()).getBytes(); //toIndexKey( key, node.getId() );
			index.singleInsert( 0, bytes, null, null ).get();
		} catch (BabuDBException e1) {
			throw new PersistenceException( "Could not remove " + key, e1 );
		}

	}

	public void removePrefix( String key ) {
		try {
			byte[] bytes = key.getBytes();

			index.singleInsert( 0, bytes, null, null ).get();
		} catch (BabuDBException e1) {
			throw new PersistenceException( "Could not remove " + key, e1 );
		}
	}

	public void removeSuffix( long id ) {
		byte[] idBytes = ByteHelper.makeByteFromLong( id );

		try {
			final Iterator<Entry<byte[], byte[]>> wrappedIterator = index.prefixLookup( 0, new byte[0], null ).get();

			BabuDBInsertGroup ig = index.createInsertGroup();

			while (wrappedIterator.hasNext()) {
				Entry<byte[], byte[]> entry = wrappedIterator.next();

				if (entry.getValue().equals( idBytes ))
					ig.addDelete( 0, entry.getKey() );
			}

			index.insert( ig, null ).get();
		} catch (BabuDBException e) {
			throw new PersistenceException( "Could not load prefixes", e );
		}
	}

	public void clear() {
		try {
			final Iterator<Entry<byte[], byte[]>> wrappedIterator = index.prefixLookup( 0, new byte[0], null ).get();

			BabuDBInsertGroup ig = index.createInsertGroup();

			while (wrappedIterator.hasNext()) {
				Entry<byte[], byte[]> entry = wrappedIterator.next();
				ig.addDelete( 0, entry.getKey() );
			}

			index.insert( ig, null ).get();
		} catch (BabuDBException e) {
			throw new PersistenceException( "Could not load prefixes", e );
		}
	}

	private byte[] toIndexKey( String key, long id ) {
		byte[] keyBytes = key.getBytes();
		byte[] idBytes = ByteHelper.makeByteFromLong( id );

		byte[] indeKeyBytes = new byte[keyBytes.length + idBytes.length];

		System.arraycopy( keyBytes, 0, indeKeyBytes, 0, keyBytes.length );
		System.arraycopy( idBytes, 0, indeKeyBytes, keyBytes.length, idBytes.length );

		return indeKeyBytes;
	}

}
