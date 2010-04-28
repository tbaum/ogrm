package org.ogrm.logger;

public interface Logger {

	public boolean isDebug();

	public void trace( Object template, Object... params );

	public void debug( Object template, Object... params );

	public void info( Object template, Object... params );

	public void warn( Object template, Object... params );

	public void error( Object template, Object... params );

	public void warn( Object template, Throwable t, Object... params );

	public void error( Object template, Throwable t, Object... params );

}
