package com.mljr.operators.service.reqeust.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mljr.operators.common.utils.DateUtil;
import com.mljr.operators.entity.dto.operator.RequestInfoDTO;
import com.mljr.operators.entity.dto.operator.RequestUrlDTO;
import com.mljr.operators.service.primary.operators.IRequestInfoService;
import com.mljr.operators.service.reqeust.IOperatorRequestUrlService;
import com.mljr.operators.service.reqeust.IRequestUrlSelectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gaoxi
 * @time 2017/3/2
 */
@Service
public class OperatorRequestUrlServiceImpl implements IOperatorRequestUrlService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OperatorRequestUrlServiceImpl.class);

  private ImmutableList<IRequestUrlSelectorService> requestUrlSelectorServices;

  @Autowired
  private IRequestInfoService requestInfoService;

  @Autowired
  public OperatorRequestUrlServiceImpl(Map<String, IRequestUrlSelectorService> serviceMap) {
    Preconditions.checkNotNull(serviceMap, "Selector service is null");
    requestUrlSelectorServices = ImmutableList.copyOf(serviceMap.values());
  }

  @Override
  public List<RequestInfoDTO> getAllUrlByOperator(RequestUrlDTO requestUrl) {
    List<RequestInfoDTO> list = Lists.newArrayList();
    requestUrlSelectorServices.forEach(requestUrlSelectorService -> {
      if (requestUrlSelectorService.getOperator() == requestUrl.getOperators()
          && requestUrlSelectorService.availableProvince().contains(requestUrl.getProvince())) {
        list.addAll(requestUrlSelectorService.getRequestUrl(requestUrl));
      }
    });
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("get all url.requestUrl:{},data:{}", requestUrl.toString(), JSON.toJSON(list));
    }
    if (!list.isEmpty()) {
      RequestInfoDTO entity = list.get(0);
      // Date date = requestInfoService.getPerRequestDate(entity.getMobile(), entity.getIdcard());

    }
    return list;
  }

  private List<RequestInfoDTO> filter(LocalDate filterDate, List<RequestInfoDTO> list) {
    return list.stream().filter(requestInfoDTO -> {
      LocalDate startDate =
          DateUtil.stringToLocalDate(requestInfoDTO.getStartDate(), DateUtil.PATTERN_yyyy_MM_dd);
      LocalDate endDate =
          DateUtil.stringToLocalDate(requestInfoDTO.getEndDate(), DateUtil.PATTERN_yyyy_MM_dd);
      if (filterDate.isAfter(endDate)) {
        return false;
      } else if (startDate.isBefore(filterDate) && filterDate.isAfter(endDate)) {

      }
      return true;
    }).collect(Collectors.toList());
  }
}