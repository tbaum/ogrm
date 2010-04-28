package org.ogrm.relation;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.ogrm.internal.context.PersistenceContext;

import com.bsc.commons.collections.Lists;

public class FromManySet<E> implements Set<E> {

	private PersistenceContext context;

	private RelationManager helper;


	public FromManySet(PersistenceContext context, Node node, String name) {
		this.context = context;
		this.helper = new RelationManager( name, node,Direction.INCOMING );
	}

	@Override
	public boolean add( E entity ) {
		Node otherNode = context.getNode( entity );
		helper.createOrReplaceRelationship( otherNode );
		return true;
	}

	@Override
	public boolean addAll( Collection<? extends E> entities ) {
		for (E entity : entities) {
			add( entity );
		}
		return true;
	}

	@Override
	public void clear() {
		helper.removeAllRelationships();
	}

	@Override
	public boolean contains( Object o ) {

		for (Relationship rel : helper.getRelationships()) {
			if (context.getNode( o ).getId() == rel.getEndNode().getId())
				return true;
		}
		return false;
	}

	@Override
	public boolean containsAll( Collection<?> objects ) {
		for (Object o : objects) {
			if (!contains( o ))
				return false;
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return !helper.getRelationships().iterator().hasNext();
	}

	@Override
	public Iterator<E> iterator() {
		return new RelatedEntitySetIterator<E>( context, helper.getRelationships().iterator() );
	}

	@Override
	public boolean remove( Object object ) {
		Node node = context.getNode( object );
		helper.removeRelationship( node );
		return true;
	}

	@Override
	public boolean removeAll( Collection<?> objects ) {
		for (Object o : objects) {
			if (remove( o ))
				return true;
		}
		return false;
	}

	@Override
	public boolean retainAll( Collection<?> arg0 ) {
		return false;
	}

	@Override
	public int size() {
		int counter = 0;

		for (@SuppressWarnings("unused")
		Relationship rel : helper.getRelationships()) {
			counter++;
		}
		return counter;
	}

	@Override
	public Object[] toArray() {
		int size = size();
		Object[] array = new Object[size];

		List<E> elements = Lists.newList( this );

		for (int i = 0; i < size; i++) {
			array[i] = elements.get( i );
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray( T[] arg0 ) {
		return (T[]) toArray();
	}

}
