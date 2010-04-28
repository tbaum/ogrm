package org.ogrm.internal.proxy;

import org.neo4j.graphdb.PropertyContainer;

public interface ContainerWrapper<T extends PropertyContainer> {

	public T getContainer();

}
