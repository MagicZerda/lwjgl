package de.magiczerda.lwjgl_game_test.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class UtilMethods {
	
	
	//WARNING: THESE METHODS MIGHT NOT WORK
	
	
	public static Charset charset = Charset.forName("UTF-8");
	public static CharsetEncoder encoder = charset.newEncoder();
	public static CharsetDecoder decoder = charset.newDecoder();
	
	
	/**
	 * @deprecated Check if it works!
	 * 
	 * @param string
	 * @return
	 */
	
	public static ByteBuffer stringToByteBuffer(String string) {
		try {
			return encoder.encode(CharBuffer.wrap(string));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @deprecated Check if it works!
	 * 
	 * @param string
	 * @return
	 */
	
	public static String byteBufferToString(ByteBuffer buffer) {
		String data = "";
		try {
			int old_position = buffer.position();
			data = decoder.decode(buffer).toString();
			
			//reset buffer position to its original so it is not changed
			buffer.position(old_position);
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		}
		return data;
	}

}
