package org.ogrm.util;

import java.util.Iterator;

public abstract class StringAppender<T> {

	private Iterable<? extends T> data;

	private boolean first = true;
	private boolean last = false;

	private StringBuilder builder;

	public StringAppender(Iterable<? extends T> data) {
		this.data = data;
		builder = new StringBuilder();
	}

	public String build() {

		Iterator<? extends T> i = data.iterator();

		before();

		while (i.hasNext()) {
			T o = i.next();
			if (!i.hasNext())
				last = true;

			item( o );

			first = false;
		}

		after();
		return builder.toString();
	}

	protected void append(Object object) {
		if (object != null)
			builder.append( object );
	}

	protected void appendIfFirst(Object object) {
		if (object != null && first)
			builder.append( object );
	}

	protected void appendIfLast(Object object) {
		if (object != null && last)
			builder.append( object );
	}

	protected void appendIfNotFirst(Object object) {
		if (object != null && !first)
			builder.append( object );
	}

	protected void appendIfNotLast(Object object) {
		if (object != null && !last)
			builder.append( object );
	}

	protected void before() {

	}

	protected void after() {

	}

	protected abstract void item(T item);

}
