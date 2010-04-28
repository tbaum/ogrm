package org.ogrm;

public interface Relation<T> {

	public void set(T related);
	
	public T get();
	
}
