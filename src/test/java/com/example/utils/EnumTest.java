package com.example.utils;

import com.example.enums.JuheErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author HaN
 * @create 2019-05-12 17:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EnumTest {
    @Test
    public void testJuheErrorCode() {
        System.out.println(JuheErrorCode.getCode("SUCCESS"));
        System.out.println(JuheErrorCode.SUCCESS);
    }
}
