package org.ogrm.util;

public class ObjectHelper {

	public static boolean areNotEqual( Object one, Object other ) {
		return !areEqual( one, other );
	}

	public static boolean areEqual( Object one, Object other ) {
		if (one == null || other == null)
			return false;

		return one.equals( other );
	}

}
