package org.ogrm.index;

import org.apache.commons.lang.StringUtils;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.event.PropertyEntry;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.graphdb.event.TransactionEventHandler;
import org.neo4j.index.IndexService;
import org.ogrm.annotations.Value;

public class Indexer implements TransactionEventHandler<Object> {

	private IndexService indexService;

	public Indexer(IndexService indexService) {
		this.indexService = indexService;
	}

	@Override
	public void afterCommit( TransactionData data, Object context ) {

	

	}

	@Override
	public void afterRollback( TransactionData data, Object node ) {
		System.out.println( "Indexer.afterRollback " );
	}

	@Override
	public Object beforeCommit( TransactionData data ) throws Exception {
		
		//TODO, this must be in the afterCommt method, but is here due to a bug in neo4j. Move up when bug is fixed
		for (PropertyEntry<Node> entry : data.assignedNodeProperties()) {
			String key = entry.key();

			if (isIndexed( key )) {
				String name = getIndexName( key );

				if (entry.previouslyCommitedValue() != null)
					indexService.removeIndex( entry.entity(), name, entry.previouslyCommitedValue() );

				if (entry.value() != null)
					indexService.index( entry.entity(), name, entry.value() );

			}

		}
		return new Object();
	}

	public static String createPropertyName( String realName, String indexName ) {
		if (indexName.equals( Value.NO_INDEX ))
			return realName;
		return new StringBuilder().append( realName ).append( ":index=" ).append( indexName ).toString();
	}

	public static String getIndexName( String fullName ) {
		if (isIndexed( fullName ))
			return StringUtils.substringAfter( fullName, "=" );
		return fullName;
	}

	public static boolean isIndexed( String fullName ) {
		return fullName.contains( ":index=" );
	}
}
