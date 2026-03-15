package io.github.ulyssesrr.infratoolbox.internal.safe;

public final class SafeThrowable {

    private SafeThrowable() {}

    public static String message(Throwable t) {

        try {
            return t == null ? null : t.getMessage();
        } catch (Throwable e) {
            return null;
        }

    }

    public static StackTraceElement[] stack(Throwable t) {

        try {
            return t == null ? null : t.getStackTrace();
        } catch (Throwable e) {
            return null;
        }

    }

}
