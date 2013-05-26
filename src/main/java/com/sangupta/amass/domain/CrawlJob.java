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

package com.sangupta.amass.domain;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A job object created out of the URL or the {@link CrawlableURL} instance
 * when provided. This job instance maintains the priority and time at which
 * it was created.
 * 
 * Two job instances are considered equal, if they are based on the same URL
 * in a case-sensitive manner. This instance will NOT do any normalization on
 * the URL.
 * 
 * The jobs are sorted based on their priority. The higher the priority of a job,
 * the sooner it will appear in a collection. If the priority of two jobs is equal,
 * they are compared based on creation time. The older the job was created, the sooner
 * it will appear in a collection.
 * 
 * @author sangupta
 *
 */
public class CrawlJob implements Comparable<CrawlJob> {

	/**
	 * The actual URL to work upon.
	 */
	private final CrawlableURL crawlableURL;
	
	/**
	 * The priority assigned to this job 
	 */
	private final AtomicInteger priority;
	
	/**
	 * The time at which this job was created
	 */
	private final long millis;
	
	/**
	 * Construct a new crawling job with default priority of one.
	 * 
	 * @param crawlableURL
	 */
	public CrawlJob(CrawlableURL crawlableURL) {
		this(crawlableURL, 1);
	}
	
	/**
	 * Construct a new crawling job with the given priority.
	 * 
	 * @param crawlableURL
	 * @param priority
	 */
	public CrawlJob(final CrawlableURL crawlableURL, final int priority) {
		if(crawlableURL == null) {
			throw new IllegalArgumentException("Crawlable URL cannot be null");
		}
		
		this.crawlableURL = crawlableURL;
		this.priority = new AtomicInteger(priority);
		this.millis = System.nanoTime();
	}

	/**
	 * Compare this job to another job based on the priority. If the priority is
	 * the same, the time at which is was added.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(CrawlJob job) {
		if(job == null) {
			return -1;
		}
		
		if(this.priority.get() == job.priority.get()) {
			return (int) (this.millis - job.millis);
		}
		
		return 0 - (this.priority.get() - job.priority.get());
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == null) {
			return false;
		}
		
		if(!(object instanceof CrawlJob)) {
			return false;
		}
		
		CrawlJob job = (CrawlJob) object;
		return this.crawlableURL.equals(job.getCrawlableURL());
	}
	
	@Override
	public int hashCode() {
		return this.crawlableURL.hashCode();
	}
	
	@Override
	public String toString() {
		return this.crawlableURL.getURL();
	}
	
	/**
	 * Increment the current priority by one.
	 * 
	 */
	public void incrementPriority(final int additive) {
		this.priority.addAndGet(additive);
	}

	// Usual accessors follow

	/**
	 * @return the crawlableURL
	 */
	public CrawlableURL getCrawlableURL() {
		return crawlableURL;
	}

	/**
	 * @return the priority
	 */
	public AtomicInteger getPriority() {
		return priority;
	}

}
