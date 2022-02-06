package io.jenkins.libs.checkstyle;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.jupiter.api.Test;

class JenkinsJavadocCheckTest extends AbstractModuleTestSupport {

    @Test
    void missingSinceOnClass() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

        final String[] expected = {
                "3:1: javadoc.missingSince"
        };

        verify(checkConfig, getPath("MissingSinceOnClass.java"), expected);
    }

    @Test
    void missingSinceOnClassNoJavaDoc() {

    }

    @Test
    void missingSinceOnMethod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

        final String[] expected = {
                "9:1: javadoc.missingSince"
        };

        verify(checkConfig, getPath("MissingSinceOnMethod.java"), expected);
    }

    @Test
    void missingSinceOnPrivateMethodIsAllowed() {

    }

    @Test
    void missingSinceWhenRestrictedMethodIsAllowed() {

    }

    @Test
    void missingSinceWhenRestrictedClassIsAllowed() {

    }


    @Test
    void missingSinceOnMethodNoJavaDoc() {

    }

    @Test
    void atSincePresent() {

    }

    @Override
    protected String getPackageLocation() {
        return "io/jenkins/libs/checkstyle";
    }
}