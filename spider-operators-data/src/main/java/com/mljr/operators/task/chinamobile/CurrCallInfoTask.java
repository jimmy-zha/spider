package com.mljr.operators.task.chinamobile;

import com.google.common.base.Stopwatch;
import com.mljr.operators.common.constant.RequestInfoEnum;
import com.mljr.operators.common.utils.CookieUtils;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.service.ChinaMobileParseService;
import com.mljr.operators.service.ChinaMobileService;
import com.mljr.operators.service.primary.operators.ICallInfoService;
import com.mljr.operators.service.primary.operators.IPhoneInfoService;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by songchi on 17/2/23.
 */
public class CurrCallInfoTask implements Runnable {

  protected static final Logger logger = LoggerFactory.getLogger(HisCallInfoTask.class);

  private ChinaMobileService chinaMobileService;

  private ICallInfoService callInfoService;

  private IRequestInfoService requestInfoService;

  public Long userInfoId;

  public String cookies;

  public RequestInfo requestInfo;

  public IPhoneInfoService phoneInfoService;


  public void setParams(Long userInfoId, String cookies, RequestInfo requestInfo,
      ApplicationContext context) {
    this.userInfoId = userInfoId;
    this.cookies = cookies;
    this.requestInfo = requestInfo;
    this.chinaMobileService = context.getBean(ChinaMobileService.class);
    this.callInfoService = context.getBean(ICallInfoService.class);
    this.requestInfoService = context.getBean(IRequestInfoService.class);
    this.phoneInfoService = context.getBean(IPhoneInfoService.class);
  }

  @Override
  public void run() {
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      DatePair pair = new DatePair(DateFormatUtils.format(requestInfo.getStartDate(), "yyyy-MM-dd"),
          DateFormatUtils.format(requestInfo.getEndDate(), "yyyy-MM-dd"));
      Map<String, String> cMap = CookieUtils.stringToMap(cookies);
      String data = chinaMobileService.getCurrentCallInfo(cMap, pair);
      writeToDb(data, pair);

      requestInfoService.updateStatusBySign(requestInfo.getSign(), RequestInfoEnum.SUCCESS,
          RequestInfoEnum.INIT);

    } catch (Exception e) {
      logger.error("CurrCallInfoTask error", e);
      requestInfoService.updateStatusBySign(requestInfo.getSign(), RequestInfoEnum.ERROR,
          RequestInfoEnum.INIT);
    }
    logger.info("{} chinamobile curr call run use time {}", Thread.currentThread().getName(),
        stopwatch.elapsed(TimeUnit.MILLISECONDS));
  }

  void writeToDb(String data, DatePair pair) throws Exception {

    callInfoService.insertByBatch(userInfoId, ChinaMobileParseService.parseCallInfo(phoneInfoService,data,pair,userInfoId));
  }

}
