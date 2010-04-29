package org.ogrm.test;

import java.util.Collection;

import org.ogrm.test.OgrmTestSupport;
import org.testng.annotations.Test;

public class PersonTest extends OgrmTestSupport {

	private String theNameOfNeo = "Neo";
	private Object idOfTheStar = null;

	@Test
	public void createStar() {
		Person neo = create( Person.class );

		neo.setName( theNameOfNeo );

		idOfTheStar = neo.getId();

		commit();

		neo = manager().get( Person.class, idOfTheStar );

		assert neo.getName().equals( theNameOfNeo );
	}

	@Test(dependsOnMethods = { "createStar" })
	public void addEventsOfHisLife() {
		Person neo = manager().get( Person.class, idOfTheStar );

		LifeEvent birth = create( LifeEventImpl.class );
		birth.setType( "birth" );
		// Not sure of the real date here...
		birth.setTimestamp( Instant.at( 2002, 4, 10 ) );
		neo.addEvent( birth );

		LifeEvent discoveryOfTheMatrix = create( LifeEventImpl.class );
		discoveryOfTheMatrix.setType( "discoveryOfTheMatrix" );
		discoveryOfTheMatrix.setTimestamp( Instant.at( 2012, 2, 21 ) );
		neo.addEvent( discoveryOfTheMatrix );

		commit();

		neo = manager().get( Person.class, idOfTheStar );

		Collection<LifeEvent> events = neo.getEvents();

		assert events.size() == 2;

		assert events.contains( birth );
		assert events.contains( discoveryOfTheMatrix );

	}

	@Test(dependsOnMethods = { "addEventsOfHisLife" })
	public void addFriends() {
		Person neo = manager().get( Person.class, idOfTheStar );

		Person trinity = create( Person.class );
		trinity.setName( "Trinity" );

		Friendship friendshipWithTrinity = neo.createFriendshipTo( trinity );

		assert trinity.getPersonsThatAreFirendsWithMe().contains( friendshipWithTrinity );

		assert friendshipWithTrinity.getSelf().equals( neo );

		assert friendshipWithTrinity.getFriend().equals( trinity );
	}

	@Test(dependsOnMethods = { "addFriends" })
	public void searchForPersons() {
		Person neo = manager().find( Person.class ).where( Indexes.NAME ).is( theNameOfNeo ).first();

		assert neo != null;
	}
}
