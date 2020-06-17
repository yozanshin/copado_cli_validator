package copado.yaml;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class YamlProcessorTest {
    private YamlProcessor yamlProcessor;
    private ClassLoader classLoader;

    private final static String INVALID_PATH_DIRECTORY = "yaml/invalid";
    private final static String INVALID_YAML = "yaml/invalid/InvalidYaml.yml";
    private final static String NOT_LIST_REPLACE_VALUE = "yaml/invalid/NotListReplaceValue.yml";
    private final static String INVALID_REGEX_YAML = "yaml/invalid/InvalidRegexYaml.yml";
    private final static String NO_REGEX_IN_RULE = "yaml/invalid/NoRegexInRule.yml";
    private final static String NO_FILENAME_OR_EXTENSIONS = "yaml/invalid/NoFileNameOrExtensions.yml";
    private final static String FILENAME_AND_EXTENSIONS = "yaml/invalid/FileNameAndExtensions.yml";
    private final static String INVALID_EXTENSIONS = "yaml/invalid/InvalidExtensions.yml";
    private final static String BRANCHES_AND_EXClUSION = "yaml/invalid/BranchesAndExclusion.yml";
    private final static String RULE_WITH_NO_REGEX = "yaml/invalid/RuleWithNoRegexAssociated.yml";

    private final static String VALID_YAML = "yaml/valid/ValidYaml.yml";
    private final static String VALID_YAML_WITH_BRANCHES = "yaml/valid/ValidYamlWithBranches.yml";
    private final static String VALID_YAML_WITH_EMPTY_REPLACE_VALUE = "yaml/valid/ValidYamlWithEmptyReplaceValue.yml";
    private final static String VALID_YAML_WITHOUT_REPLACE_VALUE = "yaml/valid/ValidYamlWithoutReplaceValue.yml";
    private final static String VALID_YAML_WITHOUT_REPLACE_NOR_REPLACE_WITH = "yaml/valid/ValidYamlWithoutReplaceValueAndWithoutReplaceWith.yml";

    @Before
    public void setUp() {
        classLoader = getClass().getClassLoader();
        yamlProcessor = new YamlProcessor();
    }

    @Test
    public void testIsValidYaml_WhenYamlFileDoesntExist_ShouldRetrieveFalseWithNodeAndMessage() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(INVALID_PATH_DIRECTORY).getPath()));
        assertFalse("Yaml must be syntactically invalid", !validations.hasErrors());
        assertEquals("Validation node must be 'root'", validations.getYamlValidationMessages().get(0).getNode(), "root");
        assertEquals("Validation message must be 'Unable to open YAML'", validations.getYamlValidationMessages().get(0).getMessage(), "Unable to open YAML");
    }

    @Test
    public void testIsValidYaml_WhenExistInvalidYamlFile_ShouldRetrieveFalseWithNodeAndMessage() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(INVALID_YAML).getPath()));
        assertFalse("Yaml must be syntactically invalid", !validations.hasErrors());
        assertEquals("Validation node must be 'root'", validations.getYamlValidationMessages().get(0).getNode(), "root");
        assertEquals("Validation message must be 'YAML is not compliance with required structure'", validations.getYamlValidationMessages().get(0).getMessage(), "YAML is not compliance with required structure");
    }

    @Test
    public void testIsValidYaml_WhenNotListReplaceValue_ShouldRetrieveFalseWithNodeAndMessage() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(NOT_LIST_REPLACE_VALUE).getPath()));
        assertFalse("Yaml must be syntactically invalid", !validations.hasErrors());
        assertEquals("Validation node must be 'root'", validations.getYamlValidationMessages().get(0).getNode(), "root");
        assertEquals("Validation message must be 'YAML is not compliance with required structure'", validations.getYamlValidationMessages().get(0).getMessage(), "YAML is not compliance with required structure");
    }

    @Test
    public void testIsValidYaml_WhenExistInvalidRegexExpressions_ShouldRetrieveFalseWithNodeAndMessage() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(INVALID_REGEX_YAML).getPath()));
        assertFalse("Yaml must be invalid", !validations.hasErrors());
        assertEquals("Validation node must be 'invalid_regex'", validations.getYamlValidationMessages().get(0).getNode(), "invalid_regex");
        assertEquals("Validation message must be 'Regular Expression is not valid'", validations.getYamlValidationMessages().get(0).getMessage(), "Regular Expression is not valid");
    }

    @Test
    public void testIsValidYaml_WhenNoRegex_ShouldRetrieveFalseWithNodeAndMessage() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(NO_REGEX_IN_RULE).getPath()));
        assertFalse("Yaml must be invalid", !validations.hasErrors());
        assertEquals("Validation node must be 'empty_regex_name'", validations.getYamlValidationMessages().get(0).getNode(), "empty_regex_name");
        assertEquals("Validation message must be 'Regex node must be defined'", validations.getYamlValidationMessages().get(0).getMessage(), "Regex node must be defined");
    }

    @Test
    public void testIsValidYaml_WhenNoRegexForARule_ShouldRetrieveFalseWithNodeAndMessage() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(RULE_WITH_NO_REGEX).getPath()));
        assertFalse("Yaml must be invalid", !validations.hasErrors());
        assertEquals("Validation node must be 'invalid_regex_name'", validations.getYamlValidationMessages().get(0).getNode(), "invalid_regex_name");
        assertEquals("Validation message must be 'Regular expression name in this rule was not found'", validations.getYamlValidationMessages().get(0).getMessage(), "Regular expression name in this rule was not found");
    }

    @Test
    public void testIsValidYaml_WhenNoFileNameOrExtensions_ShouldRetrieveFalseWithNodeAndMessage() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(NO_FILENAME_OR_EXTENSIONS).getPath()));
        assertFalse("Yaml must be invalid", !validations.hasErrors());
        assertEquals("Validation node must be 'no_filename_or_extensions'", validations.getYamlValidationMessages().get(0).getNode(), "no_filename_or_extensions");
        assertEquals("Validation message must be 'At least file_names or extensions node must be defined'", validations.getYamlValidationMessages().get(0).getMessage(), "At least file_names or extensions node must be defined");
    }

    @Test
    public void testIsValidYaml_WhenFileNameAndExtensions_ShouldRetrieveFalseWithNodeAndMessage() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(FILENAME_AND_EXTENSIONS).getPath()));
        assertFalse("Yaml must be invalid", !validations.hasErrors());
        assertEquals("Validation node must be 'filename_and_extensions'", validations.getYamlValidationMessages().get(0).getNode(), "filename_and_extensions");
        assertEquals("Validation message must be 'Is not possible to use file_names and extensions nodes at same time'", validations.getYamlValidationMessages().get(0).getMessage(), "Is not possible to use file_names and extensions nodes at same time");
    }

    @Test
    public void testIsValidYaml_WhenExtensionsAreIncorrect_ShouldRetrieveFalseWithNodeAndMessage() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(INVALID_EXTENSIONS).getPath()));
        assertFalse("Yaml must be invalid", !validations.hasErrors());
        assertEquals("Validation node must be 'invalid_extension'", validations.getYamlValidationMessages().get(0).getNode(), "invalid_extension");
        assertEquals("Validation message must be 'Extensions must be a string without symbols: permissi$onse%t'", validations.getYamlValidationMessages().get(0).getMessage(), "Extensions must be a string without symbols: permissi$onse%t");
    }

    @Test
    public void testIsValidYaml_WhenBranchesAndExclusions_ShouldRetrieveFalseWithNodeAndMessage() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(BRANCHES_AND_EXClUSION).getPath()));
        assertFalse("Yaml must be invalid", !validations.hasErrors());
        assertEquals("Validation node must be 'invalid_extension'", validations.getYamlValidationMessages().get(0).getNode(), "branches_and_exclusion");
        assertEquals("Validation message must be 'Is not possible to use branches and exclusion_branches nodes at same time'", validations.getYamlValidationMessages().get(0).getMessage(), "Is not possible to use branches and exclusion_branches nodes at same time");
    }

    @Test
    public void testIsValidYaml_WhenValidYaml_ShouldRetrieveTrue() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(VALID_YAML).getPath()));
        assertTrue("Yaml must be syntactically valid", !validations.hasErrors());
    }

    @Test
    public void testIsValidYaml_WhenValidYamlFileWithExclusion_ShouldRetrieveTrue() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(VALID_YAML_WITH_BRANCHES).getPath()));
        assertTrue("Yaml must be syntactically valid", !validations.hasErrors());
    }

    @Test
    public void testIsValidYaml_WhenValidYamlFileWithEmptyReplaceValue_ShouldRetrieveTrue() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(VALID_YAML_WITH_EMPTY_REPLACE_VALUE).getPath()));
        assertTrue("Yaml must be syntactically valid", !validations.hasErrors());
    }

    @Test
    public void testIsValidYaml_WhenValidYamlFileWithoutReplace_ShouldRetrieveTrue() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(VALID_YAML_WITHOUT_REPLACE_VALUE).getPath()));
        assertTrue("Yaml must be syntactically valid", !validations.hasErrors());
    }

    @Test
    public void testIsValidYaml_WhenValidYamlFileWithoutReplaceValueNorReplaceWith_ShouldRetrieveTrue() {
        YamlValidations validations = yamlProcessor.isValid(new File(classLoader.getResource(VALID_YAML_WITHOUT_REPLACE_NOR_REPLACE_WITH).getPath()));
        assertTrue("Yaml must be syntactically valid", !validations.hasErrors());
    }

}
