package io.github.ulyssesrr.infratoolbox.hash;

public interface StatefulHasher {

  StatefulHasher putByte(Byte b);

  StatefulHasher putBytes(byte[] bytes);

  StatefulHasher putShort(Short s);

  StatefulHasher putInt(Integer i);

  StatefulHasher putLong(Long l);

  StatefulHasher putFloat(Float f);

  StatefulHasher putDouble(Double d);

  StatefulHasher putBoolean(Boolean b);

  StatefulHasher putChar(Character c);

  StatefulHasher putString(String string);

  String hash();
}
