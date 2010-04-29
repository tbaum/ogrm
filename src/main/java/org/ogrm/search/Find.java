package org.ogrm.search;

public interface Find<T> {

	public Is<T> where(String indexName);
	
}
