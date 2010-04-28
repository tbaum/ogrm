package org.ogrm.logger;



public class LoggerImpl implements Logger {

	private org.slf4j.Logger logger;

	public LoggerImpl(org.slf4j.Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean isDebug() {
		return logger.isDebugEnabled();
	}

	@Override
	public void debug( Object template, Object... params ) {
		if (logger.isDebugEnabled())
			if (template != null)
				logger.debug( String.format( template.toString(), params ) );
	}

	@Override
	public void info( Object template, Object... params ) {
		if (logger.isInfoEnabled())
			if (template != null)
				logger.info( String.format( template.toString(), params ) );
	}

	@Override
	public void trace( Object template, Object... params ) {
		if (logger.isTraceEnabled())
			if (template != null)
				logger.trace( String.format( template.toString(), params ) );
	}

	@Override
	public void warn( Object template, Object... params ) {
		if (template != null)
			logger.warn( String.format( template.toString(), params ) );
	}

	@Override
	public void error( Object template, Object... params ) {
		if (template != null)
			logger.error( String.format( template.toString(), params ) );
	}

	@Override
	public void warn( Object template, Throwable t, Object... params ) {
		if (template != null)
			logger.warn( String.format( template.toString(), params ), t );
	}

	@Override
	public void error( Object template, Throwable t, Object... params ) {
		if (template != null)
			logger.error( String.format( template.toString(), params ), t );
	}
}
