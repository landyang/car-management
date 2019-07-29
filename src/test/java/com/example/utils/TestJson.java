package com.example.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author HaN
 * @create 2019-05-12 18:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJson {
    @Test
    public void testJson1() {
        String jsonStr = "{\n" +
                "    \"resultcode\": \"200\",\n" +
                "    \"reason\": \"成功返回\",\n" +
                "    \"result\": {\n" +
                "        \"surplus\": \"8\"\n" +
                "    },\n" +
                "    \"error_code\": 0\n" +
                "}";
        String errorCode = JsonUtil.findValue(jsonStr, "error_code");
        System.out.println(errorCode);
        if ("0".equals(errorCode)) {
            String result = JsonUtil.findValue(jsonStr, "result");
            String surplus = JsonUtil.findValue(result, "surplus");
            String num = surplus.substring(1, surplus.length() - 1);
        } else {

        }


        System.out.println("--------------------------------------------------");

        String jsonStr2 = "{\n" +
                "\t\"resultcode\":\"112\",\n" +
                "\t\"reason\":\"request exceeds the limit\",\n" +
                "\t\"result\":null,\n" +
                "\t\"error_code\":10012\n" +
                "}";
        String errorCode2 = JsonUtil.findValue(jsonStr2, "error_code");
        if ("10012".equals(errorCode2)) {
            String result2 = JsonUtil.findValue(jsonStr2, "result");
            System.out.println("null".equals(result2));
            String surplus2 = JsonUtil.findValue(result2, "surplus");
            System.out.println(surplus2);
        }
    }
}
