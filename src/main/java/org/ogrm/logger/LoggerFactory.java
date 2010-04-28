package org.ogrm.logger;


public class LoggerFactory {

	public static Logger getLogger( Class<?> type ) {
		return new LoggerImpl( org.slf4j.LoggerFactory.getLogger( type ) );
	}

	public static Logger getLogger( String category ) {
		return new LoggerImpl( org.slf4j.LoggerFactory.getLogger( category ) );
	}

}
