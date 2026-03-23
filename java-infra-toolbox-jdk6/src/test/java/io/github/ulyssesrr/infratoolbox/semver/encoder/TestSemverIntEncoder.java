package io.github.ulyssesrr.infratoolbox.semver.encoder;

public class TestSemverIntEncoder implements TestSemverEncoder<Integer> {

    @Override
    public SemverEncoder<Integer> createSemverEncoder() {
        return SemverIntEncoder.getInstance();
    }

}
