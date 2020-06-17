# Copado YAML CLI Validator

Copado offers a functionality that is called [Global Find and Replace Rules](https://docs.copado.com/article/ueos8qgp86-global-find-and-replace-rules ) that allow customers to define custom find and replace rules that applies whenever you commit files.

The rules must be written in a YAML file. This YAML file should be syntactically correct, and also semantically correct that makes it Copado Compliance YAML that we will see below.

[Click here to open a sample Copado.yml file](https://github.com/CopadoSolutions/Copado-Manual/blob/master/Copado.yml)

### Copado compliance YAML

In order to be Copado Compliance, the YAML file structure must have two sections: *regex_lib* and *rules*. These sections are configured as follows:

**regex_lib**:

The "regex_lib" section contains the 'find' regex expressions. These regex expressions are saved in a variable name so that they can be referenced in the rules section. 

In the following example, the 'find' regex expression for a field is assigned to a variable name called "field":

``` js
'(?s)(.[^\n]+<fields>(?:(?!<fields>).)*?<fullName>___REPLACEVALUE___</fullName>(?:(?!<fields>).)*?</fields>)'
```

**rules**:

The "rules" section contains one or more rules to be executed. A rule consists of a rule name and several rule parameters. The rule name cannot have spaces or special characters and the rule parameters are as follows:

- **file_names**
  - A list of the exact names of the metadata files where the find and replace will be executed (e.g. "Account.object", "MyCustomLayout.layout", etc.)
  - If you use file_names you cannot use extensions, but at least one of those is required
- **extensions**
  - A list of the metadata file extensions where the find and replace will be executed (e.g. "object", "layout", etc.)
  - Values are not able to have symbols, just numbers and letters
  - If you use extensions you cannot use file_names, but at least one of those is required
- **regex_name**
  - The variable name of the regex expression defined in the regex_lib section
  - Required value
- **replace_values**
  - A list of values to replace in the regex expression (\_\_\_REPLACEVALUE\_\_\_)
  - If you do not need to replace values, you can omit replace_values and the  \_\_\_REPLACEVALUE\_\_\_ text
- **replace_with**
  - The value that will replace the text that is found by the regex expression
  - Leaving this parameter blank means the matching regex will be deleted, instead of replaced with this
- **branches**
  - The list of Git branches where the rule applies. If left blank, the rule applies to all Git branches.
  - If you use branches you cannot use exclusion_branches, none is required
- **exclusion_branches**
  - The list of Git branches where the rule does not apply. If left blank, the rule applies to all Git branches
  - If you use exclusion_branches you cannot use branches, none is required

### Exercise

Copado would like to have a CLI tool to execute from command line and, specifying a YAML file, advice the customer about the YAML is Copado Compliance according to the rules defined above. 

The objective is to write the code needed on YamlProcessor to validate a YAML to be Copado Compliance.

The skeleton provided already have the minimal logic to handle CLI execution from Command line. Also a test class YamlProcessorTest is already defined with different YAML files that are provided as examples to help. 

**The exercise is considered finished if all tests that already exist on YamlProcessorTest passes.**

NOTES:
- Code quality is the first thing we will look at.
- Using Java 8 features add value in our revision.
- Adding more tests is recommended to check possible edge cases.
- CopadoYaml and CopadoYamlRule classes are provided to map from YAML into these classes, check them out.
- Take in consideration that output from YamlProcessor returns the node where the problem is with a concrete message (check the tests to know what to expect) 
- We recommend to use [Regexr](https://regexr.com/) to anything related to Regular Expressions, and [YAML Lint](http://www.yamllint.com/) for YAML

PS: Feel free to refactor the project to make it better :)

### How to execute

To execute the YAML CLI validator, project must be packaged so the jar can be executed. First we need to:

``` js
mvn clean package
```

After that, we just need to execute the jar specifying a YAML file to be validated, for example the ones for testing:

``` js
java -jar target/copado-yaml-cli-1.0.0-SNAPSHOT-jar-with-dependencies.jar -f src/test/resources/yaml/invalid/InvalidYaml.yml
```
