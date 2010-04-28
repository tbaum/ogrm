package org.ogrm.internal.converter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import org.apache.commons.io.input.ClassLoaderObjectInputStream;
import org.ogrm.PersistenceException;

public class SerializableConverter implements ValueConverter {

	@Override
	public Object fromSimpleValue( Object simpleValue ) {
		if (simpleValue == null)
			return null;
		byte[] bytes = (byte[]) simpleValue;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ByteArrayInputStream bis = new ByteArrayInputStream( bytes );
		ObjectInputStream ois;
		try {
			ois = new ClassLoaderObjectInputStream( classLoader, bis );
			return ois.readObject();
		} catch (Exception e) {
			throw new PersistenceException( "Could not deserialize value using " + classLoader, e );
		}
	}

	@Override
	public Object toSimpleValue( Object realValue ) {
		if (realValue == null)
			return null;
		ObjectOutputStream os = null;

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream( 5000 );
		try {
			os = new ObjectOutputStream( new BufferedOutputStream( byteStream ) );
			os.flush();
			os.writeObject( realValue );
			os.flush();
			byte[] sendBuf = byteStream.toByteArray();
			os.close();
			return sendBuf;
		} catch (IOException e) {
			throw new PersistenceException( "Could not serilialize " + realValue, e );
		}
	}

}
