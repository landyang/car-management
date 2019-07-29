package com.example.service.impl;

import com.example.bean.entity.Car;
import com.example.bean.entity.Peccancy;
import com.example.dao.PeccancyDao;
import com.example.enums.JuheErrorCode;
import com.example.exception.JuheRequestException;
import com.example.exception.ListCarByIdsException;
import com.example.service.CarService;
import com.example.service.PeccancyService;
import com.example.utils.HttpUtil;
import com.example.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * 车辆违章service实现类
 * @author HaN
 * @create 2019-05-09 14:47
 */
@Service
@Transactional
public class PeccancyServiceImpl implements PeccancyService {

    @Autowired
    private PeccancyDao peccancyDao;
    @Autowired
    private CarService carServiceImpl;
    /**
     * 日志记录
     */
    private static final Logger log = LoggerFactory.getLogger(PeccancyServiceImpl.class);

    //违章查询相关设置
    /**
     *设置聚合数据全国车辆违章请求违章查询接口url
     */
    private static final String URL = "http://v.juhe.cn/wz/query";
    /**
     * 设置聚合数据全国车辆违章Key
     */
    @Value(value = "${juhe-app-key}")
    private String APPKEY;
    /**
     * 分批查询每组数量
     */
    public static final int groupSize = 500;

    /**
     * 批量查询违章记录，并录入数据库。
     *
     * 1.根据车辆id集合查询车辆信息。
     * 2.根据车辆信息分组。
     * 3.分组调用查询违章接口。
     * 4.将该组车辆的违章插入数据库。
     * 5.继续下一组。
     *
     * @param carIds 要查询违章的车辆id集合
     * @return 查询失败的车辆id集合
     */
    @Override
    public Set<Integer> insertPeccancies(List<Integer> carIds) throws ListCarByIdsException {
        //查询或插入车辆违章失败的车辆id集合failureCarIds
        Set<Integer> failureCarIds = new TreeSet<>();
        //根据车辆id集合查询所有车辆
        List<Car> cars = carServiceImpl.listCarByIds(carIds);
        //判断查询结果（如果为空证明数据库出错）
        if (cars!=null) {
            //如果批量查询数量小于每组数量
            if (cars.size()<=groupSize) {
                //调用查询车辆违章并添加数据库方法insertPeccanciesMethod
                Set<Integer> set = insertPeccanciesMethod(cars);
                //添加失败的车辆id集合
                failureCarIds.addAll(set);
            } else {
                //分批组数
                int groupNo = cars.size()/groupSize;
                //分组车辆集合
                List<Car> subList = null;
                //循环遍历组
                for (int i = 0; i < groupNo; i++) {
                    subList = cars.subList(0,groupSize);
                    Set<Integer> set = insertPeccanciesMethod(subList);
                    failureCarIds.addAll(set);
                    //去除cars中上一组车辆
                    cars.subList(0,groupSize).clear();
                }
                if (cars.size()>0) {
                    Set<Integer> set = insertPeccanciesMethod(subList);
                    failureCarIds.addAll(set);
                }
            }
        } else {
            //查询车辆信息失败，抛出自定义异常交给controller处理
            log.error("根据车辆id集合查询所有车辆失败！");
            throw new ListCarByIdsException("根据车辆id集合查询所有车辆失败！");
        }
        //@return 查询失败的车辆id集合
        return failureCarIds;
    }


