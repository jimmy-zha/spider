/**
 * 
 */
package com.mljr.spider.listener;

import java.util.Date;

import com.mljr.spider.processor.AbstractPageProcessor.PageListener;
import com.mljr.utils.IpUtils;

import us.codecraft.webmagic.Page;

/**
 * @author Ckex zha </br>
 *         Jan 5, 2017,9:42:10 AM
 *
 */
public class ProcessListener extends AbstractMonitorCache implements PageListener {

	public ProcessListener() {
		super();
	}

	@Override
	public void afterProcess(boolean isSuccess, String domain, Page page) {
		if (isSuccess) {
			return; // nothing.
		}
		Setter setter = val -> val.setFreqParseFail(1 + val.getFreqParseFail());
		updateValue(new LocalCacheKey(new Date(), IpUtils.getHostName(), domain), setter);
	}

}
