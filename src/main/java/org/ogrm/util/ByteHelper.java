package org.ogrm.util;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

public class ByteHelper {

	public static final long makeLongFromByte( byte[] b ) {
		ByteBuffer byteBuffer = ByteBuffer.wrap( b );
		return byteBuffer.getLong();
	}

	public static final byte[] makeByteFromLong( long l ) {
		byte[] bArray = new byte[8];
		LongBuffer lBuffer = ByteBuffer.wrap( bArray ).asLongBuffer();
		lBuffer.put( 0, l );
		return bArray;
	}
}
