package org.ogrm.internal.context;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.ogrm.config.Configuration;

public interface PersistenceContext {

	public <T> T createTypedRelationship( Relationship relationship, Class<T> classType );

	public <T> T createNode( Class<T> type );

	public Node getNode( Object entity );

	public Object getEntity( Node node );

	public Object getTypedRelation( Relationship relationship );

	public Configuration getConfiguration();
}
