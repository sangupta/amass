package com.sangupta.amass.core;

import com.sangupta.amass.domain.CrawlableURL;
import com.sangupta.jerry.http.WebResponse;

public interface CrawlHandler {
	
	/**
	 * Defines an interface for custom crawling functions.
	 * 
	 * @param crawlableURL
	 * @return
	 */
	public WebResponse crawl(CrawlableURL crawlableURL);

}
