package io.github.ulyssesrr.infratoolbox.hash;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder(toBuilder = true)
@RequiredArgsConstructor
public class GuavaHasher implements StatefulHasher {

    @NonNull
    @Builder.Default
    public final Hasher hasher = Hashing.murmur3_128().newHasher();

    public static StatefulHasherFactory getDefaultFactory() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final StatefulHasherFactory INSTANCE = new StatefulHasherFactory() {
            @Override
            public StatefulHasher create() {
                return new GuavaHasher(Hashing.murmur3_128().newHasher());
            }
        };
    }

    @Override
    public GuavaHasher putByte(byte b) {
        hasher.putByte(b);
        return this;
    }

    @Override
    public GuavaHasher putBytes(byte[] bytes) {
        hasher.putBytes(bytes);
        return this;
    }

    @Override
    public GuavaHasher putShort(short s) {
        hasher.putShort(s);
        return this;
    }

    @Override
    public GuavaHasher putInt(int i) {
        hasher.putInt(i);
        return this;
    }

    @Override
    public GuavaHasher putLong(long l) {
        hasher.putLong(l);
        return this;
    }

    /**
     * Equivalent to {@code putInt(Float.floatToRawIntBits(f))}.
     */
    @Override
    public GuavaHasher putFloat(float f) {
        hasher.putFloat(f);
        return this;
    }

    /**
     * Equivalent to {@code putLong(Double.doubleToRawLongBits(d))}.
     */
    @Override
    public GuavaHasher putDouble(double d) {
        hasher.putDouble(d);
        return this;
    }

    @Override
    public GuavaHasher putBoolean(boolean b) {
        hasher.putBoolean(b);
        return this;
    }

    @Override
    public GuavaHasher putChar(char c) {
        hasher.putChar(c);
        return this;
    }

    @Override
    public GuavaHasher putString(String string) {
        final int length = string.length();
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            hasher.putChar(c);
        }
        return this;
    }

    @Override
    public String hash() {
        return hasher.hash().toString();
    }

}
