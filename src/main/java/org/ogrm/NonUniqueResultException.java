package org.ogrm;

public class NonUniqueResultException extends PersistenceException {

	private static final long serialVersionUID = 1L;

	public NonUniqueResultException(String message) {
		super( message );
	}

	

}
