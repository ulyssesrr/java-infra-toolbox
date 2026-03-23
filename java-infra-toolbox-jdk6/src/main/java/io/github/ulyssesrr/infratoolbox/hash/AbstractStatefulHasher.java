package io.github.ulyssesrr.infratoolbox.hash;

import lombok.NonNull;

public abstract class AbstractStatefulHasher implements StatefulHasher {

    @Override
    public AbstractStatefulHasher putByte(Byte b) {
        if (b == null) {
            doPutString("byte");
            doPutByte((byte) 0);
        } else {
            doPutByte(b);
        }
        return this;
    }

    protected abstract void doPutByte(byte b);

    @Override
    public AbstractStatefulHasher putBytes(byte[] bytes) {
        if (bytes == null) {
            doPutString("byte[]");
            doPutByte((byte) 0);
        } else {
            doPutBytes(bytes);
        }
        return this;
    }

    protected abstract void doPutBytes(@NonNull byte[] bytes);

    @Override
    public AbstractStatefulHasher putShort(Short s) {
        if (s == null) {
            doPutString("Short");
            doPutShort((short) 0);
        } else {
            doPutShort(s);
        }
        return this;
    }

    protected abstract void doPutShort(short s);

    @Override
    public AbstractStatefulHasher putInt(Integer i) {
        if (i == null) {
            doPutString("Integer");
            doPutInt(0);
        } else {
            doPutInt(i);
        }
        return this;
    }

    protected abstract void doPutInt(int i);

    @Override
    public AbstractStatefulHasher putLong(Long l) {
        if (l == null) {
            doPutString("Long");
            doPutLong(0L);
        } else {
            doPutLong(l);
        }
        return this;
    }

    protected abstract void doPutLong(long l);

    /**
     * Equivalent to {@code putInt(Float.floatToRawIntBits(f))}.
     */
    @Override
    public AbstractStatefulHasher putFloat(Float f) {
        if (f == null) {
            doPutString("Float");
            doPutFloat(0);
        } else {
            doPutFloat(f);
        }
        return this;
    }

    protected abstract void doPutFloat(float f);

    /**
     * Equivalent to {@code putLong(Double.doubleToRawLongBits(d))}.
     */
    @Override
    public AbstractStatefulHasher putDouble(Double d) {
        if (d == null) {
            doPutString("Double");
            doPutDouble(0);
        } else {
            doPutDouble(d);
        }
        return this;
    }

    protected abstract void doPutDouble(double d);

    /**
     * Equivalent to {@code putByte(b ? (byte) 1 : (byte) 0)}.
     */
    @Override
    public AbstractStatefulHasher putBoolean(Boolean b) {
        if (b == null) {
            doPutString("Boolean");
            doPutByte((byte) 0);
        } else {
            doPutBoolean(b);
        }
        return this;
    }

    protected abstract void doPutBoolean(boolean b);

    @Override
    public AbstractStatefulHasher putChar(Character c) {
        if (c == null) {
            doPutString("Character");
            doPutChar('\0');
        } else {
            doPutChar(c);
        }
        return this;
    }

    protected abstract void doPutChar(char c);

    @Override
    public AbstractStatefulHasher putString(String string) {
        if (string == null) {
            doPutString("String");
            doPutInt(0);
        } else {
            doPutString(string);
        }
        return this;
    }

    protected abstract void doPutString(@NonNull String string);
}
