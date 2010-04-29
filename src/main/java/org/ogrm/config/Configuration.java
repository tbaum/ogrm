package org.ogrm.config;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.index.IndexService;
import org.ogrm.internal.graph.memory.MemoryGraph;
import org.ogrm.internal.indexer.memory.MemoryIndexService;
import org.ogrm.resource.DefaultResourceLoader;
import org.ogrm.resource.ResourceLoader;

public class Configuration {

	private ResourceLoader resourceLoader;

	private GraphDatabaseService graph;

	private IndexService indexService;

	public Configuration() {

	}

	public ResourceLoader getResourceLoader() {
		if (resourceLoader == null)
			return new DefaultResourceLoader();
		return resourceLoader;
	}

	public void setResourceLoader( ResourceLoader resourceLoader ) {
		this.resourceLoader = resourceLoader;
	}

	public IndexService getIndexService() {
		if (indexService == null)
			return indexService = new MemoryIndexService( this );
		return indexService;
	}

	public Configuration setIndexService( IndexService indexer ) {
		this.indexService = indexer;
		return this;
	}

	public GraphDatabaseService getGraph() {
		if (graph == null)
			return graph = new MemoryGraph();
		return graph;
	}

	public Configuration setGraph( GraphDatabaseService graph ) {
		this.graph = graph;
		return this;
	}

}
