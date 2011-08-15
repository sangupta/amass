package com.sangupta.amass;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import com.sangupta.amass.impl.DefaultCrawlableURL;

/**
 * A priority based queue, that collects all URLs that need to be crawled.
 * All crawling threads use this queue instance to work upon.
 * 
 * This queue uses an integer priority to sort items. The higher the priority
 * value the earlier the URL will be crawled.
 *  
 * @author sangupta
 *
 */
public class CrawlingQueue {

	/**
	 * The map that maps the URL to a crawling job so that the same URL does
	 * not get crawled by two different threads at the same go.
	 */
	private final ConcurrentHashMap<String, CrawlJob> jobs;
	
	/**
	 * The embeddded priority queue that serves worker threads.
	 * 
	 */
	private final PriorityBlockingQueue<CrawlJob> jobQueue;
	
	/**
	 * The signal object that let's workers and everyone know if
	 * the jobs are paused, resumed, or stopped.
	 * 
	 */
	private final AmassSignal amassSignal;
	
	/**
	 * Indicates if a closure of this queue has been seeked.
	 * 
	 */
	private volatile boolean closureSeeked;
	
	/**
	 * Constructor.
	 * 
	 * @param amassSignal
	 */
	public CrawlingQueue(AmassSignal amassSignal) {
		this.jobs = new ConcurrentHashMap<String, CrawlJob>();
		this.jobQueue = new PriorityBlockingQueue<CrawlJob>();
		this.amassSignal = amassSignal;
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public boolean submitURL(String url) {
		return this.submitURL(url, 1);
	}

	/**
	 * 
	 * @param url
	 * @param priority
	 * @return
	 */
	public boolean submitURL(final String url, final int priority) {
		if(url == null) {
			return false;
		}
		
		return submitURL(new DefaultCrawlableURL(url), priority);
	}
	
	/**
	 * 
	 * @param crawlableURL
	 * @return
	 */
	public boolean submitURL(CrawlableURL crawlableURL) {
		return this.submitURL(crawlableURL, 1);
	}
	
	/**
	 * 
	 * @param crawlableURL
	 * @return
	 */
	public boolean submitURL(final CrawlableURL crawlableURL, final int priority) {
		if(crawlableURL == null) {
			return false;
		}
		
		CrawlJob job = this.jobs.get(crawlableURL.getURL());
		if(job == null) {
			job = new CrawlJob(crawlableURL, priority);
		}
		
		CrawlJob previous = this.jobs.putIfAbsent(crawlableURL.getURL(), job);
		if(previous == null) {
			// no previous jobs
			// submit this one up
			this.jobQueue.offer(job);
		} else {
			// there seems to be a job previously submitted
			// let's increase its priority
			previous.incrementPriority(priority);
		}
		
		return true;
	}
	
	/**
	 * Get a crawling job out of this {@link CrawlingQueue}. If no element
	 * is available in this queue, this method will wait till one is available.
	 * 
	 * If this queue is shutting down, it will return a <code>null</code>.
	 * 
	 * @return
	 */
	public CrawlJob take() {
		CrawlJob job = null;
		do {
			job = this.jobQueue.poll();
			
			if(this.amassSignal.isStopping()) {
				return null;
			}
			
			if(job != null) {
				break;
			}
			
			if(this.closureSeeked) {
				return null;
			}
			
			// wait for 100 millis before retrying
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// eat up
			}
		} while(true);

		// remove from the jobs map
		this.jobs.remove(job.getCrawlableURL().getURL());
		return job;
	}

	/**
	 * Output the debug information on all jobs.
	 * 
	 */
	void debugJobInfo() {
		for (Enumeration<CrawlJob> myJobs = this.jobs.elements(); myJobs.hasMoreElements(); ) {
			CrawlJob myJob = myJobs.nextElement();
			System.out.println("URL " + myJob.getCrawlableURL().getURL() + " with priority of " + myJob.getPriority().get());
		}
	}

	/**
	 * Clear all pending jobs and close it out.
	 * 
	 */
	void clearAllJobs() {
		this.jobQueue.clear();
		this.jobs.clear();
	}

	/**
	 * Wait for the closure of this queue. The closure time is the time
	 * till all jobs have been read from this queue.
	 * 
	 */
	public void waitForClosure() {
		CrawlJob job = null;
		do {
			job = this.jobQueue.peek();
			if(job == null) {
				break;
			}
			
			// wait for 250ms
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// eat up
			}
		} while(true);
		
		this.closureSeeked = true;
	}

}
