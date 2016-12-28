package com.mljr.monitor.controller;

import com.mljr.entity.MonitorData;
import com.mljr.monitor.service.StatusCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by songchi on 16/12/23.
 */
@Controller
public class StatusCodeController {

    @Autowired
    private StatusCodeService statusCodeService;

    @RequestMapping("/statusCode")
    public ModelAndView statusCode() {

        List<String> jsonList = statusCodeService.getLatestRecord();
        List<MonitorData> dataList = statusCodeService.transferToObject(jsonList);
        Collections.sort(dataList, new Comparator<MonitorData>() {
            @Override
            public int compare(MonitorData o1, MonitorData o2) {
                return o1.getDomain().compareTo(o2.getDomain());
            }
        });

        ModelAndView mav = new ModelAndView("statusCode");
        mav.addObject("dataList", dataList);
        mav.addObject("totolCount", statusCodeService.totolCount(dataList));
        return mav;
    }

    @RequestMapping("/detail/{serverIp}/{domain}/other")
    public ModelAndView detail(@PathVariable("serverIp") String serverIp, @PathVariable("domain") String domain) {

        List<String> jsonList = statusCodeService.getRecordByDomain(serverIp, domain);
        List<MonitorData> dataList = statusCodeService.transferToObject(jsonList);

        ModelAndView mav = new ModelAndView("statusCode");
        mav.addObject("dataList", dataList);
        mav.addObject("totolCount", statusCodeService.totolCount(dataList));
        return mav;
    }
}
