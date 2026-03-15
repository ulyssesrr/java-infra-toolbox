# Java Infra Toolbox

Small infrastructure utilities for Java applications.

## Features

- Exception fingerprinting
- Semantic Version parsing
- SemVer numeric/string encoding
- MDC logging helpers
- Logger adapters (SLF4J / reload4j)

## Modules

### java-infra-toolbox-jdk6

Compatible with Java 6.

Includes:

- JDK fingerprint strategy
- Guava fingerprint strategy (optional)
- SemanticVersion
- Semver encoders
- Logger adapters
- MDC exception logger

### java-infra-toolbox-jdk8

Adds:

- MdcScope
- semver4j integration

## Dependency

```xml
<dependency>
  <groupId>io.github.ulyssesrr</groupId>
  <artifactId>java-infra-toolbox</artifactId>
  <version>1.0.0</version>
</dependency>
```

