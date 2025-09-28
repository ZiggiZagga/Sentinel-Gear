    /**
     * Intention: The pom.xml should always contain a <name> element with the project name.
     * This test enforces that the project is clearly named for users and tools.
     *
     * NOTE: This test may not run if Maven dependencies are broken. It is written for future enforcement.
     * TODO: Update the name if the project name changes.
     */
    @Test
    void pomXmlShouldContainName() throws Exception {
        String pom = Files.readString(Paths.get("pom.xml"));
        assertTrue(pom.contains("<name>sentinel-gear</name>"),
            "pom.xml should contain a <name> element with the project name");
    }

    /**
     * Intention: The pom.xml should always contain a <description> element with a meaningful project description.
     * This test enforces that the project is discoverable and understandable for users and contributors.
     *
     * NOTE: This test may not run if Maven dependencies are broken. It is written for future enforcement.
     * TODO: Update the description if the project description changes.
     */
    @Test
    void pomXmlShouldContainDescription() throws Exception {
        String pom = Files.readString(Paths.get("pom.xml"));
        assertTrue(pom.contains("<description>Spring Cloud Gateway–based service hardened for zero-trust environments."),
            "pom.xml should contain a <description> element with a meaningful project description");
    }

    /**
     * Intention: The pom.xml should always contain a <properties> block with required build properties.
     * This test enforces that key build properties (like java.version) are explicitly set for reproducibility.
     *
     * NOTE: This test may not run if Maven dependencies are broken. It is written for future enforcement.
     * TODO: Update the properties if build requirements change.
     */
    @Test
    void pomXmlShouldContainPropertiesBlock() throws Exception {
        String pom = Files.readString(Paths.get("pom.xml"));
        assertTrue(pom.contains("<properties>") && pom.contains("<java.version>24</java.version>"),
            "pom.xml should contain a <properties> block with required build properties");
    }
package com.ironbucket.sentinelgear;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PomMetadataTest {
    /**
     * Intention: The pom.xml should always contain a <url> element pointing to the canonical project homepage or repository.
     * This test enforces discoverability and proper OSS metadata for future releases.
     *
     * NOTE: This test may not run if Maven dependencies are broken. It is written for future enforcement.
     * TODO: Update the URL if the canonical project homepage changes.
     */
    @Test
    void pomXmlShouldContainProjectUrl() throws Exception {
        String pom = Files.readString(Paths.get("pom.xml"));
        assertTrue(pom.contains("<url>https://example.com/your-project-homepage</url>"),
            "pom.xml should contain a <url> element with the canonical repository or homepage link");
    }

    /**
     * Intention: The pom.xml should always contain a <licenses> block with at least one valid license entry.
     * This test enforces open source compliance and clarity for users and contributors.
     *
     * NOTE: This test may not run if Maven dependencies are broken. It is written for future enforcement.
     * TODO: Update the license name and URL if the license changes.
     */
    @Test
    void pomXmlShouldContainLicenseBlock() throws Exception {
        String pom = Files.readString(Paths.get("pom.xml"));
        assertTrue(pom.contains("<licenses>") && pom.contains("<license>") && pom.contains("<name>Apache License, Version 2.0</name>"),
            "pom.xml should contain a <licenses> block with a valid license entry");
    }

    /**
     * Intention: The pom.xml should always contain a <developers> block with at least one developer entry.
     * This test enforces project ownership and contact discoverability.
     *
     * NOTE: This test may not run if Maven dependencies are broken. It is written for future enforcement.
     * TODO: Update the developer ID and organization if maintainers change.
     */
    @Test
    void pomXmlShouldContainDevelopersBlock() throws Exception {
        String pom = Files.readString(Paths.get("pom.xml"));
        assertTrue(pom.contains("<developers>") && pom.contains("<developer>") && pom.contains("<id>ziggizagga</id>"),
            "pom.xml should contain a <developers> block with at least one developer entry");
    }

    /**
     * Intention: The pom.xml should always contain an <scm> block with repository information.
     * This test enforces traceability and version control best practices.
     *
     * NOTE: This test may not run if Maven dependencies are broken. It is written for future enforcement.
     * TODO: Update the SCM URLs if the repository location changes.
     */
    @Test
    void pomXmlShouldContainScmBlock() throws Exception {
        String pom = Files.readString(Paths.get("pom.xml"));
        assertTrue(pom.contains("<scm>") && pom.contains("<url>https://example.com/your-project-homepage</url>"),
            "pom.xml should contain an <scm> block with repository information");
    }
}
