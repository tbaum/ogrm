package org.ogrm.persistentType;

import java.util.Calendar;
import java.util.Date;

public class Instant implements Comparable<Instant> {

	private long timestamp;

	public static Instant fromMillis( long timestamp ) {
		return new Instant( timestamp );
	}

	public static Instant at( int year, int month, int day ) {
		Calendar cal = Calendar.getInstance();
		cal.set( year, month, day );
		return new Instant( cal.getTimeInMillis() );
	}

	public static Instant now() {
		return new Instant( new Date().getTime() );
	}

	private Instant(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getInstant() {
		return timestamp;
	}

	@Override
	public int compareTo( Instant o ) {
		return (int) (timestamp - o.timestamp);
	}

}
