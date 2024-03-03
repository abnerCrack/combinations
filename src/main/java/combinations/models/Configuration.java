package combinations.models;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Configuration {
  Map<String, List<Field>> fields;
  Map<String, List<WhitelistRule>> whitelistRules;
}
