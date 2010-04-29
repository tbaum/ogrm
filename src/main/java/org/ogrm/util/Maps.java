package org.ogrm.util;

import java.util.HashMap;
import java.util.Map;

public class Maps {

	public static <K, V> Map<K, V> newMap() {
		return new HashMap<K, V>();
	}

	public static <K,V> LazyMap<K, V> asLazy(Map<K, V> map, Transformer<K, V> factory){
		return new LazyMap<K, V>( map, factory );
	}
	
}
