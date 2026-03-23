package io.github.ulyssesrr.infratoolbox.hash;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder(toBuilder = true)
@RequiredArgsConstructor
public class GuavaHasher extends AbstractStatefulHasher {

    @NonNull
    @Builder.Default
    public final Hasher hasher = Hashing.murmur3_128().newHasher();
    private final Supplier<HashCode> hashCodeSupplier = Suppliers.memoize(new Supplier<HashCode>() {

        @Override
        public HashCode get() {
            return hasher.hash();
        }

    });

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
    protected void doPutByte(byte b) {
        hasher.putByte(b);
    }

    @Override
    protected void doPutBytes(@NonNull byte[] bytes) {
        hasher.putBytes(bytes);
    }

    @Override
    protected void doPutShort(short s) {
        hasher.putShort(s);
    }

    @Override
    protected void doPutInt(int i) {
        hasher.putInt(i);
    }

    @Override
    protected void doPutLong(long l) {
        hasher.putLong(l);
    }

    @Override
    protected void doPutFloat(float f) {
        hasher.putFloat(f);
    }

    @Override
    protected void doPutDouble(double d) {
        hasher.putDouble(d);
    }

    @Override
    protected void doPutBoolean(boolean b) {
        hasher.putBoolean(b);
    }

    @Override
    protected void doPutChar(char c) {
        hasher.putChar(c);
    }

    @Override
    protected void doPutString(@NonNull String string) {
        hasher.putUnencodedChars(string);
    }

    @Override
    public String hash() {
        return hashCodeSupplier.get().toString();
    }

}
