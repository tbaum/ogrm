package org.ogrm;

import java.util.List;

public interface Search<T> {

	public List<T> list();

	public T first();

	public T unique();

}
