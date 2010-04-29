package org.ogrm.internal.search;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.index.IndexHits;
import org.neo4j.index.IndexService;
import org.ogrm.internal.context.PersistenceContext;
import org.ogrm.search.Is;
import org.ogrm.search.Result;
import org.ogrm.util.Lists;

public class IsType<T> implements Is<T> {

	private String name;

	private IndexService indexService;

	private Class<T> type;

	private PersistenceContext context;

	public IsType(String name, IndexService indexService, Class<T> type, PersistenceContext context) {
		this.name = name;
		this.indexService = indexService;
		this.type = type;
		this.context = context;
	}

	@Override
	public Result<T> is( Object value ) {
		IndexHits<Node> hits = indexService.getNodes( name, value );

		List<T> result = Lists.newList();

		for (Node node : hits) {
			result.add( type.cast( context.getEntity( node ) ) );
		}
		return new ResultType<T>( result );
	}

}
