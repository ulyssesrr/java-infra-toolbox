package io.github.ulyssesrr.infratoolbox.semver.encoder;

import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;
import lombok.NonNull;

/**
 * Encodes a {@link SemanticVersion} into a comparable value of type {@code T}.
 *
 * <p>This interface provides a way to convert semantic versions into values that can be
 * compared and ordered. The encoding preserves the semantic version ordering rules:
 * MAJOR version &gt; MINOR version &gt; PATCH version.</p>
 *
 * <p>The encoded values maintain natural ordering, meaning:</p>
 * <ul>
 *   <li>Version 1.0.0 encodes to a value greater than 0.9.0</li>
 *   <li>Version 1.1.0 encodes to a value greater than 1.0.9</li>
 *   <li>Version 1.0.1 encodes to a value greater than 1.0.0</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * SemanticVersion version = new SemanticVersion("1.2.3", 1, 2, 3, null);
 * SemverEncoder<Integer> encoder = SemverIntEncoder.getInstance();
 * Integer encoded = encoder.toValue(version);
 * // encoded can be used for comparison, storage, or sorting
 * }</pre>
 *
 * @param <T> the type of the encoded value, must implement {@code Comparable<T>}
 * @author Ulysses R. Ribeiro
 * @see SemanticVersion
 * @see SemverIntEncoder
 * @see SemverLongEncoder
 * @see SemverStringEncoder
 * @see <a href="https://semver.org/">Semantic Versioning Specification</a>
 */
public interface SemverEncoder<T extends Comparable<T>> {

    /**
     * Encodes the given semantic version into a comparable value of type {@code T}.
     *
     * <p>The returned value maintains the natural ordering of semantic versions,
     * where higher versions produce higher encoded values. This allows for direct
     * comparison of encoded values to determine version ordering.</p>
     *
     * @param v the semantic version to encode, cannot be {@code null}
     * @return the encoded value representing the semantic version, never {@code null}
     */
    T toValue(@NonNull SemanticVersion v);
}
