package combinations.creator;

import java.util.*;

import combinations.models.CombinationResult;
import combinations.models.Configuration;
import combinations.models.Field;

/**
 * CartesianCreator 笛卡尔积组合生成器
 *
 * @description 用于生成笛卡尔积的组合
 */
public class CartesianCreator extends CombinationCreator {
  public CartesianCreator(Configuration configuration, boolean applyWhitelist) {
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

    // 如果传入的 fieldsToCombine 为空，使用配置中的所有字段

    List<List<Map.Entry<String, String>>> allValueCombinations = new ArrayList<>();
    for (String fieldName : fieldsToCombine) {
      List<Field> fieldValues = configuration.getFields().get(fieldName);
      if (fieldValues == null || fieldValues.isEmpty()) {
        throw new IllegalArgumentException("Field " + fieldName + " does not have any values.");
      }
      List<Map.Entry<String, String>> fieldCombinations = new ArrayList<>();
      for (Field value : fieldValues) {
        fieldCombinations.add(new AbstractMap.SimpleEntry<>(fieldName, value.getValue()));
      }
      allValueCombinations.add(fieldCombinations);
    }

    generateCartesianProduct(
        allValueCombinations, 0, new LinkedHashMap<>(), validCombinations, invalidCombinations);

    return new CombinationResult(validCombinations, invalidCombinations);
  }

  private void generateCartesianProduct(
      List<List<Map.Entry<String, String>>> allValueCombinations,
      int depth,
      Map<String, String> currentCombination,
      List<Map<String, String>> validCombinations,
      List<Map<String, String>> invalidCombinations) {
    if (depth == allValueCombinations.size()) {
      if (isCombinationValid(currentCombination, applyWhitelist)) {
        validCombinations.add(new LinkedHashMap<>(currentCombination));
      } else {
        invalidCombinations.add(new LinkedHashMap<>(currentCombination));
      }
      return;
    }

    for (Map.Entry<String, String> valueEntry : allValueCombinations.get(depth)) {
      currentCombination.put(valueEntry.getKey(), valueEntry.getValue());
      generateCartesianProduct(
          allValueCombinations,
          depth + 1,
          currentCombination,
          validCombinations,
          invalidCombinations);
      currentCombination.remove(valueEntry.getKey());
    }
  }
}
