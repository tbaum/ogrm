package org.ogrm.test;

import org.ogrm.annotations.From;
import org.ogrm.annotations.Id;
import org.ogrm.annotations.To;

public class FriendshipImpl implements Friendship {

	@Id private Object id;
	
	@From private Person self;

	@To private Person friend;

	public Object getId() {
		return id;
	}
	
	public Person getSelf() {
		return self;
	}

	public Person getFriend() {
		return friend;
	}

}
