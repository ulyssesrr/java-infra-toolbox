package io.github.ulyssesrr.infratoolbox.hash;

public interface StatefulHasher {

  StatefulHasher putByte(byte b);

  StatefulHasher putBytes(byte[] bytes);

  StatefulHasher putShort(short s);

  StatefulHasher putInt(int i);

  StatefulHasher putLong(long l);

  /**
   * Equivalent to {@code putInt(Float.floatToRawIntBits(f))}.
   */
  StatefulHasher putFloat(float f);

  /**
   * Equivalent to {@code putLong(Double.doubleToRawLongBits(d))}.
   */
  StatefulHasher putDouble(double d);

  /**
   * Equivalent to {@code putByte(b ? (byte) 1 : (byte) 0)}.
   */
  StatefulHasher putBoolean(boolean b);

  StatefulHasher putChar(char c);

  StatefulHasher putString(String string);

  String hash();
}
