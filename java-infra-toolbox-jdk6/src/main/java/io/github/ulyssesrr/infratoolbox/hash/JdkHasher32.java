package io.github.ulyssesrr.infratoolbox.hash;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder(toBuilder = true)
@RequiredArgsConstructor
public class JdkHasher32 implements StatefulHasher {

    private final List<Object> hashing = new LinkedList<Object>();

    public static StatefulHasherFactory getDefaultFactory() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final StatefulHasherFactory INSTANCE = new StatefulHasherFactory() {
            @Override
            public StatefulHasher create() {
                return new JdkHasher32();
            }
        };
    }

    @Override
    public StatefulHasher putByte(byte b) {
        hashing.add(b);
        return this;
    }

    @Override
    public StatefulHasher putBytes(byte[] bytes) {
        hashing.add(bytes);
        return this;
    }

    @Override
    public StatefulHasher putShort(short s) {
        hashing.add(s);
        return this;
    }

    @Override
    public StatefulHasher putInt(int i) {
        hashing.add(i);
        return this;
    }

    @Override
    public StatefulHasher putLong(long l) {
        hashing.add(l);
        return this;
    }

    @Override
    public StatefulHasher putFloat(float f) {
        hashing.add(f);
        return this;
    }

    @Override
    public StatefulHasher putDouble(double d) {
        hashing.add(d);
        return this;
    }

    @Override
    public StatefulHasher putBoolean(boolean b) {
        hashing.add(b);
        return this;
    }

    @Override
    public StatefulHasher putChar(char c) {
        hashing.add(c);
        return this;
    }

    @Override
    public StatefulHasher putString(String string) {
        hashing.add(string);
        return this;
    }

    @Override
    public String hash() {
        int hashCode = 1;
        for (Object element : this.hashing) {
            int elementHash;
            if (element != null) {
                if (element.getClass().isArray()) {
                    if (element instanceof Object[]) {
                        elementHash = Arrays.deepHashCode((Object[]) element);
                    } else if (element instanceof byte[]) {
                        elementHash = Arrays.hashCode((byte[]) element);
                    } else if (element instanceof short[]) {
                        elementHash = Arrays.hashCode((short[]) element);
                    } else if (element instanceof int[]) {
                        elementHash = Arrays.hashCode((int[]) element);
                    } else if (element instanceof long[]) {
                        elementHash = Arrays.hashCode((long[]) element);
                    } else if (element instanceof char[]) {
                        elementHash = Arrays.hashCode((char[]) element);
                    } else if (element instanceof float[]) {
                        elementHash = Arrays.hashCode((float[]) element);
                    } else if (element instanceof double[]) {
                        elementHash = Arrays.hashCode((double[]) element);
                    } else if (element instanceof boolean[]) {
                        elementHash = Arrays.hashCode((boolean[]) element);
                    } else {
                        elementHash = element.hashCode();
                    }
                } else {
                    elementHash = element.hashCode();
                }
            } else {
                elementHash = 0;
            }
            hashCode = 31 * hashCode + elementHash;
        }
        return Integer.toHexString(hashCode);
    }

}
