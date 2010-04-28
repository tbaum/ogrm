package org.ogrm.util;


public class Strings {

	public static int safeCompare(String one, String other) {
		if (one == null && other == null)
			return 0;
		if (one == null)
			return -1;
		if (other == null)
			return 1;
		return one.compareTo( other );
	}

	private static String doBuildSep(String separator, Iterable<Object> stringParts) {
		StringBuilder builder = new StringBuilder();

		boolean first = true;

		for (Object part : stringParts) {
			if (!first)
				builder.append( separator );

			if (part != null)
				builder.append( part.toString() );
			else
				builder.append( "<null>" );

			first = false;
		}

		return builder.toString();
	}

	private static String doBuild(Iterable<Object> stringParts) {
		StringBuilder builder = new StringBuilder();

		for (Object part : stringParts) {
			if (part != null)
				builder.append( part.toString() );
			else
				builder.append( "<null>" );
		}

		return builder.toString();
	}

	public static String build(Object... stringParts) {
		return doBuild( Lists.newList( stringParts ) );
	}

	public static String buildSep(String separator, Object... stringParts) {
		return doBuildSep( separator, Lists.newList( stringParts ) );
	}

	public static String build(Iterable<Object> stringParts) {
		return doBuild( stringParts );
	}

	public static String buildSep(String separator, Iterable<Object> stringParts) {
		return doBuildSep( separator, stringParts );
	}

	public static String tail(String originalString, String delimiter) {
		return originalString.substring( originalString.lastIndexOf( delimiter ) );
	}

	public static String wildcardToRegexp(String wildcard) {
		StringBuffer s = new StringBuffer( wildcard.length() );
		s.append( '^' );
		for (int i = 0, is = wildcard.length(); i < is; i++) {
			char c = wildcard.charAt( i );
			switch (c) {
			case '*':
				s.append( '.' );
				s.append( '*' );
				break;
			case '?':
				s.append( '.' );
				break;
			// escape special regexp-characters
			case '(':
			case ')':
			case '[':
			case ']':
			case '$':
			case '^':
			case '.':
			case '{':
			case '}':
			case '|':
			case '\\':
				s.append( '\\' );
				s.append( c );
				break;
			default:
				s.append( c );
				break;
			}
		}
		s.append( '$' );
		return (s.toString());
	}
}
