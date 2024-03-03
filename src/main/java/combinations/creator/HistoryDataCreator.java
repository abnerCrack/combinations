package combinations.creator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import combinations.models.CombinationResult;
import combinations.models.Configuration;

public class HistoryDataCreator extends CombinationCreator {

  public HistoryDataCreator(Configuration configuration, boolean applyWhitelist) {
    super(configuration, applyWhitelist);
  }

  /** 不实现，这个类不会直接生成组合 */
  @Override
  public CombinationResult createCombinations(Set<String> fieldsToCombine) {
    throw new UnsupportedOperationException("Not supported by HistoricalDataProcessor.");
  }

  /** 兼容下之前的逻辑 */
  public CombinationResult processHistoricalData(List<Map<String, String>> allCombinations) {
    List<Map<String, String>> validCombinations = new ArrayList<>();
    List<Map<String, String>> invalidCombinations = new ArrayList<>();

    for (Map<String, String> combination : allCombinations) {
      // 使用已经存在的校验方法
      if (isCombinationValid(combination, this.applyWhitelist)) {
        validCombinations.add(combination);
      } else {
        invalidCombinations.add(combination);
      }
    }

    return new CombinationResult(validCombinations, invalidCombinations);
  }
}
