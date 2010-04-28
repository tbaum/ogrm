package org.ogrm.persistentType;

import org.ogrm.annotations.Id;
import org.ogrm.annotations.Load;
import org.ogrm.annotations.Store;
import org.ogrm.annotations.Value;

public class LifeEventImpl implements LifeEvent {

	@Id
	private Object id;

	@Value
	private String type;

	@Value(converter = InstantConverter.class)
	private Instant timestamp;

	public Object getId() {
		return id;
	}
	
	@Override
	@Load( { "timestamp" })
	public Instant getTimestamp() {
		return timestamp;
	}

	@Override
	@Load( { "type" })
	public String getType() {
		return type;
	}

	@Override
	@Store( { "timestamp" })
	public void setTimestamp( Instant timestamp ) {
		this.timestamp = timestamp;
	}

	@Override
	@Store( { "type" })
	public void setType( String type ) {
		this.type = type;
	}

	@Override
	public int compareTo( LifeEvent o ) {
		return getTimestamp().compareTo( o.getTimestamp() );
	}

}
