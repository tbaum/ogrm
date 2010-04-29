package org.ogrm.internal.search;

import org.neo4j.index.IndexService;
import org.ogrm.internal.context.PersistenceContext;
import org.ogrm.search.Find;
import org.ogrm.search.Is;

public class FindType<T> implements Find<T> {

	private Class<T> type;

	private IndexService indexService;

	private PersistenceContext context;

	public FindType(Class<T> type, IndexService indexService, PersistenceContext context) {
		this.type = type;
		this.context = context;
		this.indexService = indexService;
	}

	@Override
	public Is<T> where( String indexName ) {
		return new IsType<T>( indexName, indexService, type, context );
	}

}
