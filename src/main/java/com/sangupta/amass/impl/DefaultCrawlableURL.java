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

package com.sangupta.amass.impl;

import com.sangupta.amass.CrawlableURL;

/**
 * 
 * @author sangupta
 *
 */
public class DefaultCrawlableURL implements CrawlableURL {
	
	private final String url;
	
	public DefaultCrawlableURL(String url) {
		if(url == null) {
			throw new IllegalArgumentException("URL to be crawled cannot be null");
		}
		
		this.url = url;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(!(obj instanceof CrawlableURL)) {
			return false;
		}
		
		CrawlableURL cu = (CrawlableURL) obj;
		return this.url.equals(cu.getURL());
	}
	
	@Override
	public int hashCode() {
		return this.url.hashCode();
	}
	
	@Override
	public String toString() {
		return this.url;
	}
	
	// Usual accessors follow

	public String getURL() {
		return this.url;
	}

}
