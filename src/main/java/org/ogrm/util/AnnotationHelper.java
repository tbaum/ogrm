package org.ogrm.util;

public class AnnotationHelper {

	public static boolean contains( Object[] array, Object toTest ) {
		for (Object e : array) {
			if (e.equals( toTest ))
				return true;
		}
		return false;
	}

}
