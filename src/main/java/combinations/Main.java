package combinations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import combinations.creator.CartesianCreator;
import combinations.creator.CombinationCreator;
import combinations.models.CombinationResult;
import combinations.models.Configuration;
import combinations.creator.HistoryDataCreator;
import combinations.parser.JsonResourceReader;

public class Main {
  static String file = "data.json";
  static JsonResourceReader reader = new JsonResourceReader();

  public static void main(String[] args) {
    try {
      TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {};
      Map<String, Object> json = reader.readFromResource(file, typeReference);
      ObjectMapper mapper = new ObjectMapper();
      Configuration bizData = mapper.convertValue(json.get("test_star"), Configuration.class);
      // 关闭白名单模拟生成所有场景
      CombinationCreator creator = new CartesianCreator(bizData, false);
      CombinationResult result = creator.createCombinations(new HashSet<>());
      List<Map<String, String>> allCombinations = result.getValidCombination();

      // System.out.println(valid);
      // CombinationCreator creator2 = new CartesianCreator(biz, true);
      // CombinationResult result2 = creator2.createCombinations(new HashSet<>());
      // List<Map<String, String>> valid2 = result2.getValidCombination();
      // System.out.println(result2);
      // // 全量数据 通过兼容函数生成
      HistoryDataCreator his = new HistoryDataCreator(bizData, true);
      CombinationResult historicalData = his.processHistoricalData(allCombinations);
      System.out.println("[🍊]符合条件的数据\n" + historicalData.getValidCombination());
      System.out.println("[🍉]不符合条件的数据\n" + historicalData.getInvalidCombination());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}