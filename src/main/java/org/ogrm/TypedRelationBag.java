package org.ogrm;

import java.util.Collection;

/**
 * A set that cannot be added to. Use the createAndAddRelationTo() method to add relations to this set.
 * @author prange
 *
 * @param <T>
 */
public interface TypedRelationBag<T> extends Collection<T>{

	public <E extends T> E createAndAddRelationTo(Object other,Class<E> type);
	
}
