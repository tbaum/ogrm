package org.ogrm;

public interface TypedRelation<T> {

	public <E extends T> E createAndSetRelationTo( Object entity, Class<E> type );

	public T get();

}
