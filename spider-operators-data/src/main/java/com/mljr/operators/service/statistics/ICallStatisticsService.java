package com.mljr.operators.service.statistics;

import com.mljr.operators.entity.vo.statistics.CallStatisticsVO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by songchi on 17/3/13.
 */
public interface ICallStatisticsService {
    CallStatisticsVO selectTimeByCallType(@Param("userInfoId") long userInfoId,
                                          @Param("callType") String callType);
}
