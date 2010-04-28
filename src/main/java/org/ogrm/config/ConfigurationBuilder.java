package org.ogrm.config;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.ogrm.internal.graph.memory.MemoryGraph;
import org.ogrm.internal.graph.neo4j.Neo4jGraph;
import org.ogrm.internal.indexer.babudb.BabudbIndexProvider;
import org.ogrm.internal.indexer.memory.MemoryIndexProvider;

public class ConfigurationBuilder {

	private Configuration configuration;

	public static ConfigurationBuilder createConfig() {
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
		configuration.setGraph( new Neo4jGraph( db ) );
		return this;
	}

	public ConfigurationBuilder memoryIndex() {
		configuration.setIndexer( new MemoryIndexProvider( configuration ) );
		return this;
	}

	public ConfigurationBuilder diskIndex( String location ) {
		configuration.setIndexer( BabudbIndexProvider.createIndex( configuration, location ) );
		return this;
	}

	public ConfigurationBuilder overwriteDiskIndex( String location ) {
		configuration.setIndexer( BabudbIndexProvider.createAndOverwriteIndex( configuration, location ) );
		return this;
	}

	public Configuration get() {
		return configuration;
	}
}