    /**
     * 根据车辆集合，查询车辆违章并添加数据库
     * @param cars
     * @return 返回查询或插入车辆违章失败的车辆id集合failureCarIds
     */
    public Set<Integer> insertPeccanciesMethod(List<Car> cars) {
        //全部车辆违章集合peccancies
        List<Peccancy> peccancies = new ArrayList<>();
        //查询或插入车辆违章失败的车辆id集合failureCarIds
        Set<Integer> failureCarIds = new TreeSet<>();

        //循环遍历车辆，查询违章
        for (int i = 0; i < cars.size(); i++) {
            Car car =  cars.get(i);
            //调用sendGetToGetPeccanciesByCar获得车辆违章json
            String jsonStr = sendGetToGetPeccanciesByCar(car);
//                            String jsonStr = "{\n" +
//                                    "    \"resultcode\": \"200\",\n" +
//                                    "    \"reason\": \"查询成功\",\n" +
//                                    "    \"result\": {\n" +
//                                    "        \"province\": \"HN\",\n" +
//                                    "        \"city\": \"HN_ZZ\",\n" +
//                                    "        \"hphm\": \"豫A23QP6\",\n" +
//                                    "        \"hpzl\": \"02\",\n" +
//                                    "        \"lists\": [\n" +
//                                    "            {\n" +
//                                    "                \"date\": \"2019-03-25 17:02:38\",\n" +
//                                    "                \"area\": \"石化路（城东南路至中州大道）\",\n" +
//                                    "                \"act\": \"机动车违反禁令标线指示的\",\n" +
//                                    "                \"code\": null,\n" +
//                                    "                \"fen\": \"3\",\n" +
//                                    "                \"money\": \"100\",\n" +
//                                    "                \"handled\": \"0\",\n" +
//                                    "                \"archiveno\": \"\",\n" +
//                                    "                \"wzcity\": \"\"\n" +
//                                    "            },\n" +
//                                    "            {\n" +
//                                    "                \"date\": \"2019-03-25 12:24:29\",\n" +
//                                    "                \"area\": \"货站街（未来路至东明路）\",\n" +
//                                    "                \"act\": \"机动车违反禁令标线指示的\",\n" +
//                                    "                \"code\": null,\n" +
//                                    "                \"fen\": \"3\",\n" +
//                                    "                \"money\": \"100\",\n" +
//                                    "                \"handled\": \"0\",\n" +
//                                    "                \"archiveno\": \"\",\n" +
//                                    "                \"wzcity\": \"\"\n" +
//                                    "            }\n" +
//                                    "        ]\n" +
//                                    "    },\n" +
//                                    "    \"error_code\": 0\n" +
//                                    "}";

            //处理违章查询json结果(判断jsonStr是否为空，为空证明请求未知失败)
            if (jsonStr!=null) {
                String errorCode = JsonUtil.findValue(jsonStr, "error_code");
//                System.out.println("errorCode："+errorCode);
                if ("0".equals(errorCode)) {
                    /**
                     * 查询违章成功,处理违章数据
                     */
                    //处理违章数据，获取违章集合lists
                    String result = JsonUtil.findValue(jsonStr, "result");
                    String lists = JsonUtil.findValue(result,"lists");
                    if (!"[]".equals(lists)) {
                        //car的全部违章集合
                        List<Peccancy> peccanciesOfACar = JsonUtil.jsonToList(lists, Peccancy.class);
                        //为违章集合绑定车辆
                        for (Peccancy peccancy : peccanciesOfACar) {
                            peccancy.setCar(car);
                        }
                        //将car的全部违章集合peccanciesOfACar存入全部车辆违章集合peccancies
                        peccancies.addAll(peccanciesOfACar);
                    }
                } else {
                    //记录失败车辆id
                    failureCarIds.add(car.getCarId());
                    //查询违章失败，记录日志(调用logByErrorCode方法)
                    logByErrorCode(car,JuheErrorCode.getJuheErrorCode(errorCode));
                }
            } else {
                //记录失败车辆id
                failureCarIds.add(car.getCarId());
                log.error("车牌号为 "+car.getCarLicense()+" 的车辆请求查询违章失败，carId为 "+car.getCarId());
            }
        }

        /**
         *分类全部车辆违章集合peccancies(如果违章状态handled为0未处理，则执行插入，)
         */

        //如果违章集合不为空则将全部车辆违章集合peccancies插入数据库
        if (!peccancies.isEmpty()) {
            try {
                //插入成功
                peccancyDao.insertPeccancies(peccancies);
            } catch (Exception e) {
                //插入失败
                //记录失败车辆id
                for (Peccancy peccancy : peccancies) {
                    failureCarIds.add(peccancy.getCar().getCarId());
                }
                //插入数据库失败记录日志（全部车辆违章集合peccancies）
                log.error("存储失败的车辆违章集合："+peccancies.toString(),e);
            }
        }
        return failureCarIds;
    }


