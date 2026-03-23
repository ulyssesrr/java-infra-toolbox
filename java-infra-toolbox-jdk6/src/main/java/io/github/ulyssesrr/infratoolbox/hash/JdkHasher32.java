package io.github.ulyssesrr.infratoolbox.hash;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder(toBuilder = true)
@RequiredArgsConstructor
public class JdkHasher32 extends AbstractStatefulHasher {

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
    protected void doPutByte(byte b) {
        hashing.add(b);
    }

    @Override
    protected void doPutBytes(@NonNull byte[] bytes) {
        hashing.add(bytes);
    }

    @Override
    protected void doPutShort(short s) {
        hashing.add(s);
    }

    @Override
    protected void doPutInt(int i) {
        hashing.add(i);
    }

    @Override
    protected void doPutLong(long l) {
        hashing.add(l);
    }

    @Override
    protected void doPutFloat(float f) {
        hashing.add(f);
    }

    @Override
    protected void doPutDouble(double d) {
        hashing.add(d);
    }

    @Override
    protected void doPutBoolean(boolean b) {
        hashing.add(b);
    }

    @Override
    protected void doPutChar(char c) {
        hashing.add(c);
    }

    @Override
    protected void doPutString(@NonNull String string) {
        hashing.add(string);
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
