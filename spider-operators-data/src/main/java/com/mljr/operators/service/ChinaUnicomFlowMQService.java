package com.mljr.operators.service;

import com.alibaba.fastjson.JSON;
import com.mljr.operators.common.constant.MQConstant;
import com.mljr.operators.common.utils.RabbitMQUtil;
import com.mljr.operators.entity.model.operators.RequestInfo;
import com.mljr.operators.task.chinaunicom.ChinaUnicomTask;
import com.mljr.rabbitmq.RabbitmqClient;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by gaoxi
 *
 * @Time: 2017/3/15 上午11:53
 */
public class ChinaUnicomFlowMQService {

  private static final Logger logger = LoggerFactory.getLogger(ChinaUnicomFlowMQService.class);

  private ApplicationContext context;

  private Channel channel;

  public ChinaUnicomFlowMQService(ApplicationContext context)
      throws IOException, InterruptedException {
    this.context = context;
    channel = RabbitmqClient.newChannel();
  }

  public void run() {
    String message =
        RabbitMQUtil.pollMessage(channel, MQConstant.OPERATOR_MQ_CHINAMOBILE_FLOW_QUEUE);
    if (StringUtils.isNotBlank(message)) {
      RequestInfo entity = JSON.parseObject(message, RequestInfo.class);
      ChinaUnicomTask chinaUnicomTask = new ChinaUnicomTask(context, entity);
      chinaUnicomTask.run();
    }
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {

    }
  }
}