package com.mljr.operators.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.utils.ShanghaiUtils;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.entity.model.operators.SMSInfo;
import com.mljr.operators.service.primary.operators.ISMSInfoService;
import com.mljr.redis.RedisClient;
import org.apache.poi.util.SystemOutLogger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by songchi on 17/2/17.
 */
public class ChinaMoblieTest  {

    @Autowired
    ChinaMobileService chinaMobileService;

    @Autowired
    private ISMSInfoService smsInfoService;

    @Test
    public void getAllInfos() throws Exception {

        Map<String, String> cookies = chinaMobileService.loginAndGetCookieMap("13681668945", "672440", "438237");


//        System.out.println(chinaMobileService.getUserInfo(cookies));
//
//        System.out.println(chinaMobileService.getPackageInfo(cookies));
//
//        System.out.println(chinaMobileService.getCostInfo(cookies));
//
//        System.out.println(chinaMobileService.getFlowInfo(cookies));
//
//        System.out.println(chinaMobileService.getSmsInfo(cookies));
//
//        System.out.println(chinaMobileService.getCallInfo(cookies));
    }

    @Test
    public void testName() throws Exception {
        System.out.println(OperatorsEnum.indexOf("联通"));
        System.out.println(OperatorsEnum.indexOf("移动"));
        System.out.println(OperatorsEnum.indexOf("电信"));
    }

    @Test
    public void test2() throws Exception {
        SMSInfo info = new SMSInfo();
        info.setUserInfoId(9991L);
        info.setSendNum("999");
        info.setSendTime(new Date());
        info.setSmsType("3");
        info.setBusinessType("5");
        List<SMSInfo> list = Lists.newArrayList(info, info, info, info);
        smsInfoService.insertByBatch(list);

    }

    @Test
    public void testGetUrls() throws Exception {
        List<RequestInfoDTO> list = ShanghaiUtils.getShanghaiUrls();
        for (RequestInfoDTO dto : list) {
            System.out.println(dto.getUrl());
        }

        System.out.println(list.size());

    }

    RedisClient redisClient = new RedisClient("106.75.80.159",6379,10000,100,10,1000,"mljr9876543210");

    @Test
    public void testToken() throws Exception {
        System.out.println(redisClient.toString());
        System.out.println(saveToken("cfcb087379f2acb934f05c4e758fecbc",1l));
    }

    public String saveToken(String token, Long uid) {
        return redisClient.use(jedis -> {
            Map<String, String> map = Maps.newHashMap();
            map.put(token, String.valueOf(uid));
            return jedis.hmset("token-uid", map);
        });
    }
}
