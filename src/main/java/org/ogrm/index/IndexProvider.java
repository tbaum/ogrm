package org.ogrm.index;

public interface IndexProvider {

	public Index getIndex( String name );
	
	public void dispose();
}
