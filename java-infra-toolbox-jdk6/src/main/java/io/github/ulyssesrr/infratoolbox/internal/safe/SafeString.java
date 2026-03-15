package io.github.ulyssesrr.infratoolbox.internal.safe;

public final class SafeString {

    private SafeString() {}

    public static String value(Object o) {

        try {
            return String.valueOf(o);
        } catch (Throwable e) {
            return null;
        }

    }

}
