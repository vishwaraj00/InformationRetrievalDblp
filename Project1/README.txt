----------------------------
SYSTEM REQUIREMENTS
----------------------------
1. Ubuntu 14.04 x64 or later version.
2. 4GB or more RAM, 1GB storage for elastic search storage.
3. Access to the DBLP DataSet.
4. All required libraries are installed in the system.

----------------------------
REQUIRED LIBRARIES AND TOOLS
----------------------------

1. Apache HttpComponents Client 4.5.2 – Used in Java indexer for Elasticsearch to make Http Calls over localhost. Downloaded via https://www.apache.org/dist/httpcomponents/httpclient/binary/httpcomponents-client-4.5.2-bin.tar.gz on 8th March, 2016.
2. Apache Tomcat 5.5 – Used as a web server for GUI. Downloaded via Ubuntu repositories on 10th March, 2016.
3. Eclipse Java IDE for Linux x64 – Used as IDE for building Java indexer for Elasricsearch and to build the GUI on Sprint framework using Java web developer tools. Downloaded via http://download.eclipse.org/releases/kepler/201402280900 on 4th March, 2016.
4. Elasticsearch 2.2.1 – OpenSource and based on Lucene APIs, used as the search engine backend. Downloaded via https://download.elasticsearch.org/elasticsearch/release/org/elasticsearch/distribution/zip/elasticsearch/2.2.1/elasticsearch-2.2.1.zip latest on 20th Feb, 2016.
5. Javax Json 1.0 – Used to process json files in DBLP Dataset. Downloaded via http://www.java2s.com/Code/JarDownload/javax.json/javax.json-1.0.jar.zip on 8th March, 2016.
6. Kibana 4.4.2 for Linux x64 – Used as a tool to setup Elasticsearch. Downloaded via https://download.elastic.co/kibana/kibana/kibana-4.4.2-linux-x64.tar.gz on 28th Feb, 2016.
7. Sense 2.0.0 for Kibana – Used as a GUI over Kibana to comunicate settings to Elasticsearch. Downloaded via https://github.com/elastic/sense on 28th Feb, 2016.

-------------------
HOW TO RUN INDEXING
-------------------
1. Go to DblpDataset (root) folder and execute runElasticSearch.sh to start elastic search and wait for the cluster status to turn 'Green'.
2. Extract data files from DBLP dataset into the root project folder. '/Data'.
3. 'dblp.xml' file should be now present in the Data folder.
4. Open indexer project 'eclipse_workspace/DblpIndexerFromZip' in Eclipse.
5. Run the indexer as a 'Java Application'.

--------------------
HOW TO RUN QUERY GUI
--------------------

1. Make sure elasticsearch is running on localhost:9200 with status 'Green'.
2. Make sure the indexing is complete atleast once, so that we have some indexes to search upon.
3. Make sure the Apache server is running.
4. Open eclipse java project: DblpDataset / eclipse_workspace / Dblp.
5. Execute the project at 'Run At Server'.

-----------------------
DEPLOYMENT INSTRUCTIONS
-----------------------

1. Do indexing before searching. It will take ~5 minutes.
2. For indexing, follow these steps:
3. Paste the Dataset zip in DblpDataset / Data
4. Extract the zip to find a file: DblpDataset / Data / dblp.xml
5. Follow 'How to run indexing' to get the indexing completed.
6. For querying, you need to set up the web UI on a server:
7. Once indexing is finished, follow 'How to run query GUI' to execute the GUI page.
8. Enter the desired queries in the GUI page and click search to see the desired results.

