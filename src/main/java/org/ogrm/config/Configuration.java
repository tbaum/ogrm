package org.ogrm.config;

import org.ogrm.index.IndexProvider;
import org.ogrm.internal.graph.Graph;
import org.ogrm.internal.graph.memory.MemoryGraph;
import org.ogrm.internal.indexer.memory.MemoryIndexProvider;
import org.ogrm.resource.DefaultResourceLoader;
import org.ogrm.resource.ResourceLoader;

public class Configuration {

	private ResourceLoader resourceLoader;

	
	private Graph graph;

	private IndexProvider indexer;

	public Configuration() {

	}

	public ResourceLoader getResourceLoader() {
		if(resourceLoader == null)
			return new DefaultResourceLoader();
		return resourceLoader;
	}
	
	public void setResourceLoader( ResourceLoader resourceLoader ) {
		this.resourceLoader = resourceLoader;
	}

	public IndexProvider getIndexer() {
		if (indexer == null)
			return indexer = new MemoryIndexProvider( this );
		return indexer;
	}

	public Configuration setIndexer( IndexProvider indexer ) {
		this.indexer = indexer;
		return this;
	}

	public Graph getGraph() {
		if (graph == null)
			return graph = new MemoryGraph();
		return graph;
	}

	public Configuration setGraph( Graph graph ) {
		this.graph = graph;
		return this;
	}
	
}
