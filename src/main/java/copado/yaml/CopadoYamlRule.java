package copado.yaml;

import lombok.Data;

import java.util.List;

/**
 * Define a Copado YAML rule structure. The attributes class name must be the same than YAML because parser.
 *
 * @see <a href="http://docs.copa.do/copado/global-find-and-replace-rules"> See Global Find and Replace Rules</a>
 */
@Data
public class CopadoYamlRule {
    /**
     * A list of the exact names of the metadata files where the find and replace will be executed (e.g. "Account.object", "MyCustomLayout.layout", etc.).
     */
    List<String> file_names;
    /**
     * A list of the metadata file extensions where the find and replace will be executed (e.g. "object", "layout", etc.)
     */
    List<String> extensions;
    /**
     * A list of values to replace in the regex expression (___REPLACEVALUE___)
     */
    List<String> replace_values;
    /**
     * The value that will replace the text that is found by the regex expression
     */
    String replace_with;
    /**
     *  The list of Git branches where the rule applies. If left blank, the rule applies to all Git branches.
     */
    List<String> branches;
    /**
     *  The list of Git branches where the rule does not apply. If left blank, the rule applies to all Git branches.
     */
    List<String> exclusion_branches;
    /**
     * The variable name of the regex expression defined in the regex_lib section
     */
    String regex_name;
}
