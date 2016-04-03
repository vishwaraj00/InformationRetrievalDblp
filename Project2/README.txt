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

1. Eclipse Java IDE for Windows x64 – Used as IDE for building Java indexer for Elasricsearch and to build the GUI on Sprint framework using Java web developer tools. Downloaded via http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/mars2 on 4th March, 2016.
2. Elasticsearch 2.2.1 – OpenSource and based on Lucene APIs, used as the search engine backend. Downloaded via https://download.elasticsearch.org/elasticsearch/release/org/elasticsearch/distribution/zip/elasticsearch/2.2.1/elasticsearch-2.2.1.zip latest on 20th Feb, 2016.
3. Weka 3.6.13 for Windows - Used as a tool for Data processing. Download via http://www.cs.waikato.ac.nz/ml/weka/downloading.html

-------------------
HOW TO RUN INDEXING
-------------------
These steps need to be done before running the applications
1. Start elastic server
2. Create "data" folder in Project2 and extract data files from DBLP dataset into that folder.
3. Open indexer project 'Project2' in Eclipse.
4. Run "Indexer.java" as a 'Java Application'.

-------------------------------------
FIND MOST POPULAR TOPICS IN EACH YEAR
-------------------------------------

1. Open "SearchYear.java"
2. Enter the input year
3. Run the file

--------------------------------------
FIND MOST SIMILAR CONFERENCES/JOURNALS
--------------------------------------

1. Open "SearchConference.java"
2. Enter the input conference and year
3. Run the file
