package org.ogrm.search;

import java.util.List;


public interface Result<T> {

	public List<T> list();

	public T first();

	public T unique();
	
	public Result<T> filter(Predicate<T> predicate);

}
