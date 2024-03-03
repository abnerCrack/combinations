package combinations.creator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import combinations.models.CombinationResult;
import combinations.models.Configuration;
import combinations.models.WhitelistRule;

public abstract class CombinationCreator {
  protected Configuration configuration;
  protected boolean applyWhitelist;

  public CombinationCreator(Configuration configuration, boolean applyWhitelist) {
    this.configuration = configuration;
    this.applyWhitelist = applyWhitelist;
  }

  /** 根据输入字段生成组合, 子类实现 */
  public abstract CombinationResult createCombinations(Set<String> fieldsToCombine);

  /** 判断组合是否有效 */
  protected boolean isCombinationValid(Map<String, String> combination, boolean applyWhitelist) {
    // 如果不应用白名单，则认为组合有效
    if (!applyWhitelist) {
      return true;
    }

    /*
     *  遍历白名单，如果有一条规则匹配，则认为组合有效
     *  combination 例子 ： {aaaaaa=0, bbbbbb=0, cccccc=1}
     */
    for (Map.Entry<String, List<WhitelistRule>> ruleItem :
        configuration.getWhitelistRules().entrySet()) {
      String fieldName = ruleItem.getKey();
      // 从组合结果中获取这个字段的值
      String fieldValue = combination.get(fieldName);
      // 没有这个值直接默认非法
      if (fieldValue == null) {
        return false;
      }

      // aaaaaaa 字段对应的白名单列表
      List<WhitelistRule> rules = ruleItem.getValue();
      for (WhitelistRule rule : rules) {
        // 如果白名单规则的 value 与组合结果中的字段值不匹配，则跳过
        if (!fieldValue.equals(rule.getValue())) {
          continue;
        }
        String allowedEnums = combination.get(rule.getRelationKey());
        // 如果没有这个值或者这个值不在白名单中，则认为组合非法
        if (allowedEnums == null || !rule.getAllowedEnums().contains(allowedEnums)) {
          return false;
        }
      }
    }
    return true;
  }
}