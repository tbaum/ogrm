package org.ogrm.internal.indexer.memory;

import java.util.Map;

import org.ogrm.config.Configuration;
import org.ogrm.index.Index;
import org.ogrm.index.IndexProvider;

import com.bsc.commons.collections.Maps;

public class MemoryIndexProvider implements IndexProvider {

	private Map<String, MemoryIndex> indexes;
	private Configuration config;

	public MemoryIndexProvider(Configuration config) {
		this.config = config;
		indexes = Maps.newMap();
	}

	@Override
	public Index getIndex( String name ) {
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

	@Override
	public void dispose() {
		
	}

}