    /**
     * 根据车辆发送查询违章请求
     * @param car
     * @return json结果
     */
    public String sendGetToGetPeccanciesByCar(Car car) {
        String jsonStr = null;
        //获取车牌号
        String hphm = car.getCarLicense();
        //获取发动机号后6位
        String engineCode = car.getEngineCode();
        int engineCodeLength = engineCode.length();
        String engineno = null;
        if (engineCodeLength>6) {
            engineno = engineCode.substring(engineCodeLength-6,engineCodeLength);
        } else {
            engineno = engineCode;
        }
        //获取车架号后6位
        String carVin = car.getCarVin();
        int carVinLength = carVin.length();
        String classno = carVin.substring(carVinLength-6,carVinLength);
        //拼接请求字符串
        String param = "hphm={0}&engineno={1}&classno={2}&key={3}";
        param = MessageFormat.format(param,hphm,engineno,classno,APPKEY);
        //发送Http请求获取该车辆违章数据
        try {
            jsonStr = HttpUtil.sendGet(URL, param);
        } catch (IOException e) {
            //请求接口调用失败记录日志。
            log.error("IO Exception when send get request:" + URL, e);
        }
        return  jsonStr;
    }


    /**
     * 根据聚合数据全国车辆违章接口调用错误码记录错误日志。。。
     * 服务级错误码参照(error_code)
     * @param errorCode
     */
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


    /**
     * 聚合全国车辆违章接口剩余请求次数查询
     * @return
     */
    @Override
    public Integer howManyJuheRequstLimit() throws JuheRequestException {
        //返回结果
        Integer num = null;
        //请求url
        String statusUrl = "http://v.juhe.cn/wz/status";
        //请求param
        String param = "dtype={0}&key={1}";
        param = MessageFormat.format(param,"json",APPKEY);

        String jsonStr = null;
        try {
            jsonStr = HttpUtil.sendGet(statusUrl,param);
            System.out.println(jsonStr);
            String errorCode = JsonUtil.findValue(jsonStr, "error_code");
            if ("0".equals(errorCode)) {
                //请求成功
                String result = JsonUtil.findValue(jsonStr, "result");
                String surplus = JsonUtil.findValue(result, "surplus");
                num = Integer.parseInt(surplus.substring(1, surplus.length() - 1));
            } else if ("10012".equals(errorCode)) {
                //接口请求超过次数限制
                log.error("请求超过次数限制："+statusUrl);
                throw new JuheRequestException("请求超过次数限制");
            }
        } catch (IOException e) {
            //请求接口调用失败记录日志。
            log.error("IO Exception when send get request:" + URL, e);
        }
        return num;
    }

    @Override
    public List<Peccancy> getAllPeccanciesByComId(Integer comId) {
        return peccancyDao.getAllPeccanciesByComId(comId);
    }

    @Override
    public List<Peccancy> getAllPeccanciesByUnitId(Integer unitId) {
        return peccancyDao.getAllPeccanciesByUnitId(unitId);
    }

    @Override
    public Integer updatePeccanciesHandledByUnit(List<Integer> ids) {
        return peccancyDao.updatePeccanciesHandledByUnit(ids);
    }

    @Override
    public Integer updatePeccanciesHandledByCompany(List<Integer> ids) {
        return peccancyDao.updatePeccanciesHandledByCompany(ids);
    }

}