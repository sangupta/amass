/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 *  Copyright (C) 2013 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/

package com.sangupta.amass;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sangupta.amass.impl.CrawlingWorker;
import com.sangupta.jerry.util.DateUtils;

/**
 * @author sangupta
 *
 */
public class AmassDeadlockDetector extends TimerTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AmassDeadlockDetector.class);
	
	private static final ThreadMXBean THREAD_MX_BEAN;
	
	static {
		THREAD_MX_BEAN = ManagementFactory.getThreadMXBean();
	}
	
	private final CrawlingWorker[] workers;
	
	private final Thread[] threads;
	
	public AmassDeadlockDetector(Amass amass) {
		this.workers = amass.getCrawlingWorkers();
		this.threads = amass.getWorkerThreads();
	}
	
	/**
	 * @param workers
	 */
	AmassDeadlockDetector(CrawlingWorker[] workers, Thread[] threads) {
		this.workers = workers;
		this.threads = threads;
	}

	/**
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		// for every run, check out the threads of amass and see if they are blocked
		// or not, and why they are blocked
		long[] ids = THREAD_MX_BEAN.findDeadlockedThreads();
		if(ids != null && ids.length > 0) {
			LOGGER.error("Found " + ids.length + " dead-locked threads via MXBean");
			for(long id : ids) {
				ThreadInfo info = THREAD_MX_BEAN.getThreadInfo(id);
				LOGGER.error("    Thread " + info.getThreadName() + " is currently blocked on " + info.getLockName() + " currently owned by " + info.getLockOwnerName());
				StackTraceElement[] elements = info.getStackTrace();
				
				for(StackTraceElement element : elements) {
					LOGGER.error("        " + element.toString());
				}
			}
		}
		
		// also dump a stats of time from all amass threads as to when they were last processed.
		LOGGER.error("Last crawl times of all workers are as under: ");
		long current = System.currentTimeMillis();
		for(int i = 0; i < workers.length; i++) {
			long time = workers[i].getLastCrawlTime();
			String additive = "";
			boolean printStackTrace = false;
			if((current - time) > DateUtils.FIVE_MINUTES) {
				additive = "[MAY-BE-BLOCKED]";
				printStackTrace = true;
			}
			
			LOGGER.error("    Worker-" + i + " " + additive + " last time when the thread crawled: " + time);
			if(printStackTrace) {
				Thread t = this.threads[i];
				StackTraceElement[] elements = t.getStackTrace();
				LOGGER.error("Thread stack trace for " + t.getName() + "; id=" + t.getId());
				for(StackTraceElement element : elements) {
					LOGGER.error("        " + element.toString());
				}
			}
		}
	}

}
