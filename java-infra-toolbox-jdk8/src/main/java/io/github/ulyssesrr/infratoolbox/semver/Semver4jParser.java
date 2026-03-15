package io.github.ulyssesrr.infratoolbox.semver;

import org.semver4j.Semver;

import lombok.NonNull;

public class Semver4jParser implements SemanticVersionParser {

    public SemanticVersion parse(@NonNull String version) {
        Semver semver = Semver.parse(version);

        StringBuilder suffixTokens = new StringBuilder();
        if (semver.getPreRelease() != null) {
            if (!semver.getPreRelease().isEmpty()) {
                suffixTokens.append('.');
                suffixTokens.append(String.join(".", semver.getPreRelease()));
            }

            if (!semver.getBuild().isEmpty()) {
                suffixTokens.append('+');
                suffixTokens.append(String.join(".", semver.getBuild()));
            }
        }

        return new SemanticVersion(
            version,
            semver.getMajor(),
            semver.getMinor(),
            semver.getPatch(),
            suffixTokens.length() == 0 ? null : String.join("-", suffixTokens)
        );

    }

}
