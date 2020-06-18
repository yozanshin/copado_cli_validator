package copado.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import copado.exception.CopadoYamlValidationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class YamlProcessor {
    private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    private static final String EXTENSION_REGEX = ".*[\\W].*";
    private static final String INVALID_EXTENSION_PATTERN = "Extensions must be a string without symbols: %s";

    /**
     * Validate whether a copado yaml file is valid. Validate yaml structure and
     * regex expressions.
     *
     * @param yamlFile Is the yaml file including the file name.
     * @return ValidatedReplacementYaml that contains information on validation errors and the ReplacementRules to apply
     */
    public YamlValidations isValid(final File yamlFile) {
        YamlValidations yamlReplacementValidationResult = new YamlValidations();
        ArrayList<YamlValidationMessage> yamlValidationMessages = new ArrayList<>();

        try {
            CopadoYaml yaml = parseYamlFile(yamlFile);
            validateStructure(yaml);
            validateRegExs(yaml);
            validateRules(yaml);
        } catch (CopadoYamlValidationException ex) {
            YamlValidationMessage validationMessage = parseValidationExceptionToMessage(ex);
            yamlValidationMessages.add(validationMessage);
        }

        yamlReplacementValidationResult.setYamlValidationMessages(yamlValidationMessages);
        return yamlReplacementValidationResult;
    }

    private void validateStructure(final CopadoYaml yaml) {
        if (yaml.rules == null || yaml.regex_lib == null) {
            throw new CopadoYamlValidationException("root", "YAML is not compliance with required structure");
        }
    }

    private CopadoYaml parseYamlFile(final File yamlFile) {
        validateFile(yamlFile);
        return parseFileToYaml(yamlFile);
    }

    private void validateFile(final File yamlFile) {
        if (!yamlFile.isFile() || !yamlFile.canRead()) {
            System.err.println("Unable to read file " + yamlFile);
            throw new CopadoYamlValidationException("root", "Unable to open YAML");
        }
    }

    private CopadoYaml parseFileToYaml(final File yamlFile) {
        try {
            yamlMapper.findAndRegisterModules();
            return yamlMapper.readValue(yamlFile, CopadoYaml.class);
        } catch (IOException ex) {
            System.err.println("Unexpected error reading file " + yamlFile);
            ex.printStackTrace();
            throw new CopadoYamlValidationException("root", "YAML is not compliance with required structure");
        }
    }

    private void validateRegExs(final CopadoYaml yaml) {
        yaml.getRegex_lib().values().stream()
                .filter(this::validateRegEx)
                .findFirst()
                .ifPresent(value -> {
                    throw new CopadoYamlValidationException("invalid_regex", "Regular Expression is not valid");
                });
    }

    private void validateRules(final CopadoYaml yaml) {
        validateEmptyRegExNames(yaml);
        validateInvalidRegExNames(yaml);
        validateNoFilenameOrExtensions(yaml);
        validateFilenameAndExtensions(yaml);
        validateInvalidExtensions(yaml);
        validateBranchesAndExclusions(yaml);
    }

    private boolean validateRegEx(final String regEx) {
        try {
            Pattern.compile(regEx);
            return false;
        } catch (PatternSyntaxException ex) {
            return true;
        }
    }

    private void validateEmptyRegExNames(final CopadoYaml yaml) {
        yaml.getRules().values().stream()
                .filter(rule -> rule.getRegex_name() == null)
                .findFirst()
                .ifPresent(value -> {
                    throw new CopadoYamlValidationException("empty_regex_name", "Regex node must be defined");
                });
    }

    private void validateInvalidRegExNames(final CopadoYaml yaml) {
        yaml.getRules().values().stream()
                .filter(rule -> !yaml.getRegex_lib().containsKey(rule.getRegex_name()))
                .findFirst()
                .ifPresent(value -> {
                    throw new CopadoYamlValidationException("invalid_regex_name", "Regular expression name in this rule was not found");
                });
    }

    private void validateNoFilenameOrExtensions(final CopadoYaml yaml) {
        yaml.getRules().values().stream()
                .filter(rule -> rule.getFile_names() == null && rule.getExtensions() == null)
                .findFirst()
                .ifPresent(value -> {
                    throw new CopadoYamlValidationException("no_filename_or_extensions", "At least file_names or extensions node must be defined");
                });
    }

    private void validateFilenameAndExtensions(final CopadoYaml yaml) {
        yaml.getRules().values().stream()
                .filter(value -> value.getFile_names() != null && value.getExtensions() != null)
                .findFirst()
                .ifPresent(value -> {
                    throw new CopadoYamlValidationException("filename_and_extensions", "Is not possible to use file_names and extensions nodes at same time");
                });
    }

    private void validateInvalidExtensions(final CopadoYaml yaml) {
        yaml.getRules().values().stream()
                .filter(value -> value.getExtensions() != null)
                .flatMap(value -> value.getExtensions().stream())
                .filter(extension -> extension.matches(EXTENSION_REGEX))
                .findFirst()
                .ifPresent(extension -> {
                    throw new CopadoYamlValidationException("invalid_extension",
                            String.format(INVALID_EXTENSION_PATTERN, extension));
                });
    }

    private void validateBranchesAndExclusions(final CopadoYaml yaml) {
        yaml.getRules().values().stream()
                .filter(value -> value.getBranches() != null && value.getExclusion_branches() != null)
                .findFirst()
                .ifPresent(value -> {
                    throw new CopadoYamlValidationException("branches_and_exclusion", "Is not possible to use branches and exclusion_branches nodes at same time");
                });
    }

    private YamlValidationMessage parseValidationExceptionToMessage(final CopadoYamlValidationException exception) {
        YamlValidationMessage validationMessage = new YamlValidationMessage();
        validationMessage.setNode(exception.getNode());
        validationMessage.setMessage(exception.getMessage());
        return validationMessage;
    }
}