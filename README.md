# Java Infra Toolbox

A collection of small infrastructure utilities for Java applications, designed to help with exception tracking, semantic versioning, and logging.

## Features

- **Exception Fingerprinting** - Generate unique identifiers for exceptions to help identify recurring issues
- **Semantic Version Parsing** - Parse and handle semantic version strings
- **SemVer Encoding** - Encode versions as numbers, longs, or strings for storage/comparison
- **MDC Logging Helpers** - Enrich log events with MDC context (fingerprints, version info)
- **Logger Adapters** - Support for SLF4J and Log4j (reload4j)

## Modules

### java-infra-toolbox-jdk6

Compatible with Java 6 and above.

Includes:
- `ThrowableUtils` - Exception utilities including root cause detection
- `ExceptionFingerprinter` - Generate deterministic fingerprints from stack traces
- `SemanticVersion` - Semantic version representation
- `SemverEncoder` implementations - Encode versions as int/long/string
- `LoggerAdapter` - Abstraction over logging frameworks
- `MdcExceptionLogger` - Exception logger with MDC enrichment

### java-infra-toolbox-jdk8

Adds Java 8+ features:
- `MdcScope` - Tighter MDC scope management with try-with-resources
- `Semver4jParser` - Integration with semver4j library

## Usage

### MdcExceptionLogger

The `MdcExceptionLogger` enriches log events with MDC entries containing exception fingerprints and version information.

```java
import io.github.ulyssesrr.infratoolbox.logging.MdcExceptionLogger;
import io.github.ulyssesrr.infratoolbox.logging.adapter.Slf4jLoggerAdapter;
import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;

// Create the exception logger with default settings
MdcExceptionLogger logger = MdcExceptionLogger.builder()
    .adapter(new Slf4jLoggerAdapter(MyClass.class))
    .version(new SemanticVersion("1.2.3", 1, 2, 3, null))
    .build();

// Log an exception with MDC enrichment
try {
    // some code that throws an exception
} catch (Exception e) {
    logger.error("Operation failed", e);
}
```

The above code populates MDC with entries like:
- `exception_fingerprint` - fingerprint of the caught exception
- `root_exception_fingerprint` - fingerprint of the root cause
- `original_version` - "1.2.3"
- `encoded_version` - "0001.0002.0003" (or similar encoded form)

#### Customizing MDC Keys

```java
MdcExceptionLogger logger = MdcExceptionLogger.builder()
    .adapter(adapter)
    .fingerprintMdcKey("error_signature")
    .rootCauseFingerprintMdcKey("root_error_signature")
    .originalVersionMdcKey("app_version")
    .encodedVersionMdcKey("app_version_encoded")
    .build();
```

Set any key to `null` to disable that MDC entry.

### Exception Fingerprinting

```java
import io.github.ulyssesrr.infratoolbox.exception.fingerprint.DefaultExceptionFingerprinter;
import io.github.ulyssesrr.infratoolbox.exception.fingerprint.ExceptionFingerprinter;

ExceptionFingerprinter fingerprinter = DefaultExceptionFingerprinter.getInstance();

try {
    // code that throws
} catch (Exception e) {
    String fingerprint = fingerprinter.fingerprint(e);
    // Use fingerprint to identify recurring issues
}
```

### Semantic Version

```java
import io.github.ulyssesrr.infratoolbox.semver.SemanticVersion;
import io.github.ulyssesrr.infratoolbox.semver.encoder.SemverStringEncoder;

SemanticVersion version = new SemanticVersion("2.1.0-beta.1", 2, 1, 0, "beta.1");

// Compare versions
if (version.getMajor() > 1) {
    // major version change
}

// Encode for storage
String encoded = SemverStringEncoder.getInstance().toValue(version);
```

## Dependency

```xml
<dependency>
  <groupId>io.github.ulyssesrr</groupId>
  <artifactId>java-infra-toolbox</artifactId>
  <version>1.0.0</version>
</dependency>
```
