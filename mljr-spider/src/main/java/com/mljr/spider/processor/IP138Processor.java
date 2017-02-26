/**
 * 
 */
package com.mljr.spider.processor;

import com.mljr.utils.RandomUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,上午12:10:12
 *
 */
public class IP138Processor extends AbstractPageProcessor {

  private static Site site = Site.me().setDomain("ip138.com").setSleepTime(300).setRetrySleepTime(2000).setRetryTimes(3)
      .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");


  @Override
  boolean onProcess(Page page) {
    // Selectable tbody = page.getHtml().xpath("//body//table[2]/tbody");
    // page.putField("卡号归属地", tbody.xpath("//tr[3]//td[2]//text()"));
    // page.putField("卡类型", tbody.xpath("//tr[4]//td[2]//text()"));
    // page.putField("区号", tbody.xpath("//tr[5]//td[2]//text()"));
    // page.putField("邮编", tbody.xpath("//tr[6]//td[2]//text()"));
    if (RandomUtils.randomPrint(100)) {
      if (logger.isDebugEnabled()) {
        logger.debug("process--> " + page.getUrl());
      }
    }
    page.putField("", page.getHtml());
    return true;
  }

  public IP138Processor() {
    super(site);
  }

}
