package copado.yaml;

import lombok.Data;

import java.util.Map;

/**
 * Define a Copado YAML regex list and rule list structure.
 */
@Data
public class CopadoYaml {
    /**
     * Regular expression map.
     * The key is the regex name and the value is the regex itself.
     */
    Map<String, String> regex_lib;
    /**
     * Rules map.
     * The key is the rule name.
     * The value is the rule definition.
     */
    Map<String, CopadoYamlRule> rules;

}
