package copado.yaml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YamlValidations {
    private List<YamlValidationMessage> yamlValidationMessages;

    public boolean hasErrors(){
        return !yamlValidationMessages.isEmpty();
    }
}
