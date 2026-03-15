package io.github.ulyssesrr.infratoolbox.semver;

import lombok.NonNull;

public enum JdkSemanticVersionParser implements SemanticVersionParser {
    INSTANCE;

    private JdkSemanticVersionParser() {
    }

    public SemanticVersion parse(@NonNull String version) {
        final int length = version.length();
        int major = 0;
        int minor = 0;
        int patch = 0;
        String suffix = null;

        int firstSepPos = version.indexOf('.');
        if (firstSepPos > -1) {
            String majorPart = version.substring(0, firstSepPos);
            major = Integer.parseInt(majorPart);

            int secondSepPos = version.indexOf('.', firstSepPos + 1);
            int secondSepPosPlus1 = secondSepPos + 1;
            if (secondSepPos > -1 && secondSepPosPlus1 < length) {
                String minorPart = version.substring(firstSepPos + 1, secondSepPos);
                minor = Integer.parseInt(minorPart);

                if (length > secondSepPosPlus1) {
                    String patchStr = version.substring(secondSepPosPlus1);

                    final int patchStrLenght = patchStr.length();
                    int nonDigitPos = 0;
                    while (nonDigitPos < patchStrLenght && Character.isDigit(patchStr.charAt(nonDigitPos))) {
                        nonDigitPos++;
                    }

                    if (nonDigitPos == 0) {
                        patch = 0;
                        suffix = patchStr;
                    } else {
                        patch = Integer.parseInt(patchStr.substring(0, nonDigitPos));

                        // ignore separator
                        nonDigitPos++;

                        if (nonDigitPos < patchStrLenght) {
                            suffix = patchStr.substring(nonDigitPos);
                        } else {
                            suffix = null;
                        }
                    }
                }
            } else {
                patch = 0;
                suffix = null;
            }
        } else {
            major = Integer.parseInt(version);
        }

        return new SemanticVersion(version, major, minor, patch, suffix);
    }
}
