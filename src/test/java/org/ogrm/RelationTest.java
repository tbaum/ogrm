package org.ogrm;

import org.ogrm.test.ObjectWithRelations;
import org.ogrm.test.ObjectWithRelationsImpl;
import org.ogrm.test.OgrmTestSupport;
import org.testng.annotations.Test;


public class RelationTest extends OgrmTestSupport {

	private Object idOne;
	private Object idOther;

	@Test
	public void create() {
		ObjectWithRelations one = create( ObjectWithRelationsImpl.class );

		ObjectWithRelations other = create( ObjectWithRelationsImpl.class );

		idOne = one.getId();

		idOther = other.getId();

		one.set( other );
	}

	@Test(dependsOnMethods = { "create" })
	public void getOther() {
		ObjectWithRelations one = manager().get( ObjectWithRelations.class, idOne );

		Object otherId = one.getOther().getId();
		
		assert idOther.equals( otherId );
		
	}
}
