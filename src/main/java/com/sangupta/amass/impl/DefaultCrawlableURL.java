package com.sangupta.amass.impl;

import com.sangupta.amass.CrawlableURL;

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
