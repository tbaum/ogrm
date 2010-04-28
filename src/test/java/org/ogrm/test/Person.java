package org.ogrm.test;

import java.util.Collection;

public interface Person {

	public Object getId();
	
	public void setName(String name);
	
	public String getName();
	
	public void setFather(Person father);
	
	public Person getFather();
	
	public Friendship createFriendshipTo(Person person);
	
	
	public void removeFriendship(Friendship friendship);
	
	public Collection<Friendship>  getFriends();

	public Collection<Friendship> getPersonsThatAreFirendsWithMe();
	
	public void addEvent(LifeEvent event);
	
	public Collection<LifeEvent> getEvents();
	
}
