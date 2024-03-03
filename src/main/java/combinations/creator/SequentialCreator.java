package combinations.creator;

import combinations.models.CombinationResult;
import combinations.models.Configuration;
import combinations.models.Field;

import java.util.*;

public class SequentialCreator extends CombinationCreator {
  public SequentialCreator(Configuration configuration, boolean applyWhitelist) {
    super(configuration, applyWhitelist);
  }

  @Override
  public CombinationResult createCombinations(Set<String> fieldsToCombine) {
    List<Map<String, String>> validCombinations = new ArrayList<>();
    List<Map<String, String>> invalidCombinations = new ArrayList<>();
    if (fieldsToCombine == null || fieldsToCombine.isEmpty()) {
      fieldsToCombine = configuration.getFields().keySet();
    }
    if (fieldsToCombine.isEmpty()) {
      throw new IllegalArgumentException("The set of fields to combine cannot be empty.");
    }

    String mainField = fieldsToCombine.iterator().next();
    List<Field> mainFieldValues = configuration.getFields().get(mainField);
    if (mainFieldValues == null) {
      throw new IllegalArgumentException("Main field " + mainField + " does not exist or has no values.");
    }

    int numberOfCombinations = mainFieldValues.size();
    for (int i = 0; i < numberOfCombinations; i++) {
      Map<String, String> combination = new HashMap<>();
      for (String fieldName : fieldsToCombine) {
        List<Field> fieldValues = configuration.getFields().get(fieldName);
        if (fieldValues != null && fieldValues.size() > i) {
          combination.put(fieldName, fieldValues.get(i).getValue());
        } else {
          throw new IllegalArgumentException("Field " + fieldName + " does not have enough values to match the main field.");
        }
      }

      if (isCombinationValid(combination, applyWhitelist)) {
        validCombinations.add(combination);
      } else {
        invalidCombinations.add(combination);
      }
    }

    return new CombinationResult(validCombinations, invalidCombinations);
  }
}
