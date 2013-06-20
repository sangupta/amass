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

import com.sangupta.amass.Amass;

/**
 * A contract for any object that intends be crawled by {@link Amass} and
 * needs to keep extra data along side.
 * 
 * @author sangupta
 *
 */
public interface CrawlableURL {
	
	/**
	 * Return the URL associated with this crawling job.
	 * 
	 * @return the actual {@link String} that represents this URL.
	 * 
	 */
	public String getURL();

}
