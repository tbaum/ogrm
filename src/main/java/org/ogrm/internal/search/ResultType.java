package org.ogrm.internal.search;

import java.util.List;

import org.ogrm.NonUniqueResultException;
import org.ogrm.search.Predicate;
import org.ogrm.search.Result;
import org.ogrm.util.Lists;

public class ResultType<T> implements Result<T> {

	private List<T> result;

	public ResultType(List<T> result) {
		this.result = result;
	}

	@Override
	public T first() {
		if (result.isEmpty())
			return null;
		return result.get( 0 );
	}

	@Override
	public List<T> list() {
		return result;
	}

	@Override
	public T unique() {
		if (result.isEmpty())
			return null;

		if (result.size() > 1)
			throw new NonUniqueResultException( String
					.format( "The search for %s with %s in %s returned more than one entity" ) );

		return result.get( 0 );
	}

	@Override
	public Result<T> filter( Predicate<T> predicate ) {
		List<T> newList = Lists.newList();

		for (T e : result) {
			if (predicate.evaluate( e ))
				newList.add( e );
		}

		return new ResultType<T>( newList );
	}

}
