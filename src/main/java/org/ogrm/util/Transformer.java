package org.ogrm.util;

public interface Transformer<F,T> {

	public T transform(F from);
	
}
