package org.ogrm.internal.proxy;

public class Nullvalues {

	public static Object getForType(Class<?> type){
		if(is(type,String.class))
			return "";
		
		if(is(type,int.class,long.class,Integer.class,Long.class,Byte.class,byte.class))
			return 0;
		
		if(is(type,boolean.class,Boolean.class))
			return false;
		
		return null;
	}

	private static boolean is( Class<?> test, Class<?>... candidates ) {
		for (Class<?> candidate : candidates) {
			if (test.equals( candidate ))
				return true;
		}
		return false;
	}

}
