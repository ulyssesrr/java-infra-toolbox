package io.github.ulyssesrr.infratoolbox.semver.encoder;

import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;
public interface SemverEncoder<T> {
    T toValue(SemanticVersion v);
}
