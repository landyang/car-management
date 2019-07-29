package com.example.utils;

import com.example.bean.entity.Car;
import com.example.bean.entity.Peccancy;
import com.example.enums.JuheErrorCode;
import com.example.enums.ResultCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author HaN
 * @create 2019-04-09 20:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUtil {

    /**
     * 设置文件上传路径
     */
    @Value(value = "${upload-brandIcon-path}")
    private String filePath;
    @Value(value = "${juhe-app-key}")
    private String APPKEY;
    /**
     * 日志记录
     */
    private static Logger log = LoggerFactory.getLogger(TestUtil.class);


    @Test
    public void testResultUtil() {
        System.out.println(ResultUtil.warn(ResultCode.ERROR_DATA_NOTFIND).getCode());
        System.out.println(ResultUtil.warn(ResultCode.ERROR_DATA_NOTFIND).getCode() == 5002);
    }

    @Test
    public void testFileUtil() {
        System.out.println(FileUtil.getUUIDName("123.jpg"));
    }

    @Test
    public void test() {
        ArrayList<Object> objects = new ArrayList<>(0);
        System.out.println(objects.isEmpty());
        System.out.println(objects);

        ArrayList<Object> objects2 = new ArrayList<>();
        System.out.println(objects2.isEmpty());
        System.out.println(objects2);
    }

    @Test
    public void testJsonUtil() {
        String jsonStr = "{\n" +
                "    \"resultcode\": \"200\",\n" +
                "    \"reason\": \"查询成功\",\n" +
                "    \"result\": {\n" +
                "        \"province\": \"HN\",\n" +
                "        \"city\": \"HN_ZZ\",\n" +
                "        \"hphm\": \"豫A23QP6\",\n" +
                "        \"hpzl\": \"02\",\n" +
                "        \"lists\": [\n" +
                "            {\n" +
                "                \"date\": \"2019-03-25 17:02:38\",\n" +
                "                \"area\": \"石化路（城东南路至中州大道）\",\n" +
                "                \"act\": \"机动车违反禁令标线指示的\",\n" +
                "                \"code\": null,\n" +
                "                \"fen\": \"3\",\n" +
                "                \"money\": \"100\",\n" +
                "                \"handled\": \"0\",\n" +
                "                \"archiveno\": \"\",\n" +
                "                \"wzcity\": \"\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"date\": \"2019-03-25 12:24:29\",\n" +
                "                \"area\": \"货站街（未来路至东明路）\",\n" +
                "                \"act\": \"机动车违反禁令标线指示的\",\n" +
                "                \"code\": null,\n" +
                "                \"fen\": \"3\",\n" +
                "                \"money\": \"100\",\n" +
                "                \"handled\": \"0\",\n" +
                "                \"archiveno\": \"\",\n" +
                "                \"wzcity\": \"\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"error_code\": 0\n" +
                "}";
        /*String jsonStr = "{\n" +
                "    \"resultcode\": \"200\",\n" +
                "    \"reason\": \"查询成功\",\n" +
                "    \"result\": {\n" +
                "        \"province\": \"HN\",\n" +
                "        \"city\": \"HN_ZZ\",\n" +
                "        \"hphm\": \"豫A5B091\",\n" +
                "        \"hpzl\": \"02\",\n" +
                "        \"lists\": []\n" +
                "    },\n" +
                "    \"error_code\": 0\n" +
                "}";*/

        //获取lists
        String result = JsonUtil.findValue(jsonStr, "result");
        System.out.println(result);
        System.out.println("--------------------------------------------------");
        String lists = JsonUtil.findValue(result,"lists");
        System.out.println(lists);
        System.out.println("[]".equals(lists));
        System.out.println(!"[]".equals(lists));
        System.out.println("--------------------------------------------------");
        List<Peccancy> peccancies = new ArrayList<>();
        List<Peccancy> peccanciesOfACar = JsonUtil.jsonToList(lists, Peccancy.class);
        System.out.println(peccanciesOfACar);
        System.out.println(peccanciesOfACar.isEmpty());
        System.out.println(peccanciesOfACar.size());
        Car car = new Car();
        car.setCarId(1);
        for (Peccancy peccancy : peccanciesOfACar) {
            peccancy.setCar(car);
        }
        System.out.println(peccanciesOfACar);
        //测试向peccancies中追加peccanciesOfACar
        peccancies.addAll(peccanciesOfACar);
        peccancies.addAll(peccanciesOfACar);
        System.out.println(peccancies);

//        System.out.println("--------------------------------------------------");

        //测试error_code
//        String errorCode = JsonUtil.findValue(jsonStr, "error_code");
//        if ("0".equals(errorCode)) {
//            System.out.println("error_code=0");
//        }

        /*ObjectMapper mapper = new ObjectMapper();
        try {
            ObjectNode objectNode = mapper.readValue(jsonStr, ObjectNode.class);
            JsonNode lists = objectNode.findValue("lists");
            System.out.println(lists);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Set<Integer> set = new TreeSet<>();
        set.add(1);
        set.add(1);
        System.out.println("set："+set);
        System.out.println(set.isEmpty());
        set.clear();
        System.out.println(set.isEmpty());
    }

    @Test
    public void testHttpUtil() {
        String url = "http://v.juhe.cn/wz/status";
        String param = "key="+APPKEY;
        try {
            String s = HttpUtil.sendGet(url, param);
            System.out.println(s);
            String error_code = JsonUtil.findValue(s, "error_code");
            logByErrorCode(new Car(), error_code);
            System.out.println(error_code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJuheErrorCode() {
        String jsonStr = "{\n" +
                "    \"resultcode\": \"200\",\n" +
                "    \"reason\": \"成功返回\",\n" +
                "    \"result\": {\n" +
                "        \"surplus\": \"7\"\n" +
                "    },\n" +
                "    \"error_code\": 0\n" +
                "}";
//            System.out.println(JuheErrorCode.getJuheErrorCode(error_code));
//            logByErrorCode(new Car(),JuheErrorCode.getJuheErrorCode(error_code));
        System.out.println(JuheErrorCode.getJuheErrorCode("1"));
        System.out.println("------");
        System.out.println(JuheErrorCode.ERROR_CODE_203603.getCode().getClass());
        System.out.println(JuheErrorCode.ERROR_CODE_203603.getCode());

        logByErrorCode(new Car(), JuheErrorCode.getJuheErrorCode("1"));

    }

    public void logByErrorCode(Car car,String errorCode) {
        switch (errorCode) {
            case "203603":
                log.error("网络错误请重试(不计费)");
                break;
            case "203606":
                log.error("车辆信息错误：(请求校验后)(计费)："+"车牌号为 "+car.getCarLicense()+"，carId为 " +car.getCarId());
                break;
            case "203607":
                log.error("车辆信息错误：车架号发动机号车牌号错误(车辆信息错误，不扣费)："+"车牌号为 "+car.getCarLicense()+"，carId为 " +car.getCarId());
                break;
            case "203608":
                log.error("您好,你所查询的城市正在维护或未开通查询(如此城市维护或下线，不扣费)：");
                break;
            case "203609":
                log.error("内部错误,具体看返回(不计费)：");
                break;
            case "203611":
                log.error("根据车牌前缀获取查询规则相关错误(不计费)："+"车牌号为 "+car.getCarLicense()+"，carId为 " +car.getCarId());
                break;
            case "10001":
                log.error("错误的请求KEY：");
                break;
            case "10002":
                log.error("该KEY无请求权限");
                break;
            case "10003":
                log.error("KEY过期");
                break;
            case "10012":
                log.error("请求超过次数限制："+"车牌号为 "+car.getCarLicense()+"，carId为 " +car.getCarId());
                break;
            default:
                log.error("未知错误！："+"车牌号为 "+car.getCarLicense()+"，carId为 " +car.getCarId());
        }
    }

    public void logByErrorCode(Car car, JuheErrorCode errorCode) {
        if (errorCode == null) {
            log.error("未知错误！：" + "车牌号为 " + car.getCarLicense() + "，carId为 " + car.getCarId());
            return;
        }

        switch (errorCode) {
            case ERROR_CODE_203603:
                log.error(errorCode.getMessage());
                break;
            case ERROR_CODE_203606:
                log.error(errorCode.getMessage() + " 车牌号为 " + car.getCarLicense() + "，carId为 " + car.getCarId());
                break;
            case ERROR_CODE_203607:
                log.error(errorCode.getMessage() + " 车牌号为 " + car.getCarLicense() + "，carId为 " + car.getCarId());
                break;
            case ERROR_CODE_203608:
                log.error(errorCode.getMessage());
                break;
            case ERROR_CODE_203609:
                log.error(errorCode.getMessage());
                break;
            case ERROR_CODE_203611:
                log.error(errorCode.getMessage() + " 车牌号为 " + car.getCarLicense() + "，carId为 " + car.getCarId());
                break;
            case SYSTEM_ERROR_10001:
                log.error(errorCode.getMessage());
                break;
            case SYSTEM_ERROR_10002:
                log.error(errorCode.getMessage());
                break;
            case SYSTEM_ERROR_10003:
                log.error(errorCode.getMessage());
                break;
            case SYSTEM_ERROR_10012:
                log.error(errorCode.getMessage() + " 车牌号为 " + car.getCarLicense() + "，carId为 " + car.getCarId());
                break;
            default:
                log.error("未知错误！：" + " 车牌号为 " + car.getCarLicense() + "，carId为 " + car.getCarId());
        }
    }
}
