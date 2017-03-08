package com.mljr.operators.task.chinamobile;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.CookieUtils;
import com.mljr.operators.entity.model.operators.BillInfo;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.primary.operators.IBillInfoService;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by songchi on 17/2/23.
 */
public class HisBillInfoTask implements Runnable {
  protected static final Logger logger = LoggerFactory.getLogger(CurrCallInfoTask.class);

  private ChinaMobileService chinaMobileService;

  private IBillInfoService billInfoService;

  private IRequestInfoService requestInfoService;

  public Long userInfoId;

  public String cookies;

  public RequestInfo requestInfo;


  public void setParams(Long userInfoId, String cookies, RequestInfo requestInfo,
      ApplicationContext context) {
    this.userInfoId = userInfoId;
    this.cookies = cookies;
    this.requestInfo = requestInfo;
    this.chinaMobileService = context.getBean(ChinaMobileService.class);
    this.billInfoService = context.getBean(IBillInfoService.class);
    this.requestInfoService = context.getBean(IRequestInfoService.class);

  }

  Gson gson = new Gson();

  @Override
  public void run() {
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      // 写历史数据
      String queryTime = DateFormatUtils.format(requestInfo.getStartDate(), "yyyy年MM月");
      Map<String, String> cMap = CookieUtils.stringToMap(cookies);
      String historyData = chinaMobileService.getHistoryBillInfo(cMap, queryTime);
      writeHistory(historyData, queryTime);
      requestInfoService.updateStatusBySign(requestInfo.getSign(), RequestInfoEnum.SUCCESS,
          RequestInfoEnum.INIT);

    } catch (Exception e) {
      logger.error("CurrBillInfoTask error", e);
      requestInfoService.updateStatusBySign(requestInfo.getSign(), RequestInfoEnum.ERROR,
          RequestInfoEnum.INIT);
    }
    logger.info("{} chinamobile history bill run use time {}", Thread.currentThread().getName(),
        stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

  public void writeHistory(String historyData, String queryTime) {
    try {
      Map<String, String> map =
          gson.fromJson(historyData, new TypeToken<Map<String, String>>() {}.getType());
      String htmlStr = map.get("message");
      Html html = new Html(htmlStr);
      List<String> feeNameAll = Lists.newArrayList();
      List<String> feeValueAll = Lists.newArrayList();

      for (int i = 2; i <= 6; i++) {
        String namePattern =
            "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[1]//table//tbody//tr[%d]//td[1]//span//p//text()";
        String valuePattern =
            "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[1]//table//tbody//tr[%d]//td[2]//text()";
        String feeName = html.xpath(String.format(namePattern, i)).get();
        String feeValue = html.xpath(String.format(valuePattern, i)).get();
        feeNameAll.add(StringUtils.trim(feeName));
        feeValueAll.add(StringUtils.replace(feeValue, "￥", "").trim());
      }

      for (int i = 2; i <= 5; i++) {
        String namePattern =
            "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[2]//table//tbody//tr[%d]//td[1]//span//p//text()";
        String valuePattern =
            "//*[@id=\"feeInfo\"]//table//tbody//tr[1]//td[2]//table//tbody//tr[%d]//td[2]//text()";
        String feeName = html.xpath(String.format(namePattern, i)).get();
        String feeValue = html.xpath(String.format(valuePattern, i)).get();
        feeNameAll.add(StringUtils.trim(feeName));
        feeValueAll.add(StringUtils.replace(feeValue, "￥", "").trim());
      }

      List<BillInfo> infos = getBillInfos(queryTime, feeNameAll, feeValueAll);

      billInfoService.insertByBatch(userInfoId, infos);

    } catch (Exception e) {
      logger.error("CurrBillInfoTask write history data error", e);
      e.printStackTrace();
    }
  }

  public List<BillInfo> getBillInfos(String queryTime, List<String> feeNameAll,
      List<String> feeValueAll) {
    List<BillInfo> infos = Lists.newArrayList();
    for (int i = 0; i < feeNameAll.size(); i++) {
      BillInfo info = new BillInfo();
      info.setBillDate(Integer.parseInt(queryTime.replace("年", "").replace("月", "")));
      info.setFeeName(feeNameAll.get(i));
      info.setFee(feeValueAll.get(i));
      infos.add(info);
    }
    return infos;
  }


}