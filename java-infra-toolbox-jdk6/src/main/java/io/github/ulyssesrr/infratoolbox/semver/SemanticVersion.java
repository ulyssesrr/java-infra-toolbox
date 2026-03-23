package io.github.ulyssesrr.infratoolbox.semver;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Represents a Semantic Version as defined by the Semantic Versioning specification (SemVer).
 *
 * <p>A semantic version consists of three numeric components (MAJOR.MINOR.PATCH) and an
 * optional pre-release suffix. The version follows the format:</p>
 *
 * <pre>MAJOR.MINOR.PATCH[SUFFIX]</pre>
 *
 * <p>Example valid versions:</p>
 * <ul>
 *   <li>1.0.0</li>
 *   <li>2.1.3</li>
 *   <li>1.0.0-alpha</li>
 *   <li>1.0.0-beta.1</li>
 *   <li>1.0.0-rc.1+sha.abc123</li>
 *   <li>1.0.0+123</li>
 * </ul>
 *
 * @author Ulysses R. Ribeiro
 * @see <a href="https://semver.org/">Semantic Versioning Specification</a>
 */
@RequiredArgsConstructor
@Data
public final class SemanticVersion {

    /**
     * The original version string as it was parsed.
     * This preserves the exact input format including any build metadata or suffix.
     */
    private final String originalVersion;

    /**
     * The major version number.
     */
    private final int major;

    /**
     * The minor version number.
     */
    private final int minor;

    /**
     * The patch version number.
     */
    private final int patch;

    /**
     * The version suffix.
     */
    private final String suffix;

}
