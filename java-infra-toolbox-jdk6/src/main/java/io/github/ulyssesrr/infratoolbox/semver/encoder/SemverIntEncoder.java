package io.github.ulyssesrr.infratoolbox.semver.encoder;

import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;
import lombok.Getter;

/**
 * Encodes a {@link SemanticVersion} into a 32-bit integer value using bit packing.
 *
 * <p>This encoder packs the MAJOR, MINOR, and PATCH version components into a single
 * integer value, allowing for efficient storage and comparison. The encoding preserves
 * the natural ordering of semantic versions - higher versions produce higher integer values.
 * The bit positions are determined by the configured bit counts for each component.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * SemanticVersion version = new SemanticVersion("1.2.3", 1, 2, 3, null);
 * SemverIntEncoder encoder = SemverIntEncoder.getInstance();
 * Integer encoded = encoder.toValue(version);
 * // encoded can be compared: version 2.0.0 > version 1.9.9
 * }</pre>
 *
 * <p>Example with custom bit allocation:</p>
 * <pre>{@code
 * // Use 16 bits for major, 8 for minor, 8 for patch
 * SemverIntEncoder encoder = new SemverIntEncoder(16, 8, 8);
 * Integer encoded = encoder.toValue(new SemanticVersion("3000.200.50", 3000, 200, 50, null));
 * }</pre>
 *
 * @author Ulysses R. Ribeiro
 * @see SemanticVersion
 * @see SemverEncoder
 * @see SemverLongEncoder
 * @see SemverStringEncoder
 * @see <a href="https://semver.org/">Semantic Versioning Specification</a>
 */
@Getter
public final class SemverIntEncoder implements SemverEncoder<Integer> {

    /**
     * The number of bits allocated to the MAJOR version component.
     */
    private final int majorBits;

    /**
     * The number of bits allocated to the MINOR version component.
     */
    private final int minorBits;

    /**
     * The number of bits allocated to the PATCH version component.
     */
    private final int patchBits;

    /**
     * Returns a singleton instance with default bit allocation (11, 11, 10).
     *
     * @return the singleton encoder instance
     */
    public static SemverIntEncoder getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final SemverIntEncoder INSTANCE = new SemverIntEncoder(11, 11, 10);
    }

    /**
     * Creates a new SemverIntEncoder with the specified bit allocation.
     *
     * <p>The sum of majorBits, minorBits, and patchBits must equal 32.</p>
     *
     * @param majorBits the number of bits to allocate for the MAJOR version (must be positive)
     * @param minorBits the number of bits to allocate for the MINOR version (must be positive)
     * @param patchBits the number of bits to allocate for the PATCH version (must be positive)
     * @throws IllegalArgumentException if the sum of bits is not 32
     */
    public SemverIntEncoder(int majorBits, int minorBits, int patchBits) {
        if (majorBits + minorBits + patchBits != 32) {
            throw new IllegalArgumentException("majorBits + minorBits + patchBits must equal 32");
        }
        this.majorBits = majorBits;
        this.minorBits = minorBits;
        this.patchBits = patchBits;
    }

    /**
     * Encodes the given semantic version into a 32-bit integer value.
     *
     * <p>The version components are packed into the integer using the configured bit counts.
     * The MAJOR component occupies the most significant bits, followed by MINOR, then PATCH.
     * The encoding preserves version ordering - comparing the encoded integers will produce
     * the same result as comparing the original semantic versions.</p>
     *
     * @param v the semantic version to encode, cannot be {@code null}
     * @return the encoded integer value representing the semantic version
     */
    public Integer toValue(SemanticVersion v) {
        int major = v.getMajor() << (minorBits + patchBits);
        int minor = v.getMinor() << patchBits;
        int patch = v.getPatch();

        return major | minor | patch;
    }
}
