package com.mljr.monitor.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.mljr.common.ServiceConfig;
import com.mljr.entity.MonitorData;
import com.mljr.redis.RedisClient;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by songchi on 16/12/23.
 */
@Service
public class StatusCodeService {
    private static RedisClient redisClient = ServiceConfig.getSpiderRedisClient();

    /**
     * 每家网站取最新的一条监控数据,然后汇总起来
     */
    public List<String> getLatestRecord() {
        return redisClient.use(new Function<Jedis, List<String>>() {

            @Override
            public List<String> apply(Jedis jedis) {
                List<String> allList = new ArrayList<>();
                Set<String> set = jedis.keys("status-code-*");
                for (String key : set) {
                    allList.addAll(jedis.lrange(key, 0, 0));
                }
                return allList;
            }
        });
    }

    /**
     * 取某家网站的最新的100条记录
     */
    public List<String> getRecordByDomain(String serverIp, String domain) {
        return redisClient.use(new Function<Jedis, List<String>>() {
            @Override
            public List<String> apply(Jedis jedis) {
                return jedis.lrange(Joiner.on("-").join("status-code", serverIp, domain), 0, 99);
            }
        });
    }

    public List<MonitorData> transferToObject(List<String> jsonList) {
        List<MonitorData> dataList = new ArrayList<>();
        for (String jsonStr : jsonList) {
            MonitorData data = JSON.parseObject(jsonStr, MonitorData.class);
            dataList.add(data);
        }
        return dataList;
    }

    public MonitorData totolCount(List<MonitorData> list) {
        int allTotalRequests = 0;
        int total200 = 0;
        int total301 = 0;
        int total302 = 0;
        int total304 = 0;
        int total307 = 0;
        int total401 = 0;
        int total403 = 0;
        int total404 = 0;
        int total500 = 0;
        int total501 = 0;
        int total504 = 0;
        int totalParseFail = 0;
        for (MonitorData data : list) {
            allTotalRequests += data.getTotalRequests();
            total200 += data.getFreq200();
            total301 += data.getFreq301();
            total302 += data.getFreq302();
            total304 += data.getFreq304();
            total307 += data.getFreq307();
            total401 += data.getFreq401();
            total403 += data.getFreq403();
            total404 += data.getFreq404();
            total500 += data.getFreq500();
            total501 += data.getFreq501();
            total504 += data.getFreq504();
            totalParseFail+= data.getFreqParseFail();
        }
        MonitorData totalData = new MonitorData();
        totalData.setTotalRequests(allTotalRequests);
        totalData.setFreq200(total200);
        totalData.setFreq301(total301);
        totalData.setFreq302(total302);
        totalData.setFreq304(total304);
        totalData.setFreq307(total307);
        totalData.setFreq401(total401);
        totalData.setFreq403(total403);
        totalData.setFreq404(total404);
        totalData.setFreq500(total500);
        totalData.setFreq501(total501);
        totalData.setFreq504(total504);
        totalData.setFreqParseFail(totalParseFail);
        return totalData;
    }
}
