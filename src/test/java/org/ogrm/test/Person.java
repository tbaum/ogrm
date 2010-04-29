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

	@Id
	private Object id;

	@Value(index = Indexes.NAME)
	private String name;

	@Value(converter = InstantConverter.class)
	private Instant birthDate;

	@ToOne
	private Person father;

	@ToMany(typed = true)
	private TypedRelationBag<Friendship> friends;

	@FromMany(typed = true, incoming = "friends")
	private TypedRelationBag<Friendship> friendOf;

	@ToMany
	private Collection<LifeEvent> events;

	public Friendship createFriendshipTo( Person person ) {
		return friends.createAndAddRelationTo( person, Friendship.class );
	}

	@Load( { "father" })
	public Person getFather() {
		return father;
	}

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

	@Store( { "father" })
	public void setFather( Person father ) {
		this.father = father;
	}

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
