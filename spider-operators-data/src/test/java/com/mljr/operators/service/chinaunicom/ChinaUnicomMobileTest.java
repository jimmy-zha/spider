package com.mljr.operators.service.chinaunicom;

import com.alibaba.fastjson.JSON;
import com.mljr.operators.entity.dto.chinaunicom.*;
import com.mljr.operators.service.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gaoxi
 * @Time 2017/2/19
 */

public class ChinaUnicomMobileTest extends BaseTest {

    @Autowired
    private IChinaUnicomService chinaUnicomService;

    private String cookies;

    @Override
    public void testInit() {
        super.testInit();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setMobile("");
        loginDTO.setPassword("");
        cookies = chinaUnicomService.getCookies(loginDTO);
        System.out.println(cookies);
    }

    @Test
    public void testQueryUserInfo() throws Exception {
        PersonInfoDTO personInfo = chinaUnicomService.queryUserInfo(cookies);
        System.out.println(JSON.toJSON(personInfo));
    }

    @Test
    public void testQueryAcctBalance() throws Exception {
        RemainingDTO remaining = chinaUnicomService.queryAcctBalance(cookies);
        System.out.println(remaining);
    }

    @Test
    public void testQueryCallDetail() throws Exception {
        CallDTO call = chinaUnicomService.queryCallDetail(cookies, 2017, 1, 1);
        System.out.println(JSON.toJSON(call));
    }


    @Test
    public void testQueryHistoryBill() throws Exception {
        BillDTO bill = chinaUnicomService.queryHistoryBill(cookies, 2017, 1);
        System.out.println(JSON.toJSON(bill));
    }

    @Test
    public void testQuerySMS() throws Exception {
        SMSDTO sms = chinaUnicomService.querySMS(cookies, 2017, 1, 1);
        System.out.println(JSON.toJSON(sms));
    }

}