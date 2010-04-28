package org.ogrm.util;

public class Guard {

	public static void checkNotNull( String message, Object value ) {
		if (value == null)
			Exceptions.nullPointer( message );
	}

}
