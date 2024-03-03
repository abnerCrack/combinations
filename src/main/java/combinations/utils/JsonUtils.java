package combinations.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    public static String convertObjectToJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
