package org.ogrm.test;

import java.util.Set;

import org.ogrm.annotations.Id;
import org.ogrm.annotations.Load;
import org.ogrm.annotations.Store;
import org.ogrm.annotations.ToMany;
import org.ogrm.annotations.ToOne;
import org.ogrm.annotations.Value;

public class ObjectWithRelationsImpl implements ObjectWithRelations {

	@Id
	private Object id;

	@Value
	private String name;

	@ToMany
	private Set<ObjectWithRelations> related;

	@ToOne
	private ObjectWithRelations other;

	@Override
	public Object getId() {
		return id;
	}

	@Override
	public void add( ObjectWithRelations other ) {
		related.add( other );
	}

	@Override
	@Load({"name"})
	public String getName() {
		return name;
	}

	@Override
	public Iterable<ObjectWithRelations> getObjectWithRelations() {
		return related;
	}

	@Override
	@Load({"other"})
	public ObjectWithRelations getOther() {
		return other;
	}

	@Override
	@Store({"other"})
	public void remove( ObjectWithRelations other ) {
		related.remove( other );
	}

	@Override
	@Store({"other"})
	public void set( ObjectWithRelations other ) {
		this.other = other;
	}

	@Store( { "name" })
	public void setName( String name ) {
		this.name = name;

	}

}
