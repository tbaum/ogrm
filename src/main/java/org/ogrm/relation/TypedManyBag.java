package org.ogrm.relation;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.ogrm.TypedRelationBag;
import org.ogrm.internal.context.PersistenceContext;
import org.ogrm.internal.proxy.ContainerWrapper;
import org.ogrm.util.Lists;

public class TypedManyBag<E> implements TypedRelationBag<E> {
	private RelationManager helper;

	private PersistenceContext context;

	public TypedManyBag(PersistenceContext context, Node node, String name, Direction direction) {
		this.context = context;
		this.helper = new RelationManager( name, node, direction );
	}

	@Override
	public boolean add( E entity ) {
		throw new UnsupportedOperationException(
				"Cannot add to a typed relation collection, use createAndAddRelationT() instead" );
	}

	@Override
	public <T extends E> T createAndAddRelationTo( Object other, Class<T> type ) {
		Node otherNode = context.getNode( other );
		Relationship rel = helper.createOrReplaceRelationship( otherNode );
		return context.createTypedRelationship( rel, type );
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
		if (o instanceof ContainerWrapper<?>) {
			ContainerWrapper<?> wrapper = (ContainerWrapper<?>) o;
			for (Relationship rel : helper.getRelationships()) {
				Object container = wrapper.getContainer();
				if (container instanceof Relationship) {
					Relationship orel = (Relationship) container;
					if (rel.equals( orel ))
						return true;
				}
			}
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
		return helper.getRelationships().iterator().hasNext();
	}

	@Override
	public Iterator<E> iterator() {
		return new RelatedEntitySetIterator( context, helper.getRelationships().iterator() );
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

	class RelatedEntitySetIterator implements Iterator<E> {

		private PersistenceContext context;

		private Iterator<Relationship> relations;

		public RelatedEntitySetIterator(PersistenceContext context, Iterator<Relationship> relations) {
			this.context = context;
			this.relations = relations;
		}

		@Override
		public boolean hasNext() {
			return relations.hasNext();
		}

		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			Relationship next = relations.next();
			return (E) context.getEntity( next.getEndNode() );
		}

		@Override
		public void remove() {
			relations.remove();
		}
	}
}
