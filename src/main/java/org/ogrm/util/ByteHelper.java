package org.ogrm.util;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.Arrays;

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

	public static final long makeLongFromByteReversed( byte[] b ) {
		int l = b.length;
		byte[] reversed = new byte[l];

		for (int i = 0; i < l; i++) {
			reversed[l - i - 1] = b[i];
		}

		ByteBuffer byteBuffer = ByteBuffer.wrap( reversed );
		return byteBuffer.getLong();
	}

	public static final byte[] makeByteFromLongReversed( long l ) {
		byte[] bArray = new byte[8];
		LongBuffer lBuffer = ByteBuffer.wrap( bArray ).asLongBuffer();
		lBuffer.put( 0, l );
		
		int le = bArray.length;
		byte[] reversed = new byte[le];

		for (int i = 0; i < le; i++) {
			reversed[le - i - 1] = bArray[i];
		}
		return reversed;

	}
}
