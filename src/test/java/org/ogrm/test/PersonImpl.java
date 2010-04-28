package org.ogrm.test;

import java.util.Collection;

import org.ogrm.TypedRelationBag;
import org.ogrm.annotations.FromMany;
import org.ogrm.annotations.Id;
import org.ogrm.annotations.Indexed;
import org.ogrm.annotations.Load;
import org.ogrm.annotations.Store;
import org.ogrm.annotations.ToMany;
import org.ogrm.annotations.ToOne;
import org.ogrm.annotations.Value;

public class PersonImpl implements Person {

	@Id
	private Object id;

	@Value
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

	@Override
	public Friendship createFriendshipTo( Person person ) {
		return friends.createAndAddRelationTo( person, FriendshipImpl.class );
	}

	@Override
	@Load( { "father" })
	public Person getFather() {
		return father;
	}

	@Override
	@Load( { "name" })
	@Indexed
	public String getName() {
		return name;
	}

	@Override
	public Collection<Friendship> getFriends() {
		return friends;
	}

	@Override
	public void removeFriendship( Friendship friendship ) {
		friends.remove( friendship );

	}

	@Override
	@Store( { "father" })
	public void setFather( Person father ) {
		this.father = father;
	}

	@Override
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

	@Override
	public void addEvent( LifeEvent event ) {
		events.add( event );
	}

	@Override
	public Collection<LifeEvent> getEvents() {
		return events;
	}

	public Object getId() {
		return id;
	}

	@Override
	public Collection<Friendship> getPersonsThatAreFirendsWithMe() {
		return friendOf;
	}
}
