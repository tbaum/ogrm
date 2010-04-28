package org.ogrm;


import org.ogrm.persistentType.SimpleTestObject;
import org.ogrm.persistentType.SimpleTestObjectImpl;
import org.ogrm.test.OgrmTestSupport;
import org.testng.annotations.Test;

public class SimpleCRUDTest extends OgrmTestSupport {

	private Object id;
	private String name = "name1";

	@Test
	public void store() {
		SimpleTestObject so = create( SimpleTestObjectImpl.class );
		so.setName( name );
		id = so.getId();
	}

	@Test(dependsOnMethods = "store")
	public void load() {
		SimpleTestObject so = manager().get( SimpleTestObject.class, id );
		assert so.getName().equals( name );
	}

	@Test(dependsOnMethods = "load")
	public void delete() {
		SimpleTestObject so = manager().get( SimpleTestObject.class, id );
		manager().remove( so );
		so = manager().get( SimpleTestObject.class, id );

		assert so == null;
	}

}
