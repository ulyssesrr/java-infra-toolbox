package io.github.ulyssesrr.infratoolbox.semver.encoder;

import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;

public final class SemverIntEncoder implements SemverEncoder<Integer> {

    private final int bits;

    public static SemverIntEncoder getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final SemverIntEncoder INSTANCE = new SemverIntEncoder(10);
    }

    public SemverIntEncoder(int bits) {
        this.bits = bits;
    }

    public Integer toValue(SemanticVersion v) {
        if (v == null) {
            return 0;
        }

        int major = v.getMajor() << (bits * 2);
        int minor = v.getMinor() << bits;
        int patch = v.getPatch();

        return major | minor | patch;
    }
}
