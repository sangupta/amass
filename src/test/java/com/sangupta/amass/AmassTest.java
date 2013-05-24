package com.sangupta.amass;

import com.sangupta.jerry.http.WebResponse;


/**
 * Unit test for simple App.
 */
public class AmassTest implements BeforeCrawlHandler, AfterCrawlHandler {
	
	public static void main(String[] args) {
		AmassTest test = new AmassTest();
		
		// a new instance of Amass with one worker thread
		Amass amass = new Amass(5, test, test);
		
		// add URLs
		amass.crawl("http://www.google.com");
		amass.crawl("http://www.google.com");
		amass.crawl("http://www.google.com");
		amass.crawl("http://www.google.com");
		amass.crawl("http://www.adobe.com");
		amass.crawl("http://www.adobe.com");
		amass.crawl("http://www.sangupta.com");
		
		// print the debug information
		amass.debugJobInfo();
		
		// resume crawling
		amass.resume();
		
		// wait for amass to complete and close down
		amass.cancelAndShutdown();
		
		System.out.println("Done AMASS testing!");
	}

	@Override
	public boolean beforeCrawl(CrawlableURL crawlableURL, int priority) {
		System.out.println("Crawling: " + crawlableURL.getURL());
		return true;
	}

	@Override
	public void afterCrawl(CrawlableURL crawlableURL, int priority, WebResponse response, long time) {
		System.out.println("Done crawling: " + crawlableURL.getURL());
	}

	@Override
	public void crawlError(CrawlableURL crawlableURL, int priority, Throwable t, long time) {
		System.out.println("Error crawling: " + crawlableURL.getURL());
	}

}
