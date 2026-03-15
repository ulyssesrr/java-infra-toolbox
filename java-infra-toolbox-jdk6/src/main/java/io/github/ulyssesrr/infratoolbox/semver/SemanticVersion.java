package io.github.ulyssesrr.infratoolbox.semver;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public final class SemanticVersion {

    private final String originalVersion;
    private final int major;
    private final int minor;
    private final int patch;
    private final String suffix;

}
