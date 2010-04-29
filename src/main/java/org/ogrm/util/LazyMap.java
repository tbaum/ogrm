/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.ogrm.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class LazyMap<K, V> implements Map<K,V>, Serializable {

	private static final long serialVersionUID = 1L;

	protected final Transformer<K,V> factory;

	private Map<K, V> map;

	public LazyMap(Map<K,V> map, Transformer<K,V> factory) {
		if (factory == null) {
			throw new IllegalArgumentException( "Factory must not be null" );
		}
		this.factory = factory;
		this.map = map;
	}
	
	public void clear() {
		map.clear();
	}

	public boolean containsKey( Object key ) {
		return map.containsKey( key );
	}

	public boolean containsValue( Object value ) {
		return map.containsValue( value );
	}

	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	public boolean equals( Object o ) {
		return map.equals( o );
	}

	public int hashCode() {
		return map.hashCode();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public V put( K key, V value ) {
		return map.put( key, value );
	}

	public void putAll( Map<? extends K, ? extends V> m ) {
		map.putAll( m );
	}

	public V remove( Object key ) {
		return map.remove( key );
	}

	public int size() {
		return map.size();
	}

	public Collection<V> values() {
		return map.values();
	}

	

	public V get( Object key ) {
		// create value for key if key is not currently in the map
		if (map.containsKey( key ) == false) {
			K k = (K) key;
			V value = (V) factory.transform( k );
			map.put( k, value );
			return value;
		}
		return map.get( key );
	}

}
