package org.ogrm.test;

import org.neo4j.graphdb.Transaction;
import org.ogrm.EntityManager;
import org.ogrm.config.ConfigurationBuilder;
import org.ogrm.internal.context.EntityManagerImpl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class OgrmTestSupport {

	private EntityManager ctx;

	private Transaction tx;

	@BeforeClass
	public void setup() {
		ctx = new EntityManagerImpl( ConfigurationBuilder.createConfig().get() );
	}

	@AfterClass
	public void teardown() {
		ctx.dispose();
	}

	@BeforeMethod
	public void beginTx() {
		tx = ctx.beginTransaction();
	}

	@AfterMethod
	public void endTx() {
		tx.success();
	}

	public EntityManager manager() {
		return ctx;
	}

	protected <T> T create( Class<T> type ) {
		return manager().create( type );
	}

	protected void commit(){
		tx.success();
		tx.finish();
		beginTx();
	}
	
	protected void abort(){
		tx.failure();
		tx.finish();
		beginTx();
	}
}
