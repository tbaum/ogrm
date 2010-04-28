/**
 * 
 */
package org.ogrm.relation;

import java.util.Iterator;

import org.neo4j.graphdb.Relationship;
import org.ogrm.internal.context.PersistenceContext;

class RelatedEntitySetIterator<E> implements Iterator<E> {

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