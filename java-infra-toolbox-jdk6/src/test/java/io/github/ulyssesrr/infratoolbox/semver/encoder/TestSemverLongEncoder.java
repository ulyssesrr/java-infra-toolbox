package io.github.ulyssesrr.infratoolbox.semver.encoder;

public class TestSemverLongEncoder implements TestSemverEncoder<Long> {

    @Override
    public SemverEncoder<Long> createSemverEncoder() {
        return SemverLongEncoder.getInstance();
    }

}
