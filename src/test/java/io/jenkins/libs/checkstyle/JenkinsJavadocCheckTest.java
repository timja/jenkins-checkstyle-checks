package io.jenkins.libs.checkstyle;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("JavaDoc checkstyle")
class JenkinsJavadocCheckTest extends AbstractModuleTestSupport {

    @Nested
    @DisplayName("on a class")
    class ClazzTest {
        @Test
        @DisplayName("is missing since")
        void missingSince() throws Exception {
            final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

            final String[] expected = {
                    "3:1: javadoc.missingSince"
            };

            verify(checkConfig, getPath("MissingSinceOnClass.java"), expected);
        }

        @Test
        @Disabled("Determine if we want to do this or just rely on other checks")
        @DisplayName("is missing javadoc")
        void noJavaDoc() throws Exception {

        }

        @Test
        @DisplayName("is allowed to not have since when it's restricted")
        void missingSinceWhenRestrictedIsAllowed() throws Exception {
            final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("MissingSinceOnClassRestricted.java"), expected);
        }
    }

    @Nested
    @DisplayName("on a method")
    class MethodTest {
        @Test
        @DisplayName("is missing since")
        void missingSince() throws Exception {
            final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

            final String[] expected = {
                    "9:1: javadoc.missingSince"
            };

            verify(checkConfig, getPath("MissingSinceOnMethod.java"), expected);
        }

        @Test
        @DisplayName("is allowed to not have since when it's private")
        void missingOnPrivateMethodIsAllowed() throws Exception {
            final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("MissingSinceOnPrivateMethod.java"), expected);
        }

        @Test
        @DisplayName("is allowed to not have since when it's restricted")
        void missingSinceWhenRestrictedIsAllowed() throws Exception {
            final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

//        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
            final String[] expected = {
                    "999:1: javadoc.missingSince"
            };

            verify(checkConfig, getPath("MissingSinceOnMethodRestricted.java"), expected);
        }

        @Test
        @Disabled("Determine if we want to do this or just rely on other checks")
        @DisplayName("is missing javadoc")
        void noJavaDoc() throws Exception {

        }


    }

    @Nested
    @DisplayName("on an interface")
    class InterfaceTest {
        @Test
        @DisplayName("is missing since")
        void missingSince() throws Exception {
            final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

            final String[] expected = {
                    "3:1: javadoc.missingSince"
            };

            verify(checkConfig, getPath("MissingSinceOnInterface.java"), expected);
        }

        @Test
        @DisplayName("is missing since on method")
        void missingSinceOnMethod() throws Exception {
            final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

            final String[] expected = {
                    "9:1: javadoc.missingSince"
            };

            verify(checkConfig, getPath("MissingSinceOnInterfaceMethod.java"), expected);
        }

        @Test
        @Disabled("Determine if we want to do this or just rely on other checks")
        @DisplayName("is missing javadoc")
        void noJavaDoc() throws Exception {

        }

        @Test
        @DisplayName("is allowed to not have since when it's restricted")
        void missingSinceWhenRestrictedIsAllowed() throws Exception {
            final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("MissingSinceOnInterfaceRestricted.java"), expected);
        }
    }

    @Nested
    @DisplayName("on an enum")
    class EnumTest {
        @Test
        @DisplayName("is missing since")
        void missingSince() throws Exception {
            final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

            final String[] expected = {
                    "3:1: javadoc.missingSince"
            };

            verify(checkConfig, getPath("MissingSinceOnEnum.java"), expected);
        }

        @Test
        @Disabled("Determine if we want to do this or just rely on other checks")
        @DisplayName("is missing javadoc")
        void noJavaDoc() throws Exception {

        }

        @Test
        @DisplayName("is allowed to not have since when it's restricted")
        void missingSinceWhenRestrictedIsAllowed() throws Exception {
            final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("MissingSinceOnEnumRestricted.java"), expected);
        }
    }

    @Test
    @DisplayName("has since present")
    void atSincePresent() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JenkinsJavadocCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("AtSinceOnClassAndMethod.java"), expected);
    }

    @Override
    protected String getPackageLocation() {
        return "io/jenkins/libs/checkstyle";
    }
}