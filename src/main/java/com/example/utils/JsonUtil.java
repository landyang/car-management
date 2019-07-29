package com.example.utils;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * json转换工具类(json解析工具Jackson)
 *
 * @author HaN
 * @create 2019-05-09 22:42
 */
public class JsonUtil {

    /**
     * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
     * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
     * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
     * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
     * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
     * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
     */

    /**
     * 初始化变量
     */
    private static final ObjectMapper mapper = new ObjectMapper();
    private static Logger log = LoggerFactory.getLogger(JsonUtil.class);

    static {
        // 解决实体未包含字段反序列化时抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 对于空的对象转json的时候不抛出错误
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // 允许属性名称没有引号
        mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        // 允许单引号
        mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
    }

    /**
     * 将一个object转换为json, 可以使一个java对象，也可以是集合
     * @param obj 传入的数据
     * @return
     */
    public static String objectToJson(Object obj) {
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("将一个object转换为json出现异常！", e);
        }
        return json;
    }

    /**
     * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
     */

    /**
     * 将json数据转化为对象
     * @param json json数据
     * @param beanType 转换的实体类型
     * @param <T>
     * @return
     */
    public static <T> T jsonToClass(String json, Class<T> beanType) {
        T t = null;
        try {
            t = mapper.readValue(json, beanType);
        } catch (Exception e) {
            log.error("将json数据转化为对象出现异常！", e);
        }
        return t;
    }

    /**
     * 将json数据转换成Map
     * @param json 转换的数据
     * @return
     */
    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> map = null;
        try {
            map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            log.error("将json数据转换成Map出现异常！", e);
        }
        return map;
    }

    /**
     * 将json数据转换成list
     * @param json 待转换的json数据
     * @param beanType List中数据类型
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> beanType) {
        List<T> list = null;
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
            list = mapper.readValue(json, javaType);
        } catch (Exception e) {
            log.error("将json数据转换成list出现异常！", e);
        }
        return list;
    }

    /**
     * 获取json对象数据的属性
     * @param resData 请求的数据
     * @param resPro 请求的属性
     * @return 返回String类型数据
     */
    public static String findValue(String resData, String resPro) {
        String result = null;
        try {
            JsonNode node = mapper.readTree(resData);
            JsonNode resProNode = node.get(resPro);
            result = JsonUtil.objectToJson(resProNode);
        } catch (Exception e) {
            log.error("获取json对象数据的属性出现异常！", e);
        }
        return result;
    }
}
