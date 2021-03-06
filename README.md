amass
-----

`amass` is a high-throughput enterprise-grade web-crawler that crawls specific URLs. It can be used to fetch 
millions of url's per hour. `amass` is different than other crawlers like `crawler4j` that it does not crawl
the nested pages. Instead it just gathers and collects various URLs as supplied. Thus, it **amasses** specific
data from the internet, and hence the name `amass`.

Features
--------
* Enterprise-grade: crawl milions of URLs without worry
* A priority based queue for crawling urgent URLs faster
* Support for pre-crawl and post-crawl handler
* Mechanism to prevent crawling via the pre-crawl handler
* Support for multiple submission of a URL, which increase its priority
* Nano-time accuracy for ordering when priority is the same

Downloads
---------
Downloads via Maven Central are currently not available. You would need to clone and build using:

```
$ git clone https://github.com/sangupta/amass
$ cd amass
$ mvn clean package
```

Tech Stack
----------

* Oracle JDK 6.0
* Apache Maven 3

Dependencies
------------
`amass` project is dependent on the following libraries:

* `jerry` - a utility framework library
* `slf4j` - for logging purposes
* `junit` - for unit-testing the code

Versioning
----------

For transparency and insight into our release cycle, and for striving to maintain backward compatibility, 
`amass` will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

License
-------
	
Copyright (c) 2011-2013, Sandeep Gupta

The project uses various other libraries that are subject to their
own license terms. See the distribution libraries or the project
documentation for more details.

The entire source is licensed under the Apache License, Version 2.0 
(the "License"); you may not use this work except in compliance with
the LICENSE. You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
