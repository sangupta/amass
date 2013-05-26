/**
 *
 * amass - web crawling made easy
 * Copyright (c) 2011-2013, Sandeep Gupta
 * 
 * http://www.sangupta/projects/amass
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.amass;

import com.sangupta.amass.core.AfterCrawlHandler;
import com.sangupta.amass.core.BeforeCrawlHandler;
import com.sangupta.amass.domain.CrawlableURL;
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
		
		// resume crawling
		amass.resume();
		
		// wait for amass to complete and close down
		amass.waitAndShutdown();
		
		System.out.println("Done AMASS testing!");
	}

	@Override
	public boolean beforeCrawl(CrawlableURL crawlableURL, int priority) {
		System.out.println("Crawling: " + crawlableURL.getURL());
		return true;
	}

	@Override
	public void afterCrawl(CrawlableURL crawlableURL, int priority, WebResponse response, long time) {
		if(response != null) {
			System.out.println("Done crawling: " + crawlableURL.getURL() + "with response: " + response.getResponseCode());
		} else {
			System.out.println("Done crawling: " + crawlableURL.getURL() + " with null response");
		}
	}

	@Override
	public void crawlError(CrawlableURL crawlableURL, int priority, Throwable t, long time) {
		System.out.println("Error crawling: " + crawlableURL.getURL());
	}

}
