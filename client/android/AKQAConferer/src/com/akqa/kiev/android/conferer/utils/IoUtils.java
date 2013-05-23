package com.akqa.kiev.android.conferer.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for convenient work with streams.
 * @author Yuriy.Belelya
 *
 */
public final class IoUtils {

	private IoUtils() {
	}

	private static final int BUFFER_SIZE = 1024;

	public static byte[] getBytesFromStream(InputStream inputStream)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int count;
		byte[] buffer = new byte[BUFFER_SIZE];
		while ((count = (inputStream.read(buffer))) >= 0) {
			baos.write(buffer, 0, count);
		}
		baos.flush();
		baos.close();
		byte[] byteResult = baos.toByteArray();
		return byteResult;
	}

	public static String getStringDataFromStream(InputStream inputStream)
			throws IOException {
		byte[] bytes = getBytesFromStream(inputStream);
		return bytes != null ? new String(bytes) : null;
	}

	public static String getStringDataFromStream(InputStream inputStream,
			String charset) throws IOException {
		byte[] bytes = getBytesFromStream(inputStream);
		return bytes != null ? new String(bytes, charset) : null;
	}

}
