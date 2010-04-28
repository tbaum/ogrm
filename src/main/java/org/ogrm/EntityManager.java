package org.ogrm;

import java.util.concurrent.locks.Lock;

import org.neo4j.graphdb.Transaction;

public interface EntityManager {

	public Transaction beginTransaction();
	
	public <T> T create( Class<T> type );

	public <T> T get( Class<T> type, Object id );

	public void remove( Object entity );

	public Lock getLock( Object entity);

	public <T> Search<T> find( Class<T> type );

	public void dispose();
}
