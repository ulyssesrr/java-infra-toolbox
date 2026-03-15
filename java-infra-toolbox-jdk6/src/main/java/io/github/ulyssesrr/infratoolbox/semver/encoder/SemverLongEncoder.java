package io.github.ulyssesrr.infratoolbox.semver.encoder;

import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;

public final class SemverLongEncoder implements SemverEncoder<Long> {

    private final int bits;

    public static SemverLongEncoder getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final SemverLongEncoder INSTANCE = new SemverLongEncoder(21);
    }

    public SemverLongEncoder(int bits) {
        this.bits = bits;
    }

    public Long toValue(SemanticVersion v) {
        if (v == null) {
            return 0L;
        }

        long major = ((long)v.getMajor()) << (bits * 2);
        long minor = ((long)v.getMinor()) << bits;
        long patch = v.getPatch();

        return major | minor | patch;
    }

}
