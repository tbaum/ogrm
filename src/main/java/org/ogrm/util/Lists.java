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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Lists {

	public Lists() {
	}

	/**
	 * Constructs and returns a new generic {@link java.util.ArrayList}
	 * instance.
	 * 
	 */
	public static <T> List<T> newList() {
		return new ArrayList<T>();
	}

	/**
	 * Constructs and returns a new generic {@link java.util.ArrayList}
	 * instance.
	 * 
	 * @param initialCapacity
	 *            the initial capacity of the list
	 */
	public static <T> List<T> newList( int initialCapacity ) {
		return new ArrayList<T>( initialCapacity );
	}

	/**
	 * Creates a new empty, modifiable Deque.
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> Deque<T> newDeque() {
		return new ArrayDeque<T>();
	}

	public static <T> Deque<T> newDeque( Collection<T> collection ) {
		return new ArrayDeque<T>( collection );
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> emptyList() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * Creates a new, fully modifiable list from an initial set of elements.
	 */
	public static <T, V extends T> List<T> newList( V... elements ) {
		// Was call to newList(), but Sun JDK can't handle that.
		return new ArrayList<T>( Arrays.asList( elements ) );
	}

	/**
	 * Useful for queues.
	 */
	public static <T> LinkedList<T> newLinkedList() {
		return new LinkedList<T>();
	}

	/**
	 * Constructs and returns a new {@link java.util.ArrayList} as a copy of the
	 * provided collection.
	 */
	@SuppressWarnings("unchecked")
	public static <T, V extends T> List<T> newList( Iterable<V> list ) {
		if (list instanceof Collection)
			return new ArrayList<T>( (Collection<T>) list );

		List<T> newList = new ArrayList<T>();
		for (V v : list) {
			newList.add( v );
		}

		return newList;
	}

	/**
	 * Constructs and returns a new
	 * {@link java.util.concurrent.CopyOnWriteArrayList}.
	 */
	public static <T> List<T> newThreadSafeList() {
		return new CopyOnWriteArrayList<T>();
	}

}
