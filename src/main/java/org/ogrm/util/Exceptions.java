package org.ogrm.util;

import org.ogrm.PersistenceException;

public class Exceptions {

	public static void persistence( String message, Exception exception ) {
		throw new PersistenceException( message, exception );
	}

	public static void persistence( String message ) {
		throw new PersistenceException( message );
	}
	
	public static void nullPointer(String message){
		throw new NullPointerException( message );
	}

}
