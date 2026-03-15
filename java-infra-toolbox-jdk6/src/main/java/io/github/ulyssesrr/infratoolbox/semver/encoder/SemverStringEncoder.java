package io.github.ulyssesrr.infratoolbox.semver.encoder;

import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public final class SemverStringEncoder implements SemverEncoder<String> {

    @Builder.Default
    private final int digits = 6;

    @Builder.Default
    private final boolean includeSuffix = true;

    public static SemverStringEncoder getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final SemverStringEncoder INSTANCE = SemverStringEncoder.builder().build();
    }

    public String toValue(SemanticVersion v) {
        if (v == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(pad(v.getMajor()));
        sb.append('.');
        sb.append(pad(v.getMinor()));
        sb.append('.');
        sb.append(pad(v.getPatch()));

        if (includeSuffix) {
            sb.append(v.getSuffix());
        }

        return sb.toString();
    }

    private String pad(int value) {
        return String.format("%0"+digits+"d", value);
    }
}
