package org.ogrm.index;

import org.neo4j.graphdb.Node;

public interface Index {

	public void add( String key, Node node );

	public Node get( String key );

	public Iterable<Node> getPrefix( String prefix );

	public void remove(String key);
}
