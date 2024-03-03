package combinations.models;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class CombinationResult {
  private List<Map<String, String>> validCombination;
  private List<Map<String, String>> invalidCombination;

  public CombinationResult(
      List<Map<String, String>> validCombinations, List<Map<String, String>> invalidCombinations) {
    this.validCombination = validCombinations;
    this.invalidCombination = invalidCombinations;
  }
}
