/**
 *
 * amass - web crawling made easy
 * Copyright (c) 2011, Sandeep Gupta
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

import com.sangupta.jerry.http.WebResponse;

/**
 * Contract for handlers that need to work upon the crawled URL, its response
 * from the web; or a resulting error in the process of a crawl.
 * 
 * @author sangupta
 * 
 */
public interface AfterCrawlHandler {

	/**
	 * Method called when crawling the URLs was successful.
	 * 
	 * @param crawlableURL
	 *            the URL that was being crawled, or the {@link CrawlableURL}
	 *            instance in case one was submitted.
	 * 
	 * @param priority
	 *            the priority with which the URL was crawled.
	 * 
	 * @param response
	 *            the {@link WebResponse} received as part of crawling.
	 * 
	 * @param timeConsumed
	 *            the time consumed in milli-seconds to crawl
	 */
	public void afterCrawl(CrawlableURL crawlableURL, int priority, WebResponse response, long timeConsumed);

	/**
	 * Method called when crawling the URL resulted in an error being
	 * encountered.
	 * 
	 * @param crawlableURL
	 *            the URL that was being crawled, or the {@link CrawlableURL}
	 *            instance in case one was submitted.
	 * 
	 * @param priority
	 *            the priority with which the URL was crawled.
	 * 
	 * @param t
	 *            the exception that was thrown during the process
	 * 
	 * @param timeConsumed
	 *            the time consumed in milli-seconds to crawl
	 */
	public void crawlError(CrawlableURL crawlableURL, int priority, Throwable t, long timeConsumed);

}
