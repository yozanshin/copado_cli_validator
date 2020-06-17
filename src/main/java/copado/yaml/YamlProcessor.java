package copado.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.util.ArrayList;

public class YamlProcessor {
    private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    /**
     * Validate whether a copado yaml file is valid. Validate yaml structure and
     * regex expressions.
     *
     * @param yamlFile Is the yaml file including the file name.
     * @return ValidatedReplacementYaml that contains information on validation errors and the ReplacementRules to apply
     */
    public YamlValidations isValid(final File yamlFile) {
        // Replace this method logic to fulfil all the tests
        YamlValidations yamlReplacementValidationResult = new YamlValidations();
        ArrayList<YamlValidationMessage> yamlValidationMessages = new ArrayList<>();
        YamlValidationMessage yamlValidationMessage = new YamlValidationMessage();
        yamlValidationMessage.setMessage("Incorrect validation message");
        yamlValidationMessage.setMessage("Incorrect node");
        yamlValidationMessages.add(yamlValidationMessage);
        yamlReplacementValidationResult.setYamlValidationMessages(yamlValidationMessages);
        return yamlReplacementValidationResult;
    }
}