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

package com.sangupta.amass.core;

import com.sangupta.amass.domain.CrawlableURL;

/**
 * Contract for implementations that need to check the {@link CrawlableURL} before
 * actual crawling starts.
 * 
 * @author sangupta
 *
 */
public interface BeforeCrawlHandler {
	
	/**
	 * Method that is invoked before crawling for a URL is to begin. This hook can
	 * be used by implementations to figure out one last time, if they wish to cancel
	 * the crawling of the URL.
	 * 
	 * The method should return <code>true</code> to continue crawling the URL, or 
	 * <code>false</code> to cancel the crawling.
	 * 
	 * @param crawlableURL
	 * @return
	 */
	public boolean beforeCrawl(CrawlableURL crawlableURL, int priority);

}
