package org.ogrm.internal.graph.memory;

import java.util.TreeMap;

import org.neo4j.graphdb.PropertyContainer;

public class PropertyHolderSupport implements PropertyContainer {

	private TreeMap<String, Object> properties;

	public PropertyHolderSupport() {
		properties = new TreeMap<String, Object>();
	}

	@Override
	public Object getProperty( String arg0 ) {
		return properties.get( arg0 );
	}

	@Override
	public Object getProperty( String arg0, Object arg1 ) {
		Object value = properties.get( arg0 );
		return value == null ? arg1 : value;
	}

	@Override
	public Iterable<String> getPropertyKeys() {
		return properties.keySet();
	}

	@Override
	public Iterable<Object> getPropertyValues() {
		return properties.values();
	}

	@Override
	public boolean hasProperty( String arg0 ) {
		return properties.containsKey( arg0 );
	}

	@Override
	public Object removeProperty( String arg0 ) {
		return properties.remove( arg0 );
	}

	@Override
	public void setProperty( String arg0, Object arg1 ) {
		properties.put( arg0, arg1 );
	}

	
	public TreeMap<String, Object> getProperties() {
		return properties;
	}
}
