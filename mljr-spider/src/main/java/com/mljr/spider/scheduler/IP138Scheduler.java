/**
 * BaiduMobileScheduler.java by Ckex.zha
 */
package com.mljr.spider.scheduler;

import com.google.common.base.CharMatcher;
import com.mljr.spider.mq.UMQMessage;
import com.mljr.spider.scheduler.manager.AbstractMessage.PullMsgTask;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;

import java.util.concurrent.BlockingQueue;

/**
 * @author Ckex.zha <br/>
 *         Nov 17, 20163:06:26 PM
 */
public class IP138Scheduler extends AbstractScheduler {

	private static final String URL = "http://www.ip138.com:8080/search.asp?mobile=%s&action=mobile";

	public IP138Scheduler(Spider spider, PullMsgTask task) throws Exception {
		super(spider, task);
	}

	public IP138Scheduler(Spider spider, String qid) throws Exception {
		super(spider, qid);
	}

	public IP138Scheduler(Spider spider, BlockingQueue<UMQMessage> queue) throws Exception {
		super(spider, queue);
	}

	@Override
	public void push(Request request, Task task) {
		put(request);
	}

	@Override
	public Request poll(Task task) {
		return take();
	}

	@Override
	public int getLeftRequestsCount(Task task) {
		return 0;
	}

	@Override
	public int getTotalRequestsCount(Task task) {
		return 0;
	}

	@Override
	public boolean pushTask(Spider spider, UMQMessage message) {
		push(buildRequst(message.message), spider);
		return true;
	}

	@Override
	Request buildRequst(String message) {
		String url = String.format(URL, message);
		url = CharMatcher.WHITESPACE.replaceFrom(CharMatcher.anyOf("\r\n\t").replaceFrom(url, ""), "");
		return new Request(url);
	}
}
