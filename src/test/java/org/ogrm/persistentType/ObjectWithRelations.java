package org.ogrm.persistentType;

public interface ObjectWithRelations {

	public Object getId();
	
	public void setName( String name );

	public String getName();

	public void add( ObjectWithRelations other );

	public void remove( ObjectWithRelations other );

	public Iterable<ObjectWithRelations> getObjectWithRelations();

	public void set( ObjectWithRelations other );

	public ObjectWithRelations getOther();

}
