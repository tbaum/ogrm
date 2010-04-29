package org.ogrm.search;

public interface Predicate<T> {

	public boolean evaluate( T value );

}
