package io.github.ulyssesrr.infratoolbox.hash;

public interface StatefulHasher {

  StatefulHasher putByte(Byte b);

  StatefulHasher putBytes(byte[] bytes);

  StatefulHasher putShort(Short s);

  StatefulHasher putInt(Integer i);

  StatefulHasher putLong(Long l);

  /**
   * Equivalent to {@code putInt(Float.floatToRawIntBits(f))}.
   */
  StatefulHasher putFloat(Float f);

  /**
   * Equivalent to {@code putLong(Double.doubleToRawLongBits(d))}.
   */
  StatefulHasher putDouble(Double d);

  /**
   * Equivalent to {@code putByte(b ? (byte) 1 : (byte) 0)}.
   */
  StatefulHasher putBoolean(Boolean b);

  StatefulHasher putChar(Character c);

  StatefulHasher putString(String string);

  String hash();
}
