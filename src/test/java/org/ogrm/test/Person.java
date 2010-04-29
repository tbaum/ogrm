package org.ogrm.test;

import java.util.Collection;

import org.ogrm.TypedRelationBag;
import org.ogrm.annotations.FromMany;
import org.ogrm.annotations.Id;
import org.ogrm.annotations.Load;
import org.ogrm.annotations.Store;
import org.ogrm.annotations.ToMany;
import org.ogrm.annotations.ToOne;
import org.ogrm.annotations.Value;

public class Person {

	//All entities must have an id;
	@Id
	private Object id;

	//Values are mapped to the node, and can be indexed for easy retrieval
	@Value(index = Indexes.NAME)
	private String name;

	//Values can be converted to neo4j supported types, that are easier to read for humans too
	@Value(converter = InstantConverter.class)
	private Instant birthDate;

	//Typed relationships, must be contained in a special collection.
	@ToMany(typed = true)
	private TypedRelationBag<Friendship> friends;

	//Typed incoming relationships, managed by neo4j. Adding a friendship here automatically adds the friendship to the corresponding
	//friends collection of the other Person.
	@FromMany(typed = true, incoming = "friends")
	private TypedRelationBag<Friendship> friendOf;

	//Collection of entitites
	@ToMany
	private Collection<LifeEvent> events;

	public Friendship createFriendshipTo( Person person ) {
		return friends.createAndAddRelationTo( person, Friendship.class );
	}

	//Loads the field name before the call
	@Load( { "name" })
	public String getName() {
		return name;
	}

	public Collection<Friendship> getFriends() {
		return friends;
	}

	public void removeFriendship( Friendship friendship ) {
		friends.remove( friendship );

	}

	//Stores the field "name" after the call
	@Store( { "name" })
	public void setName( String name ) {
		this.name = name;
	}

	
	@Store( { "birthDate" })
	public void setBirthDate( Instant birthDate ) {
		this.birthDate = birthDate;
	}

	@Load( { "birthDate" })
	public Instant getBirthDate() {
		return birthDate;
	}

	public void addEvent( LifeEvent event ) {
		events.add( event );
	}

	public Collection<LifeEvent> getEvents() {
		return events;
	}

	public Object getId() {
		return id;
	}

	public Collection<Friendship> getPersonsThatAreFirendsWithMe() {
		return friendOf;
	}
}
