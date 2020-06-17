package copado;

import copado.yaml.YamlProcessor;
import copado.yaml.YamlValidationMessage;
import copado.yaml.YamlValidations;

import java.io.File;
import java.io.PrintStream;

import static java.util.Arrays.asList;

public class App {

    private static Log log = new Log(System.out, System.err);
    private static YamlProcessor yamlProcessor = new YamlProcessor();

    public static void main(String[] args) {
        if (args.length > 0) {
            File yamlFile = obtainYamlFile(args);
            YamlValidations valid = yamlProcessor.isValid(yamlFile);
            printResultsAndExit(valid);
        } else {
            printUsage();
        }
    }

    private static File obtainYamlFile(String[] args) {
        String yamlPath = parse(args);
        if(yamlPath == null) {
            System.exit(1);
        }
        return new File(yamlPath);
    }

    private static String parse(String[] args) {
        int pos = 0;
        String arg = args[pos];
        if (asList("-h", "--help").contains(arg)) {
            printUsage();
            System.exit(1);
        } else if (asList("-f", "--file").contains(arg)) {
            int valuePos = pos + 1;
            if (valuePos >= args.length) {
                log.error("Missing argument for option -f/--file");
            } else {
                return args[valuePos];
            }
        } else {
            log.error("Unrecognized option: " + arg);
        }
        return null;
    }

    private static void printUsage() {
        log.error("");
        log.error("Usage: copado-yaml [options]");
        log.error("");
        log.error("Options:");
        log.error(" -f,--file <arg>       Path to Copado YAML file");
        log.error(" -h,--help             Display help information");
    }

    private static void printResultsAndExit(YamlValidations yamlValidation) {
        if(yamlValidation.hasErrors()) {
            log.error("YAML is not valid");
            for (YamlValidationMessage validation : yamlValidation.getYamlValidationMessages()) {
                log.error("Validation error on node: " + validation.getNode() + " with message: " + validation.getMessage());
            }
            System.exit(1);
        } else {
            log.info("YAML is Copado Compliant!");
        }
        System.exit(0);
    }

    private static class Log {
        private PrintStream stdOut;
        private PrintStream stdErr;

        Log(PrintStream stdOut, PrintStream stdErr) {
            this.stdErr = stdErr;
            this.stdOut = stdOut;
        }

        void info(String message) {
            stdOut.println(message);
        }

        void error(String message) {
            stdErr.println(message);
        }
    }
}
