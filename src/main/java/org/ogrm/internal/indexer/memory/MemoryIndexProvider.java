package org.ogrm.internal.indexer.memory;

import java.util.Map;

import org.ogrm.config.Configuration;
import org.ogrm.util.Maps;

public class MemoryIndexProvider {

	private Map<String, MemoryIndex> indexes;
	private Configuration config;

	public MemoryIndexProvider(Configuration config) {
		this.config = config;
		indexes = Maps.newMap();
	}

	public MemoryIndex getIndex( String name ) {
		MemoryIndex index = indexes.get( name );
		if (index == null) {
			synchronized (indexes) {
				if (indexes.get( name ) == null) {
					index = new MemoryIndex( config.getGraph() );
					indexes.put( name, index );
				}
			}
		}
		return index;
	}

	public void dispose() {
		
	}

}
