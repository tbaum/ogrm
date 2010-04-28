package org.ogrm.test;

public interface LifeEvent extends Comparable<LifeEvent> {

	public void setType(String type);
	
	public String getType();
	
	public void setTimestamp(Instant timestamp);
	
	public Instant getTimestamp();
	
}
