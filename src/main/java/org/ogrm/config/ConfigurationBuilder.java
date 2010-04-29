package org.ogrm.config;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.ogrm.index.Indexer;
import org.ogrm.internal.graph.memory.MemoryGraph;
import org.ogrm.internal.indexer.babudb.BabuDbIndexService;
import org.ogrm.internal.indexer.babudb.BabudbIndexProvider;
import org.ogrm.internal.indexer.memory.MemoryIndexService;

public class ConfigurationBuilder {

	private Configuration configuration;

	public static ConfigurationBuilder create() {
		return new ConfigurationBuilder();
	}

	public ConfigurationBuilder() {
		configuration = new Configuration();
	}

	public ConfigurationBuilder inMemoryGraph() {
		configuration.setGraph( new MemoryGraph() );
		return this;
	}

	public ConfigurationBuilder neo4jGraph( String location ) {
		GraphDatabaseService db = new EmbeddedGraphDatabase( location );
		configuration.setGraph( db );
		return this;
	}

	public ConfigurationBuilder memoryIndex() {
		configuration.setIndexService( new MemoryIndexService( configuration ) );
		return this;
	}

	public ConfigurationBuilder babuDbIndex( String location ) {
		configuration.setIndexService( new BabuDbIndexService( BabudbIndexProvider
				.createIndex( configuration, location ) ) );
		return this;
	}

	public ConfigurationBuilder overwriteBabuDBIndex( String location ) {
		configuration.setIndexService( new BabuDbIndexService( BabudbIndexProvider.createAndOverwriteIndex(
				configuration, location ) ) );
		return this;
	}

	public Configuration get() {
		
		Indexer indexer = new Indexer( configuration.getIndexService() );
		configuration.getGraph().registerTransactionEventHandler( indexer );
		
		return configuration;
	}
}
