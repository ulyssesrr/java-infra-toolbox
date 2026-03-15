package io.github.ulyssesrr.infratoolbox.semver;

import lombok.NonNull;

public interface SemanticVersionParser {
    
    SemanticVersion parse(@NonNull String version);
}
