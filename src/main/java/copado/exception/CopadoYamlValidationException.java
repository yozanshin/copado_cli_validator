package copado.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Define a Copado YAML validation exception.
 */
@Getter
@AllArgsConstructor
public class CopadoYamlValidationException extends RuntimeException {

    /**
     * Node where the error was found
     */
    private String node;
    /**
     * Error message
     */
    private String message;
}
