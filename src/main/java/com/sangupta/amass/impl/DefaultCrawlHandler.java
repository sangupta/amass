package com.sangupta.amass.impl;

import com.sangupta.amass.core.CrawlHandler;
import com.sangupta.amass.domain.CrawlableURL;
import com.sangupta.jerry.http.WebInvoker;
import com.sangupta.jerry.http.WebResponse;

public class DefaultCrawlHandler implements CrawlHandler {

	@Override
	public WebResponse crawl(CrawlableURL crawlableURL) {
		return WebInvoker.getResponse(crawlableURL.getURL());
	}

}
