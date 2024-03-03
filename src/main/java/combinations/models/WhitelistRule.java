package combinations.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WhitelistRule {
    @JsonProperty("relationKey")
    private String relationKey;

    @JsonProperty("allowedEnums")
    private List<String> allowedEnums;

    @JsonProperty("type")
    private String type;

    @JsonProperty("value")
    private String value;
}
