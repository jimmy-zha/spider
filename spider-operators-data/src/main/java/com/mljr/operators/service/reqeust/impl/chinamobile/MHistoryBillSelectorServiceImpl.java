package com.mljr.operators.service.reqeust.impl.chinamobile;

import com.google.common.collect.Lists;
import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.OperatorsUrlEnum;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.entity.chinamobile.DatePair;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.entity.dto.operator.RequestUrlDTO;
import com.mljr.operators.service.reqeust.AbstractRequestUrlSelectorService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author gaoxi
 * @time 2017/3/5
 */
@Service
public class MHistoryBillSelectorServiceImpl extends AbstractRequestUrlSelectorService {

  @Override
  public List<RequestInfoDTO> getRequestUrl(RequestUrlDTO requestUrl, Date filterDate) {
    List<RequestInfoDTO> list = Lists.newArrayList();
    List<DatePair> datePairList =
        DateUtil.getPreEachOfMonth(requestUrl.getStartDate(), 6, DateUtil.PATTERN_yyyy_MM_dd);
    datePairList.forEach(datePair -> {
      if (null == filterDate || null != filterDate && null != filterUrl(filterDate, datePair)) {
        String url = getUrl(datePair);
        list.add(convert(requestUrl.getMobile(), requestUrl.getIdcard(), datePair, url));
      }
    });
    return list;
  }

  @Override
  public OperatorsEnum getOperator() {
    return OperatorsEnum.CHINAMOBILE;
  }

  @Override
  protected OperatorsUrlEnum getOperatorsUrl() {
    return OperatorsUrlEnum.CHINA_MOBILE_HISTORY_BILL;
  }

  private String getUrl(DatePair datePair) {
    String[] dates = datePair.getEndDate().split("-");
    return String.format(getOperatorsUrl().getUrl(), dates[0] + "年" + dates[1] + "月");
  }
}