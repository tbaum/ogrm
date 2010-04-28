package org.ogrm.persistentType;

import org.ogrm.annotations.Id;
import org.ogrm.annotations.Load;
import org.ogrm.annotations.Store;
import org.ogrm.annotations.Value;

public class SimpleTestObjectImpl implements SimpleTestObject {

	@Id
	private Object id;

	@Value
	private String name;

	@Override
	public Object getId() {
		return id;
	}

	@Override
	@Load({"name"})
	public String getName() {
		return name;
	}

	@Override
	@Store( { "name" })
	public void setName( String name ) {
		this.name = name;
	}

}
