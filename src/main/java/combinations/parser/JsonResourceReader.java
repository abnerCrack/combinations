package combinations.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JsonResourceReader {

    private final ObjectMapper objectMapper;

    public JsonResourceReader() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 从资源目录读取JSON文件并将其转换为指定类型的对象。
     * 
     * @param fileName 资源目录下的JSON文件名
     * @param typeReference 要转换为的对象类型的引用
     * @param <T> 返回对象的类型
     * @return 从JSON文件解析出的对象
     * @throws IOException 如果发生IO错误或解析错误
     */
    public <T> T readFromResource(String fileName, TypeReference<T> typeReference) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + fileName);
        }
        try {
            return objectMapper.readValue(inputStream, typeReference);
        } finally {
            inputStream.close();
        }
    }
}
